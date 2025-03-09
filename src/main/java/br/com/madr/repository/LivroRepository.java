package br.com.madr.repository;

import java.util.Optional;

import br.com.madr.domain.Livro;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

@ApplicationScoped
public class LivroRepository implements PanacheRepository<Livro> {

    public Optional<Livro> findByTitulo(String titulo) {
        return find("titulo", titulo).firstResultOptional();
    }

    public void validAndMerge(@Valid Livro livro){
        getEntityManager().merge(livro);
    }
}
