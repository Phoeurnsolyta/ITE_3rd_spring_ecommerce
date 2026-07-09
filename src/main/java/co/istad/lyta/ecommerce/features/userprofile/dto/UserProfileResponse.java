package co.istad.lyta.ecommerce.features.userprofile.dto;

import lombok.Builder;

@Builder
public record UserProfileResponse(
        String userId,
        String firstName,
        String lastName,
        String userName,
        String email,
        String phoneNumber,
        String gender,
        String address,
        String biography,
        String avatar
) {
}
