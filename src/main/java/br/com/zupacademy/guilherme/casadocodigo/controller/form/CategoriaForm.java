package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Categoria;
import br.com.zupacademy.guilherme.casadocodigo.validator.Unique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoriaForm {

    @NotNull
    @NotEmpty
    @Unique(fieldName = "nome", clazz = Categoria.class)
    private String nome;

    public CategoriaForm(){}

    public CategoriaForm(String nome) {
        this.nome = nome;
    }

    public Categoria converter() {
        return new Categoria(nome);
    }

    public String getNome() {
        return nome;
    }
}
