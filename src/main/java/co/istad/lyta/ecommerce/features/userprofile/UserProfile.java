package co.istad.lyta.ecommerce.features.userprofile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "user_profiles")
public class UserProfile {

//    from keycloak
    @Id
    private String userId;
    private String gender;
    private String biography;
    private String address;
    private String avatar;

}
