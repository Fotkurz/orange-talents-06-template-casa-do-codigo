package br.com.zupacademy.guilherme.casadocodigo.dto;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Categoria;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Livro;

import java.math.BigDecimal;

public class LivroDetalheResponseDto {

    private Long id;
    private String titulo;
    private String resumo;
    private String sumario;
    private BigDecimal preco;
    private Integer paginas;
    private String isbn;

    private AutorDetalheResponseDto autor;
    private CategoriaResponseDto categoria;


    // Implementar os detalhes do Autor aqui também.
    public LivroDetalheResponseDto(Livro livro, Autor autor, Categoria categoria) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.resumo = livro.getResumo();
        this.sumario = livro.getSumario();
        this.preco = livro.getPreco();
        this.paginas = livro.getPaginas();
        this.isbn = livro.getIsbn();
        this.autor = new AutorDetalheResponseDto(autor);
        this.categoria = new CategoriaResponseDto(categoria);
    }


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public String getSumario() {
        return sumario;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public AutorDetalheResponseDto getAutor() {
        return autor;
    }

    public CategoriaResponseDto getCategoria() {
        return categoria;
    }
}
