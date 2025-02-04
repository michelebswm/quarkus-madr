package br.com.madr.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "livro", schema = "madr")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "titulo", nullable = false, length = 500)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "romancista", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "livro_romancista_fk"))
    private Romancista romancista;

    public Livro() {
    }

    public Livro(Long id, Integer ano, String titulo, Romancista romancista) {
        this.id = id;
        this.ano = ano;
        this.titulo = titulo;
        this.romancista = romancista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Romancista getRomancista() {
        return romancista;
    }

    public void setRomancista(Romancista romancista) {
        this.romancista = romancista;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id) && Objects.equals(ano, livro.ano) && Objects.equals(titulo, livro.titulo) && Objects.equals(romancista, livro.romancista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ano, titulo, romancista);
    }
}
