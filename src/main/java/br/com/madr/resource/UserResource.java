package br.com.madr.resource;

import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.service.UserService;
import br.com.madr.vo.UserVO;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Transactional
    public RestResponse<Void> register(UserVO userVO, @Context UriInfo uriInfo) throws ApplicationServiceException {
        this.userService.register(userVO);
        return RestResponse.created(uriInfo.getAbsolutePath());
    }
}
