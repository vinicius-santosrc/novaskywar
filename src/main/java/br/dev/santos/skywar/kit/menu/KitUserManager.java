package br.dev.santos.skywar.kit.menu;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.KitManager;
import br.dev.santos.skywar.player.PlayerData;

public class KitUserManager {

    private final JavaPlugin plugin;
    private final File userKitsFile;
    private final FileConfiguration kitConfig;
    private final KitManager kitManager;

    public KitUserManager(JavaPlugin plugin, KitManager kitManager) {
        this.plugin = plugin;
        this.userKitsFile = new File(plugin.getDataFolder(),"userKits.yml");

        if (!this.userKitsFile.exists()) {
            plugin.saveResource("userKits.yml", false);
        }

        this.kitConfig = YamlConfiguration.loadConfiguration(
                this.userKitsFile);
        this.kitManager = kitManager;
    }

    public void addKit(
            PlayerData playerData,
            Kit kit) {

        String path = "players." + playerData.getPlayerEntity().getName();
        List<String> kits = this.kitConfig.getStringList(path);

        if (kits.contains(kit.getName())) {
            return;
        }

        kits.add(kit.getName());
        this.kitConfig.set(path, kits);

        save();
    }

    public List<String> getUserAllKits(
            PlayerData playerData) {

        String path = "players." + playerData.getPlayerEntity().getName();

        return this.kitConfig.getStringList(path);
    }

    public Map<String, Kit> getAllByUser(PlayerData playerData) {
        Map<String, Kit> kits = new HashMap<String, Kit>();

        for (String kitName : this.getUserAllKits(playerData)) {
            Kit kit = this.kitManager.get(kitName);

            if (kit != null) {
                kits.put(kitName, kit);
            }
        }

        return kits;
    }

    private void save() {
        try {
            this.kitConfig.save(this.userKitsFile);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}