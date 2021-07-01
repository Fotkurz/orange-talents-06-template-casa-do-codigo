package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Categoria;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Livro;
import br.com.zupacademy.guilherme.casadocodigo.validator.ExistsId;
import br.com.zupacademy.guilherme.casadocodigo.validator.Unique;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LivroForm {

    @NotBlank
    @Unique(fieldName = "titulo", clazz = "Livro")
    private String titulo;
    @NotBlank @Length(max = 500)
    private String resumo;
    private String sumario;
    @NotNull @Range(min = 20)
    private BigDecimal preco;
    @NotNull @Range(min = 100)
    private Integer paginas;
    @NotBlank @Unique(fieldName = "isbn", clazz = "Livro")
    private String isbn;
    @FutureOrPresent
    @NotNull
    private LocalDate dataPublicacao;

    @NotNull @ExistsId(entityName = "Autor", fieldName = "idAutor")
    private Long idAutor;

    @NotNull @ExistsId(entityName = "Categoria", fieldName = "idCategoria")
    private Long idCategoria;

    public LivroForm(String titulo, String resumo, String sumario,
                 BigDecimal preco, Integer paginas, String isbn,
                     LocalDate dataPublicacao, Long idAutor, Long idCategoria) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.paginas = paginas;
        this.isbn = isbn;
        this.idAutor = idAutor;
        this.idCategoria = idCategoria;
        this.dataPublicacao = dataPublicacao;
    }

    public Long getIdAutor() { return idAutor; }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public Livro converter(Autor autor, Categoria categoria) {
        return new Livro(titulo, resumo, sumario, preco, paginas, isbn, dataPublicacao, autor, categoria);
    }
}
