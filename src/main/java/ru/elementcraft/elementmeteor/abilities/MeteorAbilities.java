package ru.elementcraft.elementmeteor.abilities;

import com.projectkorra.projectkorra.ability.FireAbility;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import ru.elementcraft.elementmeteor.Config;

public class MeteorAbilities extends FireAbility {


    private final Location location;

    public MeteorAbilities(Player player, int uses) {
        super(player);
        location = player.getEyeLocation().clone();

        if (!bPlayer.canBend(this) || bPlayer.isOnCooldown("Meteor")) {
            return;
        }

        start();
    }

    @Override
    public void progress() {}

    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 0;
    }

    @Override
    public String getName() {
        return "Meteor";
    }

    @Override
    public Location getLocation() {
        return location;
    }


}
