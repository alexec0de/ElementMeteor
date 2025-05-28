package ru.elementcraft.elementmeteor.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ColorUtils {

    public String translate(@NonNull String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
