package co.istad.lyta.ecommerce.features.userprofile;

import co.istad.lyta.ecommerce.features.userprofile.dto.UpdateUserProfileRequest;
import co.istad.lyta.ecommerce.features.userprofile.dto.UserProfileResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper (componentModel = "spring")
public abstract class UserProfileMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void mapUpdateUserProfileRequestToUserProfile(
            UpdateUserProfileRequest updateUserProfileRequest,
            @MappingTarget UserProfile userProfile
    );

    public void mapUpdateUserProfileRequestToUserRepresentation(
            UpdateUserProfileRequest updateUserProfileRequest,
            @MappingTarget UserRepresentation userRepresentation
    ) {
        if (updateUserProfileRequest.firstName() != null) {
            userRepresentation.setFirstName(updateUserProfileRequest.firstName());

            if (updateUserProfileRequest.lastName() != null) {
                userRepresentation.setFirstName(updateUserProfileRequest.lastName());

                if (updateUserProfileRequest.phoneNumber() != null) {
                    Map<String, List<String>> attributes = new HashMap<>();
                    attributes.put("phoneNumber", List.of(updateUserProfileRequest.phoneNumber()));
                    userRepresentation.setAttributes(attributes);
                }
            }
        }
    }

    public UserProfileResponse toUserProfileResponse(UserRepresentation userRepresentation, UserProfile userProfile) {
        return UserProfileResponse.builder()
                .userId(userRepresentation.getId())
                .userName(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .phoneNumber(userRepresentation.getAttributes().get("phoneNumber").getFirst())
                .gender(userProfile.getGender())
                .address(userProfile.getAddress())
                .biography(userProfile.getBiography())
                .avatar(userProfile.getAvatar())
                .build();
    }
}




