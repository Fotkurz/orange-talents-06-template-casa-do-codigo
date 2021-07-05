package br.com.zupacademy.guilherme.casadocodigo.validator;

import org.hibernate.jpa.TypedParameterValue;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = UnicoComboValidator.class)
public @interface UniqueCombo {
    String message() default "Testando";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};



}
