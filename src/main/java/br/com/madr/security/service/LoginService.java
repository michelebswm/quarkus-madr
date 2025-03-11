package br.com.madr.security.service;

import br.com.madr.domain.User;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.repository.UserRepository;
import br.com.madr.security.GenerateToken;
import br.com.madr.security.dto.LoginDTO;
import br.com.madr.security.dto.LoginResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LoginService {

    @Inject
    GenerateToken generateToken;

    @Inject
    UserRepository userRepository;

    public LoginResponseDTO login(LoginDTO loginDto) throws ApplicationServiceException {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        User user = this.userRepository.findByEmail(loginDto.getEmail());
        String token = generateToken.generateTokenJWT(user);

        loginResponseDTO.setEmail(loginDto.getEmail());
        loginResponseDTO.setToken(token);

        return loginResponseDTO;
    }
}
