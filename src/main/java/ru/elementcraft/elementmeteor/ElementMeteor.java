package ru.elementcraft.elementmeteor;

import com.projectkorra.projectkorra.ability.CoreAbility;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import fr.mrmicky.fastinv.FastInvManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.elementcraft.elementmeteor.commands.ElementMeteorCommand;
import ru.elementcraft.elementmeteor.listeners.MeteorListener;
import ru.elementcraft.elementmeteor.meteor.MeteorManager;
import ru.elementcraft.elementmeteor.storage.MariaDBStorage;
import ru.elementcraft.elementmeteor.storage.Storage;


public class ElementMeteor extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Config.load(getConfig(), this);

        final Storage storage = new MariaDBStorage();
        final MeteorManager manager = new MeteorManager(this);
        getServer().getPluginManager().registerEvents(new MeteorListener(storage, manager, this), this);

        CoreAbility.registerPluginAbilities(this, "ru.elementcraft.elementmeteor.abilities");

        FastInvManager.register(this);


        LiteBukkitFactory.builder("elementmeteor")
                .commands(new ElementMeteorCommand())
                .build();
    }
}
