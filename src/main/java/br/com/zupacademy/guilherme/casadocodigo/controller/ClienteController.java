package br.com.zupacademy.guilherme.casadocodigo.controller;

import br.com.zupacademy.guilherme.casadocodigo.controller.dto.ClienteResponseDto;
import br.com.zupacademy.guilherme.casadocodigo.controller.dto.ErroDeFormularioDto;
import br.com.zupacademy.guilherme.casadocodigo.controller.form.ClienteForm;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Cliente;
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
@RequestMapping("/clientes")
public class ClienteController {

    @PersistenceContext
    public EntityManager em;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid ClienteForm clienteForm) {
        Cliente cliente = clienteForm.converter(em);
        if(cliente != null) {
            em.persist(cliente);
            return ResponseEntity.ok().body(new ClienteResponseDto(cliente));
        }
        return ResponseEntity.badRequest().body(new ErroDeFormularioDto("estado", "Invalido ou inexistente"));
    }
}
