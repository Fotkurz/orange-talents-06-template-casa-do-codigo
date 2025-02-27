package br.com.zupacademy.guilherme.casadocodigo.controller;

import br.com.zupacademy.guilherme.casadocodigo.controller.form.PaisForm;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Pais;
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
@RequestMapping("/paises")
public class PaisController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PaisForm form) {
        Pais pais = form.converter();
        em.persist(pais);
        return ResponseEntity.ok().build();
    }

}
