package br.com.zupacademy.guilherme.casadocodigo.controller;

import br.com.zupacademy.guilherme.casadocodigo.repository.AutorRepository;
import br.com.zupacademy.guilherme.casadocodigo.controller.form.AutorForm;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    /*
    Cadastrando autor no banco por meio de POST
     */
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid AutorForm autorForm) {
        Autor autor = autorForm.converter();
        autorRepository.save(autor);
    }
}
