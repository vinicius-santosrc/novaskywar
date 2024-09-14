package br.dev.santos.skywar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SignClickListener implements Listener {

    @EventHandler
    public void onSignPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType().toString().equals("SIGN") || block.getType().toString().equals("WALL_SIGN")) {
            Sign sign = (Sign) block.getState();
            String[] lines = sign.getLines();

            if (sign.getLine(0).equalsIgnoreCase("[SkyWar]")) {
                Bukkit.getLogger().info("Placa SkyWar detectada.");
                String mapName = sign.getLine(1);
                int lengthMap = GameManager.getPlayersInArena(mapName).size();
                int lengthMaxMap = GameManager.getLengthMax(mapName);
                String lengthMapCurrent = lengthMap + "/" + lengthMaxMap;

                if (lengthMap >= lengthMaxMap) {
                    sign.setLine(0, "§a§l[Aberta]");
                } else if (lengthMap >= (lengthMaxMap - 2) && lengthMap < lengthMaxMap) {
                    sign.setLine(0, "§6§l[VIP]");
                } else {
                    sign.setLine(0, "§a§l[Aberta]");
                }
                sign.setLine(1, "SkyWar-1");
                sign.setLine(2, mapName);
                sign.setLine(3, lengthMapCurrent);

                sign.update(true);
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
                    String mapName = lines[2];

                    Player player = event.getPlayer();
                    player.performCommand( "skywar entrar " + mapName);
                }
            }
        }
    }
}
