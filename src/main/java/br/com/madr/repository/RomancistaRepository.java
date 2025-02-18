package br.com.madr.repository;

import br.com.madr.domain.Romancista;
import br.com.madr.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class RomancistaRepository implements PanacheRepository<Romancista> {

    public Optional<Romancista> findByNome(String nome){
        return find("nome", nome).firstResultOptional();
    }
}
