package br.com.zupacademy.guilherme.casadocodigo.validator;



import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExisteIdValidator implements ConstraintValidator<ExistsId, Number>{

    private final EntityManager em;
    private String entity;
    private String fieldName;

    public ExisteIdValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(ExistsId constraintAnnotation) {
        this.entity = constraintAnnotation.entityName();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        String jpql = "SELECT x FROM " + entity + " x WHERE x.id = :pValue";
        System.out.println(entity);
        System.out.println(fieldName);
        Query query = em.createQuery(jpql)
                .setParameter("pValue", value);
        List<Object> list = query.getResultList();
        if (list.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(entity + " inexistente!").addConstraintViolation();
            return false;
        }
        return true;
    }

}
