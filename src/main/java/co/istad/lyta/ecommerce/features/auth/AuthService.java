package co.istad.lyta.ecommerce.features.auth;

import co.istad.lyta.ecommerce.features.auth.dto.RegisterRequest;
import co.istad.lyta.ecommerce.features.auth.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register (RegisterRequest registerRequest);
}
