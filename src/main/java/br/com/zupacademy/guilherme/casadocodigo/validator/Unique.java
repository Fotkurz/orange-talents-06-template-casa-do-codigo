package br.com.zupacademy.guilherme.casadocodigo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = UnicoValidator.class)
public @interface Unique {
    String fieldName();
    Class<?> clazz();
    String message() default "Falha na validação de valor único";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
