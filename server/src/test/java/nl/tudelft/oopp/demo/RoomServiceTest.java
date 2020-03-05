package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.services.BuildingService;
import nl.tudelft.oopp.demo.services.BuildingServiceImpl;
import nl.tudelft.oopp.demo.services.RoomService;
import nl.tudelft.oopp.demo.services.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoomServiceTest {
    @TestConfiguration
    static class BuildingServiceTestConfiguration {
        @Bean
        public BuildingService buildingService() {
            return new BuildingServiceImpl();
        }
    }

    @TestConfiguration
    static class RoomServiceTestConfiguration {
        @Bean
        public RoomService roomService() {
            return new RoomServiceImpl();
        }
    }

    @Autowired
    RoomService roomService;

    @Autowired
    BuildingService buildingService;


    Building building;
    Building building2;
    Room room;
    Room room2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        buildingService.add("EWI", "Mekelweg", 4);
        buildingService.add("EWI2", "Mekelweg2", 42);

        List<Building> buildings = new ArrayList<>();
        buildingService.all().forEach(buildings::add);
        building = buildings.get(0);
        building2 = buildings.get(1);

        room = new Room();
        room.setName("Ampere");
        room.setBuilding(building);
        room.setFaculty("EWI");
        room.setFacultySpecific(false);
        room.setScreen(true);
        room.setProjector(true);
        room.setNrPeople(200);
        room.setPlugs(200);

    }

    @Test
    public void testCreate() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        assertEquals(Collections.singletonList(room), roomService.all());
    }

    @Test
    public void testUpdate() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        roomService.add("Auditorium", "Aula", true, false, false, building2.getId(), 500, 100);
        List<Room> rooms = new ArrayList<>();
        roomService.all().forEach(rooms::add);
        assertEquals(2, rooms.size());
        room = rooms.get(0);
        room2 = rooms.get(1);

        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "name", room.getName());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "faculty", room.getFaculty());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "facultyspecific", ((Boolean) room.isFacultySpecific()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "screen", ((Boolean) room.isScreen()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "projector", ((Boolean) room.isProjector()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "buildingid", ((Integer) room.getBuilding().getId()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "amountofpeople", ((Integer) room.getNrPeople()).toString());
        assertNotEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
        roomService.update(room2.getId(), "plugs", ((Integer) room.getPlugs()).toString());
        assertEquals(roomService.find(room.getId()), roomService.find(room2.getId()));
    }

    @Test
    public void testDelete() {
        roomService.add("Ampere", "EWI", false, true, true, building.getId(), 200, 200);
        roomService.add("Auditorium", "Aula", true, false, false, building2.getId(), 500, 100);
        List<Room> rooms = new ArrayList<>();
        roomService.all().forEach(rooms::add);
        assertEquals(2, rooms.size());
        roomService.delete(rooms.get(0).getId());
        rooms = new ArrayList<>();
        roomService.all().forEach(rooms::add);
        assertEquals(1, rooms.size());
    }
}