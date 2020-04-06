package nl.tudelft.oopp.demo.specifications;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.not;

import java.util.List;

import javax.transaction.Transactional;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;

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

    /**
     * Initializes variables before each test.
     */
    @BeforeEach
    public void setup() {
        Building building = new Building();
        building.setHouseNumber(1);
        building.setName("Neo-Aula");
        building.setStreet("Straatlaanweg");
        buildingRepository.save(building);

        roomAlpha = new Room();
        roomAlpha.setBuilding(building);
        roomAlpha.setStudySpecific("CSE");
        roomAlpha.setName("Auditorium");
        roomAlpha.setCapacity(500);
        roomAlpha.setPlugs(0);
        roomAlpha.setProjector(true);
        roomAlpha.setScreen(true);
        roomRepository.save(roomAlpha);

        roomBeta = new Room();
        roomBeta.setBuilding(building);
        roomBeta.setStudySpecific("CSE");
        roomBeta.setName("CZ A");
        roomBeta.setCapacity(250);
        roomBeta.setPlugs(125);
        roomBeta.setProjector(true);
        roomBeta.setScreen(false);
        roomRepository.save(roomBeta);
    }

    /**
     * Tests querying on a name of the room.
     */
    @Test
    public void testNameSearch() {
        RoomSpecification spec = new RoomSpecification(new SearchCriteria("name", ":", "Auditorium"));
        List<Room> results = roomRepository.findAll(spec);
        assertThat(roomAlpha, in(results));
        assertThat(roomBeta, not(in(results)));
    }

    /**
     * Tests querying on the number of plugs of a room.
     */
    @Test
    public void testAtLeast10Plugs() {
        RoomSpecification spec = new RoomSpecification(new SearchCriteria("plugs", ">", 10));
        List<Room> results = roomRepository.findAll(spec);
        assertThat(roomAlpha, not(in(results)));
        assertThat(roomBeta, in(results));
    }

    /**
     * Tests querying on the studySpecific and capacity of a room.
     */
    @Test
    public void testStudySpecificAndCapacity() {
        RoomSpecification spec1 = new RoomSpecification(new SearchCriteria("capacity", ">", 250));
        RoomSpecification spec2 = new RoomSpecification(new SearchCriteria("studySpecific", ":", "CSE"));
        List<Room> results = roomRepository.findAll(spec1.and(spec2));
        assertThat(roomAlpha, in(results));
        assertThat(roomBeta, in(results));
    }

    /**
     * Tests querying on the name and capacity of a room.
     */
    @Test
    public void testSearch() {
        RoomSpecification spec1 = new RoomSpecification(new SearchCriteria("name", ":","Auditorium"));
        RoomSpecification spec2 = new RoomSpecification(new SearchCriteria("capacity", ">","10"));
        List<Room> results = roomRepository.findAll(spec1.and(spec2));
        assertThat(roomAlpha, in(results));
        assertThat(roomBeta, not(in(results)));
    }

    /**
     * Tests querying on the name and the boolean screen of a room.
     */
    @Test
    public void testBoolean() {
        RoomSpecification spec1 = new RoomSpecification(new SearchCriteria("name", ":","Auditorium"));
        RoomSpecification spec2 = new RoomSpecification(new SearchCriteria("screen", ":","true"));
        List<Room> results = roomRepository.findAll(spec1.and(spec2));
        assertThat(roomAlpha, in(results));
        assertThat(roomBeta, not(in(results)));
    }
}

