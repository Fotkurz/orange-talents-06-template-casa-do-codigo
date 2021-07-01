package br.com.zupacademy.guilherme.casadocodigo.validator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UnicoValidator implements ConstraintValidator<Unique, String> {

    private final EntityManager em;
    private String fieldName;
    private String clazz;

    public UnicoValidator(final EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.clazz = constraintAnnotation.clazz();
    }

    @Override
    public boolean isValid(String campo, ConstraintValidatorContext context) {

        String jpql = "SELECT x FROM " + clazz + " x WHERE x." + fieldName + " = :pCampo";
        Query query = em.createQuery(jpql).setParameter("pCampo", campo);

        List<Object> list = query.getResultList();

        if (list.isEmpty()) {
            return true;
        } else {
            if(fieldName.isEmpty()) {
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addConstraintViolation();
                return false;
            }else{
                String message = campo + " já foi cadastrado.";
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                context.disableDefaultConstraintViolation();
                return false;
            }
        }
    }

}
