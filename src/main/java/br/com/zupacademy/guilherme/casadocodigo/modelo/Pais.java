package br.com.zupacademy.guilherme.casadocodigo.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_pais")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(unique = true)
    private String nome;

    public Pais (){}
    public Pais(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
