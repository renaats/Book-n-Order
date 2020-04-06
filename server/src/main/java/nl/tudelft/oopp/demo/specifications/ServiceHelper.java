package nl.tudelft.oopp.demo.specifications;

import nl.tudelft.oopp.demo.services.BuildingService;

import nl.tudelft.oopp.demo.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Responsible for allowing wiring beans from the
 * specification package to the building service.
 */
@Service
public class ServiceHelper {
    private static BuildingService buildingService;
    private static MenuService menuService;

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        ServiceHelper.buildingService = buildingService;
    }

    @Autowired
    public void setMenuService(MenuService menuService) {
        ServiceHelper.menuService = menuService;
    }

    public static BuildingService getBuilding() {
        return buildingService;
    }

    public static MenuService getMenu() {
        return menuService;
    }
}
