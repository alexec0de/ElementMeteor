package ru.elementcraft.elementmeteor.listeners;

import com.projectkorra.projectkorra.BendingPlayer;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import ru.elementcraft.elementmeteor.Config;
import ru.elementcraft.elementmeteor.ElementMeteor;
import ru.elementcraft.elementmeteor.data.User;
import ru.elementcraft.elementmeteor.meteor.MeteorManager;
import ru.elementcraft.elementmeteor.storage.Storage;

@AllArgsConstructor
public class MeteorListener implements Listener {

    private final Storage storage;
    private final MeteorManager meteorManager;
    private final ElementMeteor plugin;

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR) return;

        final Player player = event.getPlayer();
        final String name = player.getName();
        final BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (!bPlayer.getBoundAbilityName().equals("Meteor")) return;

        storage.getUser(name).thenAccept((user) -> {
            if (user == null) {
                user = new User(name, 1);
                storage.addUser(user);
            }
            final int uses = user.getUses();
            Bukkit.getScheduler().runTask(plugin, () -> meteorManager.launch(player.getEyeLocation(), uses));
            user.setUses(user.getUses() + 1);
            storage.editUser(user);
        });
    }

    @EventHandler
    public void on(ProjectileHitEvent event) {
        final Projectile projectile = event.getEntity();

        if (!(projectile instanceof Fireball)) return;
        if (!projectile.getPersistentDataContainer().has(Config.getKeyMeteor(), PersistentDataType.INTEGER)) return;

        final int uses = projectile.getPersistentDataContainer().get(Config.getKeyMeteor(), PersistentDataType.INTEGER);

        meteorManager.explosion(projectile.getLocation(), uses);


    }

}
