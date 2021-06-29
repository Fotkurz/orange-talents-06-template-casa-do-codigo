package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AutorForm {

    @NotNull @NotEmpty
    private String nome;
    @NotNull @NotEmpty @Email @Unique
    private String email;
    @NotNull @NotEmpty @Length(max = 400)
    private String descricao;

    public AutorForm(@NotNull @NotEmpty String nome,
                     @NotNull @NotEmpty @Email @Unique String email,
                     @NotNull @NotEmpty @Length(max = 400) String descricao) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public Autor converter() {
        return new Autor(nome, email, descricao);
    }

}
