package br.com.madr.dto.response;

import br.com.madr.domain.Romancista;
import br.com.madr.vo.RomancistaVO;

public class RomancistaDTOResponse {
    private Long id;
    private String nome;

    public RomancistaDTOResponse(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static RomancistaDTOResponse fromEntity(Romancista romancista){
        return new RomancistaDTOResponse(
                romancista.getId(),
                romancista.getNome()
        );
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
}
