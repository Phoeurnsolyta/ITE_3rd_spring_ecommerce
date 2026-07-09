package co.istad.lyta.ecommerce.features.auth;

import co.istad.lyta.ecommerce.features.auth.dto.RegisterResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;


@Mapper (componentModel = "spring")
public abstract class AuthMapper {

    public RegisterResponse mapUserRepresentationToRegisterResponse(
            UserRepresentation userRepresentation) {
        return RegisterResponse.builder()
                .userId(userRepresentation.getId())
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .phoneNumber(userRepresentation.firstAttribute("phoneNumber"))
                .build();
    }
}
