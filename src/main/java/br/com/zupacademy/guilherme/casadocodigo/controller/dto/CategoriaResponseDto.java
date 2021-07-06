package br.com.zupacademy.guilherme.casadocodigo.controller.dto;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Categoria;

public class CategoriaResponseDto {

    private String nome;

    CategoriaResponseDto (Categoria categoria) {
        this.nome = categoria.getNome();
    }

    public String getNome() {
        return nome;
    }
}
