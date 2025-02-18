package br.com.madr.vo;

import br.com.madr.domain.Romancista;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
public class RomancistaVO implements Serializable {
    private static final long serialVersioUID = 1L;

    private Long id;
    private String nome;

    public RomancistaVO() {
    }

    public RomancistaVO(Romancista romancista) {
        this.id = romancista.getId();
        this.nome = romancista.getNome();
    }

    public Romancista toEntity(){
        Romancista romancista = new Romancista();
        romancista.setId(this.id != null ? this.id : null);
        romancista.setNome(this.nome != null ? this.nome : null);
        return romancista;
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

    @Override
    public String toString() {
        return "RomancistaVO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
