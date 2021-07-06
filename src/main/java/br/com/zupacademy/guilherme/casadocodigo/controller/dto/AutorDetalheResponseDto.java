package br.com.zupacademy.guilherme.casadocodigo.controller.dto;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;

public class AutorDetalheResponseDto {

    private String nome;
    private String email;
    private String descricao;

    public AutorDetalheResponseDto(Autor autor) {
        this.nome = autor.getNome();
        this.email = autor.getEmail();
        this.descricao = autor.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDescricao() {
        return descricao;
    }
}
