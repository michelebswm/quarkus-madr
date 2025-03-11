package br.com.madr.security.resource;

import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.security.dto.LoginDTO;
import br.com.madr.security.dto.LoginResponseDTO;
import br.com.madr.security.service.LoginService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/login")
public class LoginResource {

    @Inject
    LoginService loginService;

    @POST
    public LoginResponseDTO login(LoginDTO loginDto) throws ApplicationServiceException {
        return loginService.login(loginDto);
    }

}
