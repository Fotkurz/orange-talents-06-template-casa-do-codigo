package br.com.zupacademy.guilherme.casadocodigo.controller.form;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EmailUnicoValidator implements ConstraintValidator<Unique, String> {

    private final EntityManager em;

    private Unique unique;

    public EmailUnicoValidator(final EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.unique = constraintAnnotation;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        String jpql = "SELECT a FROM Autor a WHERE a.email = :pEmail";
        Query query = em.createQuery(jpql).setParameter("pEmail", email);
        List<Object> list = query.getResultList();
        if(list.isEmpty()) {
            return true;
        } else {
            context.buildConstraintViolationWithTemplate(unique.message());
            return false;
        }
    }

}
