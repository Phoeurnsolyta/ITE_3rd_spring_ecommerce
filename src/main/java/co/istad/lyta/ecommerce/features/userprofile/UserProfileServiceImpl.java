package co.istad.lyta.ecommerce.features.userprofile;

import co.istad.lyta.ecommerce.features.userprofile.dto.UpdateUserProfileRequest;
import co.istad.lyta.ecommerce.features.userprofile.dto.UserProfileResponse;
import co.istad.lyta.ecommerce.security.KeycloakAdminClientProps;
import co.istad.lyta.ecommerce.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl  implements UserProfileService {

    private final Keycloak keycloak;
    private final KeycloakAdminClientProps props;
    private final UserProfileMapper userProfileMapper;
    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileResponse me() {
//        1. profile from keycloak by using userid
        String userId = SecurityUtils.extractUserId();

        UserRepresentation keycloakUser = keycloak.realm(props.getTargetRealm())
                .users()
                .get(userId)
                .toRepresentation();

//        2. profile from database by using userId
        UserProfile userProfile  = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile has not been found"));
        return userProfileMapper.toUserProfileResponse(keycloakUser, userProfile);
    }

    @Override
    public UserProfileResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest) {

//        get current log in user
        String userId = SecurityUtils.extractUserId();

//        update profile in keycloak
        UserResource userResource = keycloak
                .realm(props.getTargetRealm())
                .users()
                .get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userProfileMapper.mapUpdateUserProfileRequestToUserRepresentation(
                updateUserProfileRequest,
                userRepresentation
        );
        userResource.update(userRepresentation);
//        update profile in database
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile has not been found"));
        userProfileMapper.mapUpdateUserProfileRequestToUserProfile(
                updateUserProfileRequest,
                userProfile
        );
        userProfileRepository.save(userProfile);

        return userProfileMapper.toUserProfileResponse(
                userRepresentation,
                userProfile
        );
    }
}
