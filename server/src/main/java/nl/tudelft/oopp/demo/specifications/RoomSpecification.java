package nl.tudelft.oopp.demo.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.domain.Specification;

/**
 * Used for creating predicates based on criteria to be used for Room searching.
 */
public class RoomSpecification implements Specification<Room> {

    private SearchCriteria criteria;

    public RoomSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Boolean.class) {
                Boolean value = "true".equalsIgnoreCase((String) criteria.getValue()) ? Boolean.TRUE :
                        "false".equalsIgnoreCase((String) criteria.getValue()) ? Boolean.FALSE : null;
                return builder.equal(root.<Boolean>get(criteria.getKey()), value);
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }

}
