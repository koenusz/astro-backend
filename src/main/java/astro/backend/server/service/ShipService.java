package astro.backend.server.service;

import astro.backend.server.model.entities.Ship;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;

import java.io.File;
import java.io.IOException;

@Singleton
public class ShipService {


    ObjectMapper mapper = new ObjectMapper();

    Ship[] ships;

    int id = 0;

    public ShipService() {
        File file = new File(this.getClass().getResource("/ships.json").getFile());
        try {
            ships = mapper.readValue(file, Ship[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public ActionEvent getAction(){
//        int ship = new Double(Math.random() * 2.999).intValue();
//
//        Ship proto = ships[ship];
//
//        long entityId = 1;
//
//        List<Component> components = new ArrayList<>();
//        components.add(new Shield(entityId, 2));
//        components.add(new Weapon(entityId, 1));
//        components.add(new Armor(entityId,2));
//        components.add(new Hull(entityId, 5));
//        Ship newShip = new Ship(entityId, components);
//
//        ActionEvent action = new ShipAction("SHIP_ADD_NAME", mapper.valueToTree(newShip));
//        id++;
//        return action;
//    }
}
