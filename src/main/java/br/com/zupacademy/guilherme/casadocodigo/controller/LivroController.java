package br.com.zupacademy.guilherme.casadocodigo.controller;

import br.com.zupacademy.guilherme.casadocodigo.controller.form.LivroForm;
import br.com.zupacademy.guilherme.casadocodigo.dto.LivroResponseDto;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Categoria;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Livro;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    @Transactional
    public ResponseEntity<List<LivroResponseDto>> listar() {
        String jpql = "SELECT x FROM Livro x";
        TypedQuery query = em.createQuery(jpql, Livro.class);
        List<Livro> list = query.getResultList();

        if(list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(LivroResponseDto.getLivros(list));
    }
}
