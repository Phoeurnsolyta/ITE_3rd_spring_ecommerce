package co.istad.lyta.ecommerce.features.userprofile;

import co.istad.lyta.ecommerce.features.userprofile.dto.UserProfileResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public abstract class UserProfileMapper {

    public UserProfileResponse mapUserRepresentationToUserProfileResponse(UserRepresentation userRepresentation) {
        return UserProfileResponse.builder()
                .userId(userRepresentation.getId())
                .userName(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .phoneNumber(userRepresentation.getAttributes().get("phoneNumber").getFirst())
                .build();
    }
}
