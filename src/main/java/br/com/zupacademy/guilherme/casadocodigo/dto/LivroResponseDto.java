package br.com.zupacademy.guilherme.casadocodigo.dto;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroResponseDto {

    private Long id;
    private String titulo;


    public LivroResponseDto(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public static List<LivroResponseDto> getLivros(List<Livro> listaLivros) {
        List<LivroResponseDto> lista = new ArrayList<>();
        listaLivros.forEach(e -> {
            lista.add(new LivroResponseDto(e));
        });
        return lista;
    }

}
