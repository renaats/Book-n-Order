package nl.tudelft.oopp.demo.specifications;

import nl.tudelft.oopp.demo.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceHelper {
    private static BuildingService buildingService;

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        ServiceHelper.buildingService = buildingService;
    }

    public static BuildingService get() {
        return buildingService;
    }
}
