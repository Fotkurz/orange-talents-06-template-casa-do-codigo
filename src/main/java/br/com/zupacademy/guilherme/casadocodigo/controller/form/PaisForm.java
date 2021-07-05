package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Pais;
import br.com.zupacademy.guilherme.casadocodigo.validator.Unique;

import javax.validation.constraints.NotBlank;

public class PaisForm {

    @NotBlank @Unique(fieldName = "nome", clazz = "Pais")
    private String nome;

    public PaisForm(){}

    public PaisForm(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Pais converter () {
        return new Pais(nome);
    }

}
