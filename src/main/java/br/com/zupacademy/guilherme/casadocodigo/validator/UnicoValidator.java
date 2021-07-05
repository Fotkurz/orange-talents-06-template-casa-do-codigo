package br.com.zupacademy.guilherme.casadocodigo.validator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnicoValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager em;
    private String fieldName;
    private String clazz;
    private List<String> combo = new ArrayList<>();

    public UnicoValidator(final EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.clazz = constraintAnnotation.clazz();
        if(Arrays.stream(constraintAnnotation.combo()).count() != 0) {
            for (String combo: constraintAnnotation.combo()) {
                this.combo.add(combo);
            }
        }
    }

    @Override
    public boolean isValid(Object campo, ConstraintValidatorContext context) {
        String jpql = "SELECT x FROM " + clazz + " x WHERE x." + fieldName + " = :pCampo";
        Query query = em.createQuery(jpql).setParameter("pCampo", campo);
        List<Object> list = query.getResultList();
        if (list.isEmpty()) {
            return true;
        } else {
            if(fieldName.isEmpty()) {
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addConstraintViolation();
            }else{
                String message = campo + " já foi cadastrado.";
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                context.disableDefaultConstraintViolation();
            }
            return false;
        }
    }

}
