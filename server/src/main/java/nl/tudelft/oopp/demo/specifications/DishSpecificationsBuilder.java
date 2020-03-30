package nl.tudelft.oopp.demo.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.oopp.demo.entities.Dish;

import org.springframework.data.jpa.domain.Specification;

/**
 * Used for creating a compound specification based on multiple parameters.
 */
public class DishSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public DishSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public DishSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    /**
     * Combines all parameters into a Dish specification.
     * @return Dish specification
     */
    public Specification<Dish> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(DishSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
