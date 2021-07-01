package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import br.com.zupacademy.guilherme.casadocodigo.validator.Unique;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AutorForm {

    @NotNull @NotEmpty
    private final String nome;
    @NotNull @NotEmpty @Email @Unique(fieldName = "email", clazz = "Autor")
    private final String email;
    @NotNull @NotEmpty @Length(max = 400)
    private final String descricao;

    public AutorForm(@NotNull @NotEmpty String nome,
                     @NotNull @NotEmpty @Email @Unique(fieldName = "email", clazz = "Autor") String email,
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
