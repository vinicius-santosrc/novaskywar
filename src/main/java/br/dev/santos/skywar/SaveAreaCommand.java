package br.dev.santos.skywar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileWriter;
import java.io.IOException;

public class SaveAreaCommand implements CommandExecutor {

    private JavaPlugin plugin;
    private Gson gson;

    public SaveAreaCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = new Gson();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Use: /salvararea <nome>");
            return false;
        }

        String areaName = args[0];
        Location loc1 = player.getLocation();
        Location loc2 = player.getLocation().add(50, 50, 50);

        JsonArray blocksArray = new JsonArray();

        int maxSize = 50;
        int count = 0;

        for (int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
            for (int y = loc1.getBlockY(); y <= loc2.getBlockY(); y++) {
                for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                    if (count >= maxSize) {
                        player.sendMessage("Área muito grande para salvar. Limite de " + maxSize + " blocos alcançado.");
                        return true;
                    }

                    Location blockLocation = new Location(player.getWorld(), x, y, z);
                    Block block = blockLocation.getBlock();

                    JsonObject blockData = new JsonObject();
                    blockData.addProperty("x", x);
                    blockData.addProperty("y", y);
                    blockData.addProperty("z", z);
                    blockData.addProperty("material", block.getType().toString());

                    blocksArray.add(blockData);
                    count++;
                }
            }
        }

        // Salva a área em um arquivo JSON usando Gson
        try (FileWriter file = new FileWriter(this.plugin.getDataFolder() + "/" + areaName + ".json")) {
            gson.toJson(blocksArray, file);
            player.sendMessage("Área salva como " + areaName);
        } catch (IOException e) {
            player.sendMessage("Erro ao salvar a área!");
            e.printStackTrace();
        }

        return true;
    }
}
