package nl.tudelft.oopp.demo.services;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    BuildingRepository buildingRepository;

    /**
     * Adds a building.
     * @param name = the name of the building
     * @return String to see if your request passed
     */
    public String add(String name, String street, int houseNumber) {
        Building building = new Building();
        building.setName(name);
        building.setStreet(street);
        building.setHouseNumber(houseNumber);
        building.setRooms(new HashSet<>());
        buildingRepository.save(building);
        return "Saved";
    }

    /**
     * Updates a database attribute.
     * @param id = the building id
     * @param attribute = the attribute that is changed
     * @param value = the new value of the attribute
     * @return message if it passes
     */
    public String update(int id, String attribute, String value) {
        if (!buildingRepository.existsById(id)) {
            return "Building with ID: " + id + " Does not exist!";
        }
        Building building = buildingRepository.getOne(id);
        switch (attribute.toLowerCase()) {
            case "name":
                building.setName(value);
                break;
            case "street":
                building.setStreet(value);
                break;
            case "housenumber":
                building.setHouseNumber(Integer.parseInt(value));
                break;
            default:
                System.out.println(attribute);
                return "No attribute with name " + attribute + " found!";
        }
        buildingRepository.save(building);
        return "The attribute has been set!";
    }

    /**
     * Deletes a building.
     * @param id = the id of the building
     * @return String to see if your request passed
     */
    public String delete(int id) {
        if (!buildingRepository.existsById(id)) {
            return "Building with ID: " + id + " Does not exist!";
        }
        if (buildingRepository.findById(id).get().hasRooms()) {
            return "This building cannot be deleted as it has at least one room. Delete the room before deleting the building!";
        }
        buildingRepository.deleteById(id);
        return "Deleted!";
    }

    /**
     * Lists all buildings.
     * @return all buildings
     */
    public Iterable<Building> all() {
        return buildingRepository.findAll();
    }

    /**
     * Finds a building with the specified id.
     * @param id = the id of the building
     * @return a building that matches the id
     */
    public Building find(int id) {
        return buildingRepository.findById(id).orElse(null);
    }

    /**
     * Return all rooms that are in the building with the specified id.
     * @param id = the id of the building
     * @return all rooms that are in the building that matches the id
     */
    public Set<Room> rooms(int id) {
        if (buildingRepository.findById(id).isEmpty()) {
            return null;
        }
        return buildingRepository.findById(id).get().getRooms();
    }
}
