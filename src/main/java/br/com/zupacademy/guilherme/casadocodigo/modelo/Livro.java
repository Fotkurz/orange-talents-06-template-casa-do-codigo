package br.com.zupacademy.guilherme.casadocodigo.modelo;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(unique = true)
    private String titulo;
    @NotBlank @Column(length = 500)
    private String resumo;
    @Column(length = 3000)
    private String sumario;
    @NotNull @Range(min = 20)
    private BigDecimal preco;
    @NotNull @Range(min = 100)
    private Integer paginas;
    @NotBlank @Column(unique = true)
    private String isbn;
    @NotNull
    private LocalDate dataPublicacao;

    @NotNull @ManyToOne
    private Categoria categoria;

    @NotNull @ManyToOne
    private Autor autor;

    public Livro(){}

    public Livro(String titulo, String resumo, String sumario,
                 BigDecimal preco, Integer paginas, String isbn,
                 LocalDate dataPublicacao, Autor autor, Categoria categoria) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.paginas = paginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.autor = autor;
        this.categoria = categoria;
    }

}
