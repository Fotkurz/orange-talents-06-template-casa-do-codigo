package br.com.zupacademy.guilherme.casadocodigo.controller.dto;

import br.com.zupacademy.guilherme.casadocodigo.modelo.Cliente;

public class ClienteResponseDto {

    private Long id;

    public ClienteResponseDto(Cliente cliente) {
        this.id = cliente.getId();
    }

    public Long getId() {
        return id;
    }
}
