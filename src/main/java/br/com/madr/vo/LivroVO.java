package br.com.madr.vo;

import br.com.madr.domain.Livro;
import br.com.madr.domain.Romancista;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

import br.com.madr.utils.StringUtils;

@RegisterForReflection
public class LivroVO implements Serializable {
    private static final long serialVersioUID = 1L;

    private Long id;
    private Integer ano;
    private String titulo;
    private RomancistaVO romancista;

    public LivroVO() {
    }

    public LivroVO(Livro livro) {
        this.id = livro.getId();
        this.ano = livro.getAno();
        this.titulo = livro.getTitulo();
        this.romancista = livro.getRomancista() != null ? new RomancistaVO(livro.getRomancista()) : null;
    }

    public Livro toEntity(){
        Livro livro = new Livro();
        livro.setId(this.id != null ? this.id : null);
        livro.setAno(this.ano != null ? this.ano : null);
        livro.setTitulo(this.titulo != null ? StringUtils.sanitizeName(this.titulo) : null);
        Romancista romanc = this.romancista != null ? this.romancista.toEntity() : null;
        livro.setRomancista(romanc);

        return livro;
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

    public RomancistaVO getRomancista() {
        return romancista;
    }

    public void setRomancista(RomancistaVO romancista) {
        this.romancista = romancista;
    }

    @Override
    public String toString() {
        return "LivroVO{" +
                "id=" + id +
                ", ano=" + ano +
                ", titulo='" + titulo + '\'' +
                ", romancista=" + romancista +
                '}';
    }
}
