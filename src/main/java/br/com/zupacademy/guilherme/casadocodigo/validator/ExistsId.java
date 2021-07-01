package br.com.zupacademy.guilherme.casadocodigo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ExisteIdValidator.class)
public @interface ExistsId {
    String entityName();
    String fieldName();
    String message() default "Entidade não encontrada";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
