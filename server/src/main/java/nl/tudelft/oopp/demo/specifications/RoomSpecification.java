package nl.tudelft.oopp.demo.specifications;

import javax.persistence.criteria.*;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;

import nl.tudelft.oopp.demo.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Used for creating predicates based on criteria to be used for Room searching.
 */
public class RoomSpecification implements Specification<Room> {

    @Bean
    public BuildingService buildingService() {
        return new BuildingService();
    }

    @Autowired
    private BuildingService buildingService;

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
            } else if (root.get(criteria.getKey()).getJavaType() == Building.class) {
                    return builder.equal(root.<Building>get(criteria.getKey()), buildingService.find(Integer.parseInt((String) criteria.getValue())));
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
