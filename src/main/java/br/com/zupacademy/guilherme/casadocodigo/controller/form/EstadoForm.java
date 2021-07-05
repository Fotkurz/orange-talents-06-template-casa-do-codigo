package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Estado;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Pais;
import br.com.zupacademy.guilherme.casadocodigo.validator.ExistsId;
import br.com.zupacademy.guilherme.casadocodigo.validator.UniqueCombo;
import org.hibernate.jpa.TypedParameterValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EstadoForm {
    @NotBlank
    private String nome;
    @NotNull
    @ExistsId(entityName = "Pais")
    private Integer idPais;

    public EstadoForm(String nome, Integer idPais) {
        this.nome = nome;
        this.idPais = idPais;
    }

    public Estado converter(Pais pais) {
        return new Estado(nome, pais);
    }

    public Integer getIdPais() {
        return this.idPais;
    }

    public String getNome() {
        return this.nome;
    }
}
