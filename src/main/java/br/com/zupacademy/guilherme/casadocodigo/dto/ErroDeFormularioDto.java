package br.com.zupacademy.guilherme.casadocodigo.dto;


public class ErroDeFormularioDto {
    private final String campo;
    private final String message;

    public ErroDeFormularioDto(String campo, String message) {
        this.campo = campo;
        this.message = message;
    }

    public String getCampo() {
        return campo;
    }

    public String getMessage() {
        return message;
    }
}
