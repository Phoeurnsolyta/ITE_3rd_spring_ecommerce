package co.istad.lyta.ecommerce.features.userprofile.dto;

import lombok.Builder;

@Builder
public record UpdateUserProfileRequest(
        String firstName,
        String lastName,
        String phoneNumber,
        String gender,
        String address,
        String biography,
        String avatar
) {
}
