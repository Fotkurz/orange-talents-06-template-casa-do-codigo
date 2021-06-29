package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailUnicoValidator.class)
public @interface Unique {
    String message() default "Email já cadastrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
