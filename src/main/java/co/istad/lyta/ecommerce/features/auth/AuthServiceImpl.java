package co.istad.lyta.ecommerce.features.auth;

import co.istad.lyta.ecommerce.features.auth.dto.RegisterRequest;
import co.istad.lyta.ecommerce.features.auth.dto.RegisterResponse;
import co.istad.lyta.ecommerce.features.auth.dto.RoleEnum;
import co.istad.lyta.ecommerce.features.userprofile.UserProfile;
import co.istad.lyta.ecommerce.features.userprofile.UserProfileRepository;
import co.istad.lyta.ecommerce.security.KeycloakAdminClientProps;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    
    private final Keycloak keycloak;
    private final KeycloakAdminClientProps props;
    private final AuthMapper authMapper;
    private final UserProfileRepository userProfileRepository;
    
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
//        validate password matching
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, 
                    "Passwords don't match"
            );
        }
        // Create keycloak user
        UsersResource userResource = keycloak
                .realm(props.getTargetRealm())
                .users();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(registerRequest.username());
        userRepresentation.setEmail(registerRequest.email());
        userRepresentation.setFirstName(registerRequest.firstName());
        userRepresentation.setLastName(registerRequest.lastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);
        
//        set keycloak custom
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("phoneNumber", List.of(registerRequest.phoneNumber()));
        userRepresentation.setAttributes(attributes);
        
//        set credential
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequest.password());
        userRepresentation.setCredentials(List.of(credential));
        
      try (Response response = userResource.create(userRepresentation)) {
          log.info("Response Status Code : {}", response.getStatus());
          if (response.getStatus() == HttpStatus.CREATED.value()) {
//              success situation
              UserRepresentation createUser = keycloak.realm(props.getTargetRealm())
                      .users()
                      .search(userRepresentation.getUsername())
                      .getFirst();
              log.info("Created User : {}", createUser.getId());

              UserResource userResource1 = keycloak.realm(props.getTargetRealm()).users().get(createUser.getId());
              userResource1.sendVerifyEmail();
              RolesResource roleResource = keycloak.realm(props.getTargetRealm()).roles();
              RoleRepresentation roleUser = roleResource.get(RoleEnum.USER.name()).toRepresentation();
              RoleRepresentation roleCustomer = roleResource.get(RoleEnum.CUSTOMER.name()).toRepresentation();
              log.info("role user: {}", roleUser);
              log.info("role student: {}", roleCustomer);
              userResource1.roles().realmLevel().add(List.of(roleUser,roleCustomer));

//              saved user profile
              UserProfile userProfile = new UserProfile();
              userProfile.setUserId(createUser.getId());
              userProfileRepository.save(userProfile);


              return authMapper.mapUserRepresentationToRegisterResponse(createUser);
              
          } else if (response.getStatus() == HttpStatus.CONFLICT.value()) {
              log.info("check username or email are already exist");
          }
      }
        return null;
    }
}
