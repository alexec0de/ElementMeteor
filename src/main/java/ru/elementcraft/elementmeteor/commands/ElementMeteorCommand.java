package ru.elementcraft.elementmeteor.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.elementcraft.elementmeteor.menu.MeteorMenu;

@Command(name = "elementmeteor")
@Permission("elementcraft.menu")
public class ElementMeteorCommand {

    @Execute
    public void execute(@Sender CommandSender sender) {
        if (sender instanceof Player p) {
            new MeteorMenu().open(p);
        }
    }

}
