package nl.tudelft.oopp.demo.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.tudelft.oopp.demo.entities.Allergy;

import org.springframework.data.jpa.domain.Specification;

/**
 * Used for creating predicates based on criteria to be used for Allergy searching.
 */
public class AllergySpecification implements Specification<Allergy> {
    private SearchCriteria criteria;

    public AllergySpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Allergy> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
