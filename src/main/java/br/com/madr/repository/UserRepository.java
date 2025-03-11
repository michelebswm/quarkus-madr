package br.com.madr.repository;

import br.com.madr.domain.User;
import br.com.madr.exception.ApplicationServiceException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByUsername(String username){
        return find("username", username).firstResultOptional();
    }

    public User findByEmail(String email) throws ApplicationServiceException{
        return find("email", email).firstResultOptional().orElseThrow(
            () -> new ApplicationServiceException("message.forbidden", Response.Status.FORBIDDEN.getStatusCode())
        );
    }

    public void validAndMerge(@Valid User user) {
        getEntityManager().merge(user);
    }
}
