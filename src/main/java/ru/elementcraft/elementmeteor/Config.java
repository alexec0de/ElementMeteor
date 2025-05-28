package ru.elementcraft.elementmeteor;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Config {

    @Getter
    private static NamespacedKey keyMeteor;
    private static FileConfiguration config;
    private static final Logger logger = Bukkit.getLogger();


    public static void load(FileConfiguration c, ElementMeteor plugin) {
        config = c;
        parseMariaDB();
        parseGui();
        keyMeteor = new NamespacedKey(plugin, "meteor");
    }

    private static void parseMariaDB() {
        final ConfigurationSection mariadbSection = config.getConfigurationSection("mariadb");
        isSectionNotNull(mariadbSection, "mariadb");
        Mariadb.database = mariadbSection.getString("database");
        Mariadb.host = mariadbSection.getString("host");
        Mariadb.user = mariadbSection.getString("user");
        Mariadb.password = mariadbSection.getString("password");
        Mariadb.port = mariadbSection.getInt("port");
    }

    private static void parseGui() {
        final ConfigurationSection section = config.getConfigurationSection("gui");
        isSectionNotNull(section, "gui");
        Gui.title = section.getString("title");
        Gui.size = section.getInt("size");

        final ConfigurationSection meteorButtonSection = section.getConfigurationSection("buttons.meteor");
        isSectionNotNull(meteorButtonSection, "buttons.meteor");
        Gui.MeteorButton.name = meteorButtonSection.getString("name");
        Gui.MeteorButton.material = Material.getMaterial(meteorButtonSection.getString("material"));
        Gui.MeteorButton.lore = meteorButtonSection.getStringList("lore");
        Gui.MeteorButton.slot = meteorButtonSection.getInt("slot");

        final ConfigurationSection infoButtonSection = section.getConfigurationSection("buttons.info-meteor");
        isSectionNotNull(infoButtonSection, "buttons.info-meteor");
        Gui.InfoButton.name = infoButtonSection.getString("name");
        Gui.InfoButton.material = Material.getMaterial(infoButtonSection.getString("material"));
        Gui.InfoButton.lore = infoButtonSection.getStringList("lore");
        Gui.InfoButton.slot = infoButtonSection.getInt("slot");

    }

    private static void isSectionNotNull(ConfigurationSection section, String sectionName) {
        if (section == null) throw new RuntimeException("Configuration section '"+ sectionName +"' not found");
    }


    public static class Mariadb {
        public static String host, database, user, password;
        public static int port;
    }

    public static class Gui {
        public static String title;
        public static int size;
        public static class MeteorButton {
            public static String name;
            public static Material material;
            public static List<String> lore;
            public static int slot;
        }
        public static class InfoButton {
            public static String name;
            public static Material material;
            public static List<String> lore;
            public static int slot;
        }
    }

}
