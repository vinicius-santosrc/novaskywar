package br.dev.santos.skywar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResetAreaCommand implements CommandExecutor {

    private JavaPlugin plugin;
    private Gson gson;

    public ResetAreaCommand(JavaPlugin plugin) {
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
            player.sendMessage("Use: /resetarea <nome>");
            return false;
        }

        String areaName = args[0];
        File folder = this.plugin.getDataFolder();
        File[] files = folder.listFiles((dir, name) -> name.startsWith(areaName) && name.endsWith(".json"));

        if (files == null || files.length == 0) {
            player.sendMessage("Nenhum arquivo encontrado para a área " + areaName);
            return false;
        }

        for (File file : files) {
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {
                JsonArray blocksArray = JsonParser.parseReader(reader).getAsJsonArray();

                for (JsonElement element : blocksArray) {
                    JsonObject blockData = element.getAsJsonObject();
                    int x = blockData.get("x").getAsInt();
                    int y = blockData.get("y").getAsInt();
                    int z = blockData.get("z").getAsInt();
                    Material material = Material.valueOf(blockData.get("material").getAsString());

                    Location location = new Location(player.getWorld(), x, y, z);
                    Block block = location.getBlock();
                    block.setType(material);
                }

                player.sendMessage("Área " + areaName + " foi restaurada!");
            } catch (IOException e) {
                player.sendMessage("Erro ao restaurar a área!");
                e.printStackTrace();
            }
        }

        return true;
    }
}
