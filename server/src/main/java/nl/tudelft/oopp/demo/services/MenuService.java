package nl.tudelft.oopp.demo.services;

import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Adds a menu to a restaurant
     * @param name menu name
     * @param restaurantId restaurant id
     * @return Error code
     */
    public int add(String name, int restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            return 428;
        }
        Restaurant restaurant = optionalRestaurant.get();

        Menu menu = new Menu();
        menu.setName(name);
        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
        return 201;
    }

    /**
     * Deletes a menu
     * @param id menu id
     * @return Error code
     */
    public int delete(int id) {
        if (!menuRepository.existsById(id)) {
            return 429;
        }
        menuRepository.deleteById(id);
        return 200;
    }
}
