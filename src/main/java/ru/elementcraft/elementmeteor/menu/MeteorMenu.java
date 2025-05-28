package ru.elementcraft.elementmeteor.menu;

import com.projectkorra.projectkorra.BendingPlayer;
import fr.mrmicky.fastinv.FastInv;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.elementcraft.elementmeteor.Config;
import ru.elementcraft.elementmeteor.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class MeteorMenu extends FastInv {
    public MeteorMenu() {
        super(Config.Gui.size, Config.Gui.title);

        final ItemStack meteorStack = new ItemStack(Config.Gui.MeteorButton.material);
        meteorStack.editMeta(meta -> {
            meta.setDisplayName(ColorUtils.translate(Config.Gui.MeteorButton.name));

            final List<String> formatedLore = new ArrayList<>();
            Config.Gui.MeteorButton.lore.forEach(lore -> {
                formatedLore.add(ColorUtils.translate(lore));
            });

            meta.setLore(formatedLore);
        });

        setItem(Config.Gui.MeteorButton.slot, meteorStack, (e) -> {
            final Player p = (Player) e.getWhoClicked();
            final BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(p);
            bPlayer.bindAbility("Meteor");
            p.closeInventory();
        });

        final ItemStack infoStack = new ItemStack(Config.Gui.InfoButton.material);
        infoStack.editMeta(meta -> {
            meta.setDisplayName(ColorUtils.translate(Config.Gui.InfoButton.name));

            final List<String> formatedLore = new ArrayList<>();
            Config.Gui.InfoButton.lore.forEach(lore -> {
                formatedLore.add(ColorUtils.translate(lore));
            });

            meta.setLore(formatedLore);
        });

        setItem(Config.Gui.InfoButton.slot, infoStack);


    }
}
