package nl.tudelft.oopp.demo.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.services.MenuService;

import org.springframework.data.jpa.domain.Specification;

/**
 * Used for creating predicates based on criteria to be used for Dish searching.
 */
public class DishSpecification implements Specification<Dish> {
    private SearchCriteria criteria;
    private MenuService menuService;

    public DishSpecification(SearchCriteria criteria) {
        menuService = ServiceHelper.getMenu();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Dish> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Menu.class) {
                return builder.equal(root.<Menu>get(criteria.getKey()), menuService.find(Integer.parseInt((String) criteria.getValue())));
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
