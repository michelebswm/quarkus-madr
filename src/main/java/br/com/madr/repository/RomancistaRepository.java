package br.com.madr.repository;

import br.com.madr.domain.Romancista;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RomancistaRepository implements PanacheRepository<Romancista> {
}
