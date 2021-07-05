package br.com.zupacademy.guilherme.casadocodigo.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UnicoComboValidator implements ConstraintValidator<UniqueCombo, Object> {

    @PersistenceContext
    private EntityManager em;
    private String[] combo;


    @Override
    public void initialize(UniqueCombo constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        return false;
    }
}
