package co.istad.lyta.ecommerce.features.userprofile;

import co.istad.lyta.ecommerce.features.userprofile.dto.UpdateUserProfileRequest;
import co.istad.lyta.ecommerce.features.userprofile.dto.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse me ();

    UserProfileResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest);
}
