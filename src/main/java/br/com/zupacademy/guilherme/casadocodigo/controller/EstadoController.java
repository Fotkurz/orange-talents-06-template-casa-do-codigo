package br.com.zupacademy.guilherme.casadocodigo.controller;

import br.com.zupacademy.guilherme.casadocodigo.controller.form.EstadoForm;
import br.com.zupacademy.guilherme.casadocodigo.dto.ErroDeFormularioDto;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Estado;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Pais;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    public ResponseEntity<ErroDeFormularioDto> cadastrar(@RequestBody @Valid EstadoForm estadoForm) {
        Pais pais = em.find(Pais.class, estadoForm.getIdPais());

        if(pais != null) {
            Query query = em.createQuery("SELECT e FROM Estado e WHERE e.pais.id = :pIdPais AND e.nome = :pNome")
                    .setParameter("pIdPais", estadoForm.getIdPais())
                    .setParameter("pNome", estadoForm.getNome());
            if(query.getResultList().isEmpty()) {
                Estado estado = estadoForm.converter(pais);
                em.persist(estado);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body(new ErroDeFormularioDto("nome", estadoForm.getNome() + " já cadastrado"));
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
