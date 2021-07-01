package br.com.zupacademy.guilherme.casadocodigo.controller;

import br.com.zupacademy.guilherme.casadocodigo.controller.form.LivroForm;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Categoria;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Livro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid LivroForm livroForm) {

        Livro livro = livroForm.converter(
                em.find(Autor.class, livroForm.getIdAutor()),
                em.find(Categoria.class, livroForm.getIdCategoria()));

        em.persist(livro);

        return ResponseEntity.ok().build();
    }
}
