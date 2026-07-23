package br.dev.santos.skywar.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.ArenaManager;

import org.bukkit.entity.Player;

public final class SignClickListener implements Listener {

    private final ArenaManager arenaManager;

    public SignClickListener(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Block block = event.getBlock();

        if (!block.getType().toString().contains("SIGN")) {
            return;
        }

        if (!"[SkyWar]".equalsIgnoreCase(event.getLine(0))) {
            return;
        }

        String mapName = event.getLine(1);
        String numberRoom = event.getLine(2);

        if (mapName == null || mapName.trim().isEmpty()) {
            event.getPlayer().sendMessage("§cInforme o nome do mapa na segunda linha.");
            return;
        }

        if (numberRoom == null || numberRoom.trim().isEmpty()) {
            event.getPlayer().sendMessage("§cInforme o número da sala na terceira linha.");
            return;
        }

        Arena arena = arenaManager.getArena(mapName, numberRoom);

        if (arena == null) {
            event.getBlock().breakNaturally();
            return;
        }

        updateLines(event, arena, mapName, numberRoom);

        event.getPlayer().sendMessage("§aPlaca criada para a arena " + mapName + " sala " + numberRoom + ".");
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();

        if (block == null || !block.getType().toString().contains("SIGN")) {
            return;
        }

        if (!(block.getState() instanceof Sign)) {
            return;
        }

        Sign sign = (Sign) block.getState();
        updateSign(sign, arenaManager);

        String statusLine = sign.getLine(0);

        if (!"§a§l[Aberta]".equalsIgnoreCase(statusLine) && !"§6§l[VIP]".equalsIgnoreCase(statusLine)) {
            return;
        }

        String mapName = sign.getLine(3);
        String numberRoom = extractRoomNumber(sign.getLine(1));

        Arena arena = arenaManager.getArena(mapName, numberRoom);

        if (arena == null) {
            event.getPlayer().sendMessage("§cEssa arena não existe mais.");
            return;
        }

        if (arena.getPlayers().size() >= arena.maxPlayers) {
            event.getPlayer().sendMessage("§cEssa arena está cheia.");
            return;
        }

        Player player = event.getPlayer();

        player.performCommand("skywar entrar " + mapName + "-" + numberRoom);
    }

    public static void updateSign(Sign sign, ArenaManager arenaManager) {
        String mapName = sign.getLine(3);
        String numberRoom = extractRoomNumber(sign.getLine(1));

        if (mapName == null || mapName.trim().isEmpty()) {
            return;
        }

        if (numberRoom == null || numberRoom.trim().isEmpty()) {
            return;
        }

        Arena arena = arenaManager.getArena(mapName, numberRoom);

        if (arena == null) {
            sign.setLine(0, "§c§l[Inválida]");
            sign.setLine(1, "SkyWar " + numberRoom);
            sign.setLine(2, "§cNão existe");
            sign.setLine(3, mapName);
            sign.update(true);
            return;
        }

        updateLines(sign, arena, mapName, numberRoom);
        sign.update(true);
    }

    private static void updateLines(
            SignChangeEvent event,
            Arena arena,
            String mapName,
            String numberRoom) {

        event.setLine(0, getStatusText(arena));
        event.setLine(1, "SkyWar " + numberRoom);
        event.setLine(
                2,
                arena.getPlayers().size()
                        + "/"
                        + arena.maxPlayers
        );
        event.setLine(3, mapName);
    }

    private static void updateLines(
            Sign sign,
            Arena arena,
            String mapName,
            String numberRoom) {

        sign.setLine(0, getStatusText(arena));
        sign.setLine(1, "SkyWar " + numberRoom);
        sign.setLine(
                2,
                arena.getPlayers().size()
                        + "/"
                        + arena.maxPlayers
        );
        sign.setLine(3, mapName);
    }

    private static String getStatusText(Arena arena) {
        switch (arena.getStatus()) {
            case OPEN:
                return "§a§l[Aberta]";

            case VIP:
                return "§6§l[VIP]";

            case STARTED:
                return "§5§l[Em Jogo]";

            case FINISHING:
                return "§5§l[Finalizando]";

            case RESETING:
                return "§4§l[Resetando]";

            default:
                return "§c§l[Fechada]";
        }
    }

    private static String extractRoomNumber(String line) {
        if (line == null) {
            return "";
        }

        String prefix = "SkyWar ";

        if (line.startsWith(prefix)) {
            return line.substring(prefix.length()).trim();
        }

        return line.trim();
    }
}