package br.com.zupacademy.guilherme.casadocodigo.controller;

import br.com.zupacademy.guilherme.casadocodigo.controller.form.LivroForm;
import br.com.zupacademy.guilherme.casadocodigo.dto.LivroDetalheResponseDto;
import br.com.zupacademy.guilherme.casadocodigo.dto.LivroResponseDto;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Categoria;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Livro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<LivroDetalheResponseDto> detalhar (@PathVariable Long id) {
        Livro livro = em.find(Livro.class, id);
        if (livro != null) {
            Autor autor = em.find(Autor.class, livro.getAutor().getId());
            Categoria categoria = em.find(Categoria.class, livro.getCategoria().getId());
            return ResponseEntity.ok().body(new LivroDetalheResponseDto(livro, autor, categoria));
        }
        return ResponseEntity.notFound().build();
    }

}
