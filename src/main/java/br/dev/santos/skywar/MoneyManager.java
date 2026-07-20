package br.dev.santos.skywar;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MoneyManager {
    private JavaPlugin plugin;
    private File moneyFile;
    private FileConfiguration moneyConfig;

    public MoneyManager(JavaPlugin plugin) {
        this.plugin = plugin;
        moneyFile = new File(plugin.getDataFolder(), "money.yml");
        if (!moneyFile.exists()) {
            plugin.saveResource("money.yml", false);
        }
        moneyConfig = YamlConfiguration.loadConfiguration(moneyFile);
    }

    // Salva o dinheiro do jogador
    public void setMoney(Player player, int amount) {
        moneyConfig.set("players." + player.getUniqueId().toString(), amount);
        saveMoneyConfig();
    }

    // Pega o dinheiro do jogador
    public int getMoney(Player player) {
        return moneyConfig.getInt("players." + player.getUniqueId().toString(), 0);
    }

    // Adiciona uma quantidade de dinheiro
    public void addMoney(Player player, int amount) {
        int currentMoney = getMoney(player);
        setMoney(player, currentMoney + amount);
    }

    // Salva as mudanças no arquivo YAML
    private void saveMoneyConfig() {
        try {
            moneyConfig.save(moneyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega o arquivo de configuração
    public void reloadMoneyConfig() {
        moneyConfig = YamlConfiguration.loadConfiguration(moneyFile);
    }
}
