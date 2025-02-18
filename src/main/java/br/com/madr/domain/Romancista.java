package br.com.madr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "romancista",
        schema = "madr",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"nome"},
                        name = "uk_nome_romancista"
                )
        }
)
public class Romancista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 500)
    @NotNull(message = "{romancista.nome.notnull}")
    @Size(min = 5, max = 500, message = "{romancista.nome.size}")
    private String nome;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "romancista")
    private Set<Livro> livros;

    public Romancista() {
    }

    public Romancista(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Romancista(Long id, String nome, Set<Livro> livros) {
        this.id = id;
        this.nome = nome;
        this.livros = livros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Romancista that = (Romancista) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Romancista{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", livros=" + livros +
                '}';
    }
}
