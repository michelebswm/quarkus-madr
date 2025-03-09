package br.com.madr.dto.response;

import br.com.madr.domain.Livro;

public class LivroDTOResponse {

    private Long id;
    private Integer ano;
    private String titulo;
    private Long romancista_id;

    public LivroDTOResponse(Long id, Integer ano, String titulo, Long romancista_id) {
        this.id = id;
        this.ano = ano;
        this.titulo = titulo;
        this.romancista_id = romancista_id;
    }

    public static LivroDTOResponse fromEntity(Livro livro) {
        return new LivroDTOResponse(
                livro.getId(),
                livro.getAno(),
                livro.getTitulo(),
                livro.getRomancista().getId()
        );
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

    public Long getRomancista_id() {
        return romancista_id;
    }

    public void setRomancista_id(Long romancista_id) {
        this.romancista_id = romancista_id;
    }

    

}
