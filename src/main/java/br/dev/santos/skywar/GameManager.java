package br.dev.santos.skywar;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager implements Listener {

    private static Map<Player, PlayerData> playersData = new HashMap<>();
    private final JavaPlugin plugin;

    public GameManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static class PlayerData {
        private String kit;
        private int life;
        private boolean isDead;
        private String status;
        private String arena;
        private boolean usedExtraLife = false;
        private int NumberIsland;

        public PlayerData() {
            this.life = 100;
            this.isDead = false;
            this.status = "NotPlaying";
            this.arena = null;
        }

        public boolean hasUsedExtraLife() {
            return usedExtraLife;
        }

        public void setUsedExtraLife(boolean usedExtraLife) {
            this.usedExtraLife = usedExtraLife;
        }

        public String getKit() {
            return kit;
        }

        public void setKit(String kit) {
            this.kit = kit;
        }

        public void setIsland(int island) {
            this.NumberIsland = island;
        }

        public int getIsland() {
            return NumberIsland;
        }

        public String getArena() {
            return arena;
        }

        public void setArena(String arena) {
            this.arena = arena;
        }

        public int getLife() {
            return life;
        }

        public void setLife(int life) {
            this.life = life;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean isDead) {
            this.isDead = isDead;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }


    public static class Partida {
        private String status; // Started, Finishing, Reseting, Vip, Open
        private boolean pvpOff; // true ou false
        private int pvpOffTime; // 15/10/5 seconds
        private int maxPlayers;
        private int minPlayers;
        private int timeToStart;

        public int getTimeToStart() {
            return this.timeToStart;
        }

        public void setTimeToStart(int time) {
            this.timeToStart = time;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


    public static void chooseKit(Player player, String kit) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        PlayerData playerData = playersData.getOrDefault(player, new PlayerData());
        playerData.setKit(kit);
        playersData.put(player, playerData);

        String message = config.getString("messages.kit_selected")
                .replace("{kit}", kit);
        player.sendMessage(message);
    }

    public static void removeKit(Player player) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        PlayerData playerData = playersData.get(player);
        if (playerData != null) {
            playerData.setKit(null);
        }

        String message = config.getString("messages.kit_removed");
        player.sendMessage(message);
    }

    public static void checkKit(Player player) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        PlayerData playerData = playersData.get(player);
        if (playerData != null && playerData.getKit() != null) {
            String message = config.getString("messages.actual_kit")
                    .replace("{kit}", playerData.getKit());
            player.sendMessage(message);
        } else {
            String message = config.getString("messages.noactual_kit");
            player.sendMessage(message);
        }
    }

    public static void sendMessageToArena(String arena, String message) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        for (Map.Entry<Player, PlayerData> entry : playersData.entrySet()) {
            Player player = entry.getKey();
            PlayerData playerData = entry.getValue();

            if (arena.equals(playerData.getArena())) {
                player.sendMessage(message);
            }
        }
    }

    public static void joinGame(Player player, String arena) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        PlayerData playerData = playersData.getOrDefault(player, new PlayerData());

        if (playerData.getArena() == null) {
            playerData.setDead(false);
            playerData.setLife(100);
            playerData.setKit(null);
            playerData.setStatus("Playing");
            playerData.setArena(arena);

            playersData.put(player, playerData);

            String message = config.getString("messages.join_arena")
                    .replace("{arena}", arena);
            player.sendMessage(message);

            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            player.performCommand("warp WaitingSW");
            player.getInventory().clear();

            ItemStack chest = new ItemStack(Material.CHEST);
            ItemMeta chestMeta = chest.getItemMeta();
            if (chestMeta != null) {
                chestMeta.setDisplayName("§6Seleção de KIT");
                chest.setItemMeta(chestMeta);
            }

            ItemStack emerald = new ItemStack(Material.EMERALD);
            ItemMeta emeraldMeta = emerald.getItemMeta();
            if (emeraldMeta != null) {
                emeraldMeta.setDisplayName("§aLoja");
                emerald.setItemMeta(emeraldMeta);
            }

            Inventory inventory = player.getInventory();
            inventory.setItem(3, chest);
            inventory.setItem(5, emerald);

            Integer PlayersArena = getPlayersInArena(playerData.arena).size();
            Integer MaxPlayers = 12;

            String joinMessage = config.getString("messages.player_joined_arena")
                    .replace("{player}", player.getDisplayName())
                    .replace("{X}", String.valueOf(getPlayersInArena(playerData.arena).size()))
                    .replace("{Y}", MaxPlayers.toString());
            sendMessageToArena(arena, joinMessage);

            List<Player> playersInArena = getPlayersInArena(arena);

            int playersAlive = playersInArena.size();
            int spectators = getSpectators(arena).size();

            for (int i = 0; i < playersInArena.size(); i++) {
                Player p = playersInArena.get(i);
                ScoreBoard.setScoreBoard(p, playersAlive, spectators, arena, 0);
            }

            for (int i = 0; i < getSpectators(arena).size(); i++) {
                Player p = getSpectators(arena).get(i);
                ScoreBoard.setScoreBoard(p, playersAlive, spectators, arena, 0);
            }

        } else {
            String message = config.getString("messages.error_arena");
            player.sendMessage(message);
        }
    }


    public static void leaveGame(Player player) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();
        PlayerData playerData = playersData.get(player);

        List<Player> playersInArena = getPlayersInArena(playerData.getArena());

        int playersAlive = playersInArena.size();
        int spectators = getSpectators(playerData.getArena()).size();

        for (int i = 0; i < playersInArena.size(); i++) {
            Player p = playersInArena.get(i);
            ScoreBoard.setScoreBoard(p, playersAlive, spectators, playerData.getArena(), 0);
        }

        for (int i = 0; i < getSpectators(playerData.getArena()).size(); i++) {
            Player p = getSpectators(playerData.getArena()).get(i);
            ScoreBoard.setScoreBoard(p, playersAlive, spectators, playerData.getArena(), 0);
        }

        player.setPlayerListName(player.getName());
        player.setDisplayName(player.getName());
        player.setCustomName(player.getName());
        player.setCustomNameVisible(false);
        ScoreBoard.removeScoreBoard(player);

        if(playerData.arena == null) {
            String message = config.getString("messages.not_in_arena");
            player.sendMessage(message);
        }
        else {
            if (playerData != null) {
                player.performCommand("warp skywar");
                String message = config.getString("messages.leave_arena");
                player.sendMessage(message);

                player.getInventory().clear();

                Integer PlayersArena = getPlayersInArena(playerData.arena).size();
                Integer MaxPlayers = 12;

                String joinMessage = config.getString("messages.player_leave_arena")
                        .replace("{player}", player.getDisplayName())
                        .replace("{X}", String.valueOf(getPlayersInArena(playerData.arena).size()))
                        .replace("{Y}", MaxPlayers.toString());
                sendMessageToArena(playerData.arena, joinMessage);
                playerData.setDead(false);
                playerData.setLife(100);
                playerData.setKit(null);
                playerData.setStatus(null);
                playerData.setArena(null);
            }
        }
    }

    public static void leaveGameAfterFinished(Player player) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        PlayerData playerData = playersData.get(player);

        ScoreBoard.removeScoreBoard(player);

        player.setPlayerListName(player.getName());
        player.setDisplayName(player.getName());
        player.setCustomName(player.getName());
        player.setCustomNameVisible(false);

        if(playerData.arena == null) {
            String message = config.getString("messages.not_in_arena");
            player.sendMessage(message);
        }
        else {
            if (playerData != null) {
                ScoreBoard.removeScoreBoard(player);
                player.performCommand("warp skywar");

                player.getInventory().clear();

                playerData.setDead(false);
                playerData.setLife(100);
                playerData.setKit(null);
                playerData.setStatus(null);
                playerData.setArena(null);
            }
        }
    }

    public static void startGame(Player player, String arena) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();
        PlayerData playerData = playersData.get(player);
        List<Player> playersInArena = getPlayersInArena(arena);

        int playersAlive = playersInArena.size();
        int spectators = getSpectators(arena).size();

        Partida partida = new Partida();
        int i = 60;
        partida.setTimeToStart(i);

        // Cria um array final para armazenar o ID da tarefa
        final int[] taskId = new int[1];


        taskId[0] = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            int count = i;

            @Override
            public void run() {
                if (count > 0) {
                    partida.setTimeToStart(count);
                    if(count <= 5 || count == 10 || count == 30 || count == 60 || count == 120 || count == 180) {
                        String joinMessage = config.getString("messages.seconds_to_start").replace("{seconds}", String.valueOf(count));
                        sendMessageToArena(playerData.arena, joinMessage);
                    }
                    for (int i = 0; i < getPlayersInArena(arena).size(); i++) {
                        Player p = getPlayersInArena(arena).get(i);
                        p.setLevel(count);
                        if(count == 1) {
                            p.playSound(p.getLocation(), Sound.valueOf("ANVIL_LAND"), 1.0f, 1.0f);
                        }
                        else {
                            if(count <= 5 || count == 10 || count == 30) {
                                p.playSound(p.getLocation(), Sound.valueOf("CLICK"), 1.0f, 1.0f);
                            }
                        }
                    }
                    count--;
                } else {
                    Bukkit.getScheduler().cancelTask(taskId[0]);
                    for (int i = 0; i < getPlayersInArena(arena).size(); i++) {
                        Player p = getPlayersInArena(arena).get(i);
                        p.setLevel(count);
                        p.playSound(p.getLocation(), Sound.valueOf("CLICK"), 1.0f, 1.0f);
                    }
                    gameStart(player, playerData.arena);
                }
            }

        }, 0L, 20L).getTaskId();
    }

    public static void gameStart(Player player, String arena) {
        Partida partida = new Partida();
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();
        PlayerData playerData = playersData.get(player);
        List<Player> playersInArena = getPlayersInArena(arena);

        int playersAlive = playersInArena.size();
        int spectators = getSpectators(arena).size();

        String message = config.getString("messages.game_start");
        sendMessageToArena(playerData.arena, message);

        String messagePvP = config.getString("messages.pvp_message")
                .replace("{seconds}", "5");
        sendMessageToArena(playerData.arena, messagePvP);

        for (int i = 0; i < getPlayersInArena(arena).size(); i++) {
            Player p = getPlayersInArena(arena).get(i);
            p.setGameMode(GameMode.SURVIVAL);
            ScoreBoard.setScoreBoard(p, playersAlive, spectators, arena, 0);
            p.setPlayerListName(ChatColor.GREEN + p.getName());
            p.setCustomName(ChatColor.GREEN + p.getName());
            p.setCustomNameVisible(true);
        }

        final int[] timeofMatch = { 5 };

        for (int i = 0; i < playersInArena.size(); i++) {
            Player p = playersInArena.get(i);
            PlayerData playerDataP = playersData.get(p);
            int warpNumber = i + 1;

            p.getInventory().clear();
            Kit.giveItems(p, playerDataP.getKit());
            p.setLevel(0);
            p.setExp(0);
            partida.setStatus("Playing");

            playerDataP.setIsland(warpNumber);

            String warpName = arena + "-" + warpNumber;

            teleportPlayerToWarp(p, warpName);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                String delayedMessage = config.getString("messages.pvpenabled");
                sendMessageToArena(arena, delayedMessage);
            }
        }.runTaskLater(plugin, 100L); //

        new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> playersInArena = getPlayersInArena(arena);

                int playersAlive = playersInArena.size();
                int spectators = getSpectators(arena).size();
                timeofMatch[0]--;

                for (int i = 0; i < playersInArena.size(); i++) {
                    Player p = playersInArena.get(i);
                    ScoreBoard.setScoreBoard(p, playersAlive, spectators, arena, timeofMatch[0]);
                }

                for (int i = 0; i < getSpectators(arena).size(); i++) {
                    Player p = getSpectators(arena).get(i);
                    ScoreBoard.setScoreBoard(p, playersAlive, spectators, arena, timeofMatch[0]);
                }

                if (playersInArena.size() == 1) {
                    endGame(getPlayersInArena(arena).get(0), playerData.getArena());
                    cancel();
                }

            }
        }.runTaskTimer(plugin, 20L, 20L);


    };

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (player.getItemInHand().getType().equals(Material.CHEST)) {
            Bukkit.dispatchCommand(player, "chestcommands open swkits " + player.getDisplayName());
        }
        else if (player.getItemInHand().getType().equals(Material.EMERALD)) {
            Bukkit.dispatchCommand(player, "chestcommands open swloja " + player.getDisplayName());
        }

    }

    @EventHandler
    public void onEnderPearl(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            Skywar plugin = Skywar.getPlugin(Skywar.class);
            FileConfiguration config = plugin.getConfig();
            Player player = event.getPlayer();
            PlayerData playerData = playersData.get(player);
            Location enderPearlLocation = event.getTo();
            if(playerData.getKit().equals("Enderman")) {
                String message = config.getString("messages.enderpearl_message");
                player.sendMessage(message);
                event.setCancelled(true);
                enderPearlLocation.getWorld().playSound(enderPearlLocation, Sound.valueOf("ANVIL_LAND"), 1.0f, 1.0f);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location newLocation = enderPearlLocation.clone().add(0, 15, 0);
                        player.teleport(newLocation);
                        player.setNoDamageTicks(60);
                    }
                }.runTaskLater(plugin, 60L);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();
        Player player = event.getPlayer();
        PlayerData playerData = playersData.get(player);

        playerData.setDead(true);
        playerData.setKit(null);
        playerData.setStatus(null);
        event.setQuitMessage(null);
        player.setPlayerListName(player.getName());
        player.setDisplayName(player.getName());
        player.setCustomName(player.getName());
        player.setCustomNameVisible(false);

        if (playerData != null) {
            List<Player> playersInArena = getPlayersInArena(playerData.arena);
            playersInArena.remove(player);

            String messageDeath = config.getString("messages.messageDeath")
                    .replace("{player}", player.getDisplayName())
                    .replace("{X}", String.valueOf(playersInArena.size()))
                    .replace("{Y}", "12");
            sendMessageToArena(playerData.arena, messageDeath);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();
        Player player = event.getEntity();
        PlayerData playerData = playersData.get(player);
        event.setDeathMessage(null);
        player.spigot().respawn();

        player.setPlayerListName(ChatColor.RED + player.getName());
        player.setCustomName(ChatColor.RED + player.getName());
        player.setCustomNameVisible(true);

        if(playerData.getKit().equals("Vida-extra") && !playerData.hasUsedExtraLife()) {
            player.setPlayerListName(ChatColor.GREEN + player.getName());
            player.setCustomName(ChatColor.GREEN + player.getName());
            player.setCustomNameVisible(true);
        }

        EntityDamageEvent lastDamageCause = player.getLastDamageCause();

        String deathMessage = "";
        if (lastDamageCause instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) lastDamageCause;
            if (entityDamageEvent.getDamager() instanceof Player) {
                Player killer = (Player) entityDamageEvent.getDamager();
                deathMessage = config.getString("messages.player_killed_oponent")
                        .replace("{player}", player.getDisplayName())
                        .replace("{killer}", killer.getDisplayName());
            }
        } else if (lastDamageCause.getCause() == EntityDamageEvent.DamageCause.VOID) {
            deathMessage = config.getString("messages.messageDeath")
                    .replace("{player}", player.getDisplayName());
        } else {
            deathMessage = config.getString("messages.messageDeath")
                    .replace("{player}", player.getDisplayName());
        }

        // Se o jogador tiver o kit "Vida-extra"
        if (playerData != null) {
            if (playerData.getKit() != null && playerData.getKit().equalsIgnoreCase("Vida-extra") && !playerData.isDead()) {
                // Verifica se o jogador já usou a vida extra
                if (!playerData.hasUsedExtraLife()) {
                    event.getDrops().clear(); // Limpa os drops de itens
                    teleportPlayerToWarp(player, playerData.getArena() + "-" + playerData.getIsland()); // Teleporta para a warp configurada

                    String extraLifeMessage = config.getString("messages.extralifekit_message")
                            .replace("{player}", player.getDisplayName());
                    sendMessageToArena(playerData.getArena(), extraLifeMessage);

                    String extraLifeMessageToPlayer = config.getString("messages.extralife_message_to_player");

                    player.sendMessage(extraLifeMessageToPlayer);

                    playerData.setUsedExtraLife(true);
                } else {
                    playerData.setDead(true);
                    playerData.setKit(null);
                    playerData.setUsedExtraLife(false);
                    playerData.setStatus("Espectador");

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            teleportPlayerToWarp(player, playerData.getArena() + "-winner");
                            player.setGameMode(GameMode.SPECTATOR);
                        }
                    }.runTaskLater(Skywar.getPlugin(Skywar.class), 1L);

                    if (playerData != null) {
                        List<Player> playersInArena = getPlayersInArena(playerData.arena);
                        playersInArena.remove(player);

                        String messageDeath = deathMessage
                                .replace("{X}", String.valueOf(playersInArena.size()))
                                .replace("{Y}", "12");
                        sendMessageToArena(playerData.arena, messageDeath);
                    }
                }
            } else {
                playerData.setDead(true);
                playerData.setKit(null);
                playerData.setUsedExtraLife(false);
                playerData.setStatus("Espectador");

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        teleportPlayerToWarp(player, playerData.getArena() + "-winner");
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                }.runTaskLater(Skywar.getPlugin(Skywar.class), 1L);

                if (playerData != null) {
                    List<Player> playersInArena = getPlayersInArena(playerData.arena);
                    playersInArena.remove(player);

                    String messageDeath = deathMessage
                            .replace("{X}", String.valueOf(playersInArena.size()))
                            .replace("{Y}", "12");
                    sendMessageToArena(playerData.arena, messageDeath);
                }
            }
        }

    }


    private static void teleportPlayerToWarp(Player player, String warpName) {
        player.performCommand("warp " + warpName);
    };

    public static int getLengthMax(String arena) {
        if(arena .equals("DesertLands")) {
            return 12;
        }
        else if(arena .equals("SkyLands")){
            return 16;
        }
        else {
            return 0;
        }
    };

    public static List<Player> getPlayersInArena(String arena) {
        List<Player> playersInArena = new ArrayList<>();

        for (Map.Entry<Player, PlayerData> entry : playersData.entrySet()) {
            Player player = entry.getKey();
            PlayerData playerData = entry.getValue();

            if (arena.equals(playerData.getArena())) {
                if(!playerData.isDead()){
                    playersInArena.add(player);
                }
            }
        }

        return playersInArena;
    }

    private static List<Player> getSpectators(String arena) {
        List<Player> playersInArena = new ArrayList<>();

        for (Map.Entry<Player, PlayerData> entry : playersData.entrySet()) {
            Player player = entry.getKey();
            PlayerData playerData = entry.getValue();

            if (arena.equals(playerData.getArena())) {
                if(playerData.isDead()){
                    playersInArena.add(player);
                }
            }
        }

        return playersInArena;
    }

    public static void endGame(Player player, String arena) {

        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        String message = config.getString("messages.winning_message");
        player.sendMessage(message);

        String messageFinished = config.getString("messages.game_finished").replace("{player}", player.getDisplayName());
        sendMessageToArena(arena, messageFinished);
        String messageLobby = config.getString("messages.lobby_message");
        sendMessageToArena(arena,messageLobby);

        player.performCommand("warp " + arena + "-winner");

        player.getLocation();


        final int[] taskId = new int[1];

        taskId[0] = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            int i = 15;
            int count = i;

            PlayerData playerData = playersData.get(player);

            @Override
            public void run() {
                startFireworks(player, arena, playerData);
                if (count > 0) {
                    count--;
                } else {
                    Bukkit.getScheduler().cancelTask(taskId[0]);

                    List<Player> playersInArena = new ArrayList<>(getPlayersInArena(arena));
                    List<Player> specsInArena = new ArrayList<>(getSpectators(arena));

                    for (Player p : playersInArena) {
                        PlayerData player  = getPlayer(p);
                        player.setDead(false);
                        player.setArena(null);
                        player.setKit(null);
                        player.setStatus(null);
                        player.setIsland(0);
                        p.getInventory().clear();
                        p.performCommand("skywar leaveafterwin");
                        teleportPlayerToWarp(p, "skywar");
                        p.setPlayerListName(ChatColor.GREEN + p.getName());
                        p.setCustomName(ChatColor.GREEN + p.getName());
                        p.setCustomNameVisible(true);
                    }
                    for (Player spec : specsInArena) {
                        PlayerData player  = getPlayer(spec);
                        player.setDead(false);
                        player.setArena(null);
                        player.setKit(null);
                        player.setStatus(null);
                        player.setIsland(0);
                        spec.getInventory().clear();
                        spec.performCommand("skywar leaveafterwin");
                        teleportPlayerToWarp(spec, "skywar");
                    }

                    try {
                        Skywar.resetWorldByArena(arena);
                    } catch (Exception e) {
                        Bukkit.getLogger().severe("Erro ao resetar a arena: " + e.getMessage());
                        e.printStackTrace();
                    }

                }
            }

            public void startFireworks(Player player, String arena, PlayerData playerData) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (playerData.getArena() != null) {
                            Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                            FireworkMeta meta = firework.getFireworkMeta();
                            meta.addEffect(FireworkEffect.builder()
                                    .withColor(Color.RED, Color.BLUE)
                                    .withFlicker()
                                    .withTrail()
                                    .with(FireworkEffect.Type.BALL_LARGE)
                                    .build());
                            meta.setPower(1);
                            firework.setFireworkMeta(meta);
                        } else {
                            cancel();
                        }
                    }
                }.runTaskTimer(Skywar.getPlugin(Skywar.class), 0L, 40L);
            }

        }, 0L, 40L).getTaskId();
    }

    public static String getPlayerData(Player player) {
        PlayerData playerData = playersData.get(player);

        if (playerData != null) {
            return playerData.getStatus();
        }
        else if (playerData == null) {
            return "NotPlaying";
        }
        else {
            return "NotPlaying";
        }
    }

    public static PlayerData getPlayer(Player player) {
        PlayerData playerData = playersData.get(player);

        return playerData;

    }

}
