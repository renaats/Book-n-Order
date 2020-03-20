package nl.tudelft.oopp.demo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.not;

import java.util.List;
import javax.transaction.Transactional;
import nl.tudelft.oopp.demo.controllers.RoomController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.specifications.RoomSpecification;
import nl.tudelft.oopp.demo.specifications.SearchCriteria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Transactional
@DataJpaTest
public class RoomsJpaSpecificationsTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    private Room roomAlpha;
    private Room roomBeta;
    private Building building;

    /**
     * Initializes variables before each test.
     */
    @BeforeEach
    public void init() {

        building = new Building();
        building.setHouseNumber(1);
        building.setName("Neo-Aula");
        building.setStreet("Straatlaanweg");
        buildingRepository.save(building);

        roomAlpha = new Room();
        roomAlpha.setBuilding(building);
        roomAlpha.setFaculty("EWI");
        roomAlpha.setFacultySpecific(true);
        roomAlpha.setName("Auditorium");
        roomAlpha.setNrPeople(500);
        roomAlpha.setPlugs(0);
        roomAlpha.setProjector(true);
        roomAlpha.setScreen(true);
        roomRepository.save(roomAlpha);

        roomBeta = new Room();
        roomBeta.setBuilding(building);
        roomBeta.setFaculty("EWI");
        roomBeta.setFacultySpecific(true);
        roomBeta.setName("CZ A");
        roomBeta.setNrPeople(250);
        roomBeta.setPlugs(125);
        roomBeta.setProjector(true);
        roomBeta.setScreen(false);
        roomRepository.save(roomBeta);

    }

    @Test
    public void nameSearchTest() {
        RoomSpecification spec = new RoomSpecification(new SearchCriteria("name", ":", "Auditorium"));

        List<Room> results = roomRepository.findAll(spec);

        assertThat(roomAlpha, in(results));
        assertThat(roomBeta, not(in(results)));
    }

    @Test
    public void atLeast10Plugs() {
        RoomSpecification spec = new RoomSpecification(new SearchCriteria("plugs", ">", 10));

        List<Room> results = roomRepository.findAll(spec);

        assertThat(roomAlpha, not(in(results)));
        assertThat(roomBeta, in(results));
    }

    @Test
    public void facultyAndSeats() {
        RoomSpecification spec1 = new RoomSpecification(new SearchCriteria("nrPeople", ">", 250));
        RoomSpecification spec2 = new RoomSpecification(new SearchCriteria("faculty", ":", "EWI"));

        List<Room> results = roomRepository.findAll(spec1.and(spec2));

        assertThat(roomAlpha, in(results));
        assertThat(roomBeta, in(results));
    }

    @Test
    public void searchTest() {
        List<Room> results = RoomController.search("name:Auditorium,nrPeople>10", roomRepository);

        assertThat(roomAlpha, in(results));
        assertThat(roomBeta, not(in(results)));

    }

}

