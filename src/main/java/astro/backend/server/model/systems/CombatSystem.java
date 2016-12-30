package astro.backend.server.model.systems;

import astro.backend.server.engine.Engine;
import astro.backend.server.engine.GameObject;
import astro.backend.server.model.components.Shield;
import astro.backend.server.model.components.Weapon;
import astro.backend.server.model.entities.Ship;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CombatSystem implements GameObject{

    private static final Logger logger = LogManager.getLogger();

    @Inject
    private Engine engine;


    public void resolveAttack(long attackerId, long targetId){
        Ship attacker = engine.findEntity(Ship.class, attackerId);
        Ship target = engine.findEntity(Ship.class, targetId);
        Weapon weapon = attacker.getComponent(Weapon.class);

        Shield shield = target.getComponent(Shield.class);

        Shield.ShieldBuilder shieldBuilder = new Shield.ShieldBuilder()
                .setShield(shield)
                .setStrength(shield.getStrength() - weapon.getDamage());
        engine.updateComponent(shieldBuilder);


        //remove, for testing purps
        Shield shield1 = engine.findComponent(Shield.class, shield.getId());
        logger.info("{} has taken {} damage, shields at {} ", target.getId(), weapon.getDamage(), shield1.getStrength());
    }


    @Override
    public void initialise() {

    }

    @Override
    public void update(double deltaTime) {

    }
}
