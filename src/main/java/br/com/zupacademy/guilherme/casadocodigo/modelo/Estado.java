package br.com.zupacademy.guilherme.casadocodigo.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_estados", uniqueConstraints = @UniqueConstraint(columnNames = {"pais_id", "nome"}))
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank @Column(unique = true)
    private String nome;

    @ManyToOne
    @NotNull
    private Pais pais;

    public Estado() {}
    public Estado(String nome, Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }

    public boolean pertenceAoPais(Pais pais, EntityManager em) {
        if(em.createQuery("SELECT x FROM Estado x WHERE x.pais.id = " + pais.getId())
        .getResultList().isEmpty()) return false;
        return true;
    }
}
