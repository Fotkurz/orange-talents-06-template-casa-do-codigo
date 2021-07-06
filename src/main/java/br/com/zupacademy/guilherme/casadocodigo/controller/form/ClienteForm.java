package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Cliente;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Estado;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Pais;
import br.com.zupacademy.guilherme.casadocodigo.validator.ExistsId;
import br.com.zupacademy.guilherme.casadocodigo.validator.Unique;
import br.com.zupacademy.guilherme.casadocodigo.validator.ValidaDoc;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClienteForm {
    @NotBlank @Email @Unique(fieldName = "email", clazz = "Cliente")
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotBlank @Unique(fieldName = "document", clazz = "Cliente") @ValidaDoc
    private String document;
    @NotBlank
    private String endereco;
    @NotBlank
    private String complemento;
    @NotBlank
    private String cidade;
    @NotNull @ExistsId(entityName = "Pais")
    private Integer idPais;
    @ExistsId(entityName = "Estado")
    private Integer idEstado;
    @NotBlank
    private String telefone;
    @NotBlank
    private String cep;

    public ClienteForm(){}

    public ClienteForm(String email, String nome, String sobrenome,
                       String document, String endereco, String complemento,
                       String cidade, Integer pais,
                       String telefone, String cep) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.document = document;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.idPais = pais;
        this.telefone = telefone;
        this.cep = cep;
    }

    public ClienteForm(String email, String nome, String sobrenome,
                       String document, String endereco, String complemento,
                       String cidade, Integer pais, Integer estado,
                       String telefone, String cep) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.document = document;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.idPais = pais;
        this.idEstado = estado;
        this.telefone = telefone;
        this.cep = cep;
    }

    public Cliente converter(EntityManager em) {
        Pais pais = em.find(Pais.class, idPais);
        if(idEstado != null) {
            Estado estado = em.find(Estado.class, idEstado);
            if(estado.pertenceAoPais(pais,em)) {
                Cliente cliente = new Cliente(email, nome, sobrenome, document, endereco,
                        complemento, cidade, pais, estado, telefone, cep);
                return cliente;
            }

        } else {
            if(pais.ExisteEstadoNoPais(em)) {
                return null;
            }
            Cliente cliente = new Cliente(email, nome, sobrenome, document, endereco,
                    complemento, cidade, pais, telefone, cep);
            return cliente;
        }
        return null;
    }


    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getDocument() {
        return document;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public Integer getIdPais() {
        return this.idPais;
    }

    public Integer getIdEstado() {
        return this.idEstado;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }
//
}
