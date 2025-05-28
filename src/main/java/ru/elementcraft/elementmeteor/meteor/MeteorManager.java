package ru.elementcraft.elementmeteor.meteor;

import com.projectkorra.projectkorra.util.TempBlock;
import lombok.AllArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import ru.elementcraft.elementmeteor.Config;
import ru.elementcraft.elementmeteor.ElementMeteor;

import java.util.Collection;


@AllArgsConstructor
public class MeteorManager {

    private final ElementMeteor plugin;


    public void launch(Location location, int uses) {
        final Fireball fireball = location.getWorld().spawn(location, Fireball.class);
        fireball.setYield(0);
        fireball.setVelocity(location.getDirection().toBlockVector());
        fireball.getPersistentDataContainer().set(Config.getKeyMeteor(), PersistentDataType.INTEGER, uses);
    }

    public void explosion(Location location, int uses) {
        final int radius = 10 + uses / 9;
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2f, 1f);


        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    final Location target = location.clone().add(x, y, z);
                    final double distance = location.distance(target);
                    final double chance = (1.1 * radius - distance) / radius;

                    if (chance > Math.random()) {
                        final Block block = target.getBlock();

                        if (block.getType() == Material.AIR || block.getType() == Material.BEDROCK) continue;
                        final TempBlock temp = new TempBlock(block, Material.AIR);
                        Bukkit.getScheduler().runTaskLater(plugin, temp::revertBlock, 60L);

                        final Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, radius, radius, radius);

                        nearbyEntities.forEach(entity -> entity.setVelocity(new Vector(0, 0.6, 0)));
                    }
                }
            }
        }


    }
}