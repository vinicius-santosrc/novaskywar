package br.dev.santos.skywar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SignClickListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Block block = event.getBlock();

        // Verifica se a placa é do tipo SIGN ou WALL_SIGN
        if (block.getType().toString().contains("SIGN")) {
            String[] lines = event.getLines();

            if (event.getLine(0).equalsIgnoreCase("[SkyWar]")) {
                Bukkit.getLogger().info("Placa SkyWar detectada.");
                String mapName = event.getLine(1);
                String numberRoom = event.getLine(2);
                int lengthMap = GameManager.getPlayersInArena(mapName).size();
                int lengthMaxMap = GameManager.getLengthMax(mapName);
                String lengthMapCurrent = lengthMap + "/" + lengthMaxMap;

                if (lengthMap >= lengthMaxMap) {
                    event.setLine(0, "§a§l[Aberta]");
                } else if (lengthMap >= (lengthMaxMap - 2) && lengthMap < lengthMaxMap) {
                    event.setLine(0, "§6§l[VIP]");
                } else {
                    event.setLine(0, "§a§l[Aberta]");
                }

                event.setLine(1, "SkyWar " + numberRoom);
                event.setLine(2, lengthMapCurrent);
                event.setLine(3, mapName);
            }
        }
    }


    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block.getType().toString().equals("SIGN") || block.getType().toString().equals("WALL_SIGN")) {
                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();

                if (lines[0].equalsIgnoreCase("§a§l[Aberta]") || lines[0].equalsIgnoreCase("§6§l[VIP]")) {
                    String mapName = lines[3];

                    Player player = event.getPlayer();
                    player.performCommand( "skywar entrar " + mapName);
                }
            }
        }
    }
}
