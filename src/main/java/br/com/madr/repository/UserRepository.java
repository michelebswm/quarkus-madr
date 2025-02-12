package br.com.madr.repository;

import br.com.madr.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByUsername(String username){
        return find("username", username).firstResultOptional();
    }

    public void validAndMerge(@Valid User user) {
        getEntityManager().merge(user);
    }
}
