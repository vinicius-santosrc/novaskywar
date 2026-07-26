package br.dev.santos.skywar;

import java.io.File;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.ArenaLoader;
import br.dev.santos.skywar.arena.ArenaManager;
import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.arena.GameManager;
import br.dev.santos.skywar.commands.ResetAreaCommand;
import br.dev.santos.skywar.commands.ResetWorldCommand;
import br.dev.santos.skywar.commands.SaveAreaCommand;
import br.dev.santos.skywar.economy.MoneyManager;
import br.dev.santos.skywar.handler.EliminationHandler;
import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.KitManager;
import br.dev.santos.skywar.kit.KitSelectionService;
import br.dev.santos.skywar.kit.KitShopService;
import br.dev.santos.skywar.kit.ability.AbilityManager;
import br.dev.santos.skywar.listeners.PlayerConnectionListener;
import br.dev.santos.skywar.listeners.PlayerDeathListener;
import br.dev.santos.skywar.listeners.SignClickListener;
import br.dev.santos.skywar.listeners.WaitingLobbyListener;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerGameService;
import br.dev.santos.skywar.player.PlayerData.PlayerState;
import br.dev.santos.skywar.scoreboard.ScoreBoardManager;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.tasks.FireworksTask;
import br.dev.santos.skywar.tasks.SignMonitorTask;
import br.dev.santos.skywar.warp.WarpManager;

public final class Skywar extends JavaPlugin {

    private File backupFolder;

    private final ArenaManager arenaManager = new ArenaManager();
    private ArenaMessenger arenaMessenger;

    private ScoreBoardManager scoreBoardManager;

    private PlayerManager playerManager;
    private WarpManager warpManager;

    private MoneyManager moneyManager;
    private KitManager kitManager;
    private KitShopService kitShopService;

    private AbilityManager abilityManager;

    private EliminationHandler eliminationHandler;
    private PlayerGameService playerGameService;

    private KitSelectionService kitSelectionService;

    private ResetWorldCommand resetWorldCommand;

    private FireworksTask fireWorksTask;

    private GameManager gameManager;

    private ArenaLoader arenaLoader;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        createFolders();
        createManagers();
        registerKitsAndAbilities();
        registerListeners();
        registerCommands();
        startTasks();

        getLogger().info("SkyWar Plugin habilitado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SkyWar Plugin desabilitado.");
    }

    private void createFolders() {
        this.backupFolder = new File(getDataFolder(), "backups");

        if (!this.backupFolder.exists()) {
            this.backupFolder.mkdirs();
        }
    }

    private void createManagers() {
        this.playerManager = new PlayerManager();
        this.scoreBoardManager = new ScoreBoardManager();

        this.arenaMessenger = new ArenaMessenger();
        this.warpManager = new WarpManager(this.getConfig());

        this.eliminationHandler = new EliminationHandler(
                this.arenaMessenger,
                this.playerManager);

        this.moneyManager = new MoneyManager(this);

        this.kitManager = new KitManager(
                this.playerManager,
                this.warpManager,
                this.arenaMessenger);

        this.abilityManager = new AbilityManager(
                this,
                this.playerManager,
                this.warpManager,
                this.arenaMessenger);

        this.kitSelectionService = new KitSelectionService(this.playerManager, this.kitManager);

        this.playerGameService = new PlayerGameService(
                this.scoreBoardManager,
                this.kitManager,
                this.warpManager,
                this.kitSelectionService);

        this.kitShopService = new KitShopService(
                this.playerManager,
                this.kitManager,
                this.moneyManager);

        this.resetWorldCommand = new ResetWorldCommand(
                "SkyLands",
                this.backupFolder);

        this.fireWorksTask = new FireworksTask();

        this.gameManager = new GameManager(
                this,
                this.playerManager,
                this.scoreBoardManager,
                this.arenaMessenger,
                this.resetWorldCommand,
                this.fireWorksTask,
                this.playerGameService);

        this.arenaLoader = new ArenaLoader(this.getConfig(), this.arenaManager);
        this.arenaLoader.loadArenas();
    }

    private void registerKitsAndAbilities() {
        this.kitManager.registerDefaults();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new SignClickListener(this.arenaManager), this);
        pluginManager.registerEvents(new PlayerDeathListener(this.playerManager, this.eliminationHandler, this.abilityManager, this.warpManager, this.playerGameService, this.arenaMessenger), this);
        pluginManager.registerEvents(new PlayerConnectionListener(this.playerManager, this.gameManager, this.arenaMessenger, this.playerGameService), this);
        pluginManager.registerEvents(new WaitingLobbyListener(this.playerManager, this.warpManager), this);
    }

    private void registerCommands() {
        PluginCommand saveAreaCommand = getCommand("salvararea");

        if (saveAreaCommand != null) {
            saveAreaCommand.setExecutor(
                    new SaveAreaCommand(this));
        }

        PluginCommand resetAreaCommand = getCommand("resetarea");

        if (resetAreaCommand != null) {
            resetAreaCommand.setExecutor(
                    new ResetAreaCommand(this));
        }

        PluginCommand resetWorldPluginCommand = getCommand("resetworld");

        if (resetWorldPluginCommand != null) {
            resetWorldPluginCommand.setExecutor(
                    this.resetWorldCommand);
        }
    }

    private void startTasks() {
        new SignMonitorTask(this.arenaManager)
                .runTaskTimer(this, 0L, 20L);
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args) {

        Player player = (Player) sender;
        String subCommand = args[0];

        switch (subCommand.toLowerCase()) {
            case "help":
                sendHelp(player);
                return true;

            case "entrar":
                this.handleJoinCommand(player, args);
                return true;

            case "creditos":
                int money = this.moneyManager.getMoney(player);

                sendMessageToPlayer(
                        player,
                        "messages.money_player",
                        "{money}",
                        String.valueOf(money));

                return true;

            case "sair":
                this.gameManager.leaveGame(player);
                return true;

            case "comprar":
                if (args.length > 1) {
                    this.kitShopService.buyNewKit(
                            player,
                            args[1]);
                }

                return true;

            case "start":
                if (args.length > 1) {
                    Arena arena = this.arenaManager.getArena(args[1], "1");
                    this.gameManager.forceStart(arena);
                }

                return true;

            case "reload":
                this.reloadConfig();

                sendMessageToPlayer(
                        player,
                        "messages.config_reloaded");

                return true;

            case "kit":
                handleKitCommand(player, args);
                return true;

            case "create":
                this.getArenaManager().createArena(
                        player,
                        args[1],
                        player.getWorld().getName(),
                        Integer.parseInt(args[2]),
                        getConfig());

                this.saveConfig();
                return true;

            case "remove":
                boolean result = this.getArenaManager().removeArena(
                        player,
                        args[1],
                        getConfig());

                this.saveConfig();
                return result;

            case "set":
                if (!this.arenaManager.exists(args[1], "1")) {
                    player.sendMessage("§cA arena " + args[1] + " não existe.");
                    return false;
                }

                Arena arenaSelected = this.arenaManager.getArena(args[1], "1");

                String arg2 = args.length > 3 ? args[3] : "";

                this.getArenaManager().handleEditArena(
                        arenaSelected,
                        player,
                        args[2],
                        arg2,
                        getConfig());

                this.saveConfig();
                return true;

            default:
                sendMessageToPlayer(
                        player,
                        "messages.unknown_command");

                return true;
        }
    }

    private void handleJoinCommand(Player player, String[] args) {
        if (args.length < 2) {
            sendMessageToPlayer(
                    player,
                    "messages.unknown_command");
            return;
        }

        String mapName = args[1];

        if (args.length >= 3) {
            String roomNumber = args[2];

            Arena arena = this.arenaManager.getArena(
                    mapName,
                    roomNumber);

            if (arena == null) {
                getLogger().severe("Arena não encontrada: " + mapName + " sala " + roomNumber);
                player.sendMessage("Arena não encontrada");
                return;
            }

            this.gameManager.joinGame(
                    player,
                    arena,
                    roomNumber);

            return;
        }
    }

    private void handleKitCommand(
            Player player,
            String[] args) {

        if (args.length < 2) {
            sendMessageToPlayer(
                    player,
                    "messages.unknown_command");
            return;
        }

        PlayerData playerData = this.playerManager.get(player);

        if (playerData == null || playerData.getStatus() != PlayerState.WAITING) {

            sendMessageToPlayer(
                    player,
                    "messages.not_in_lobby");
            return;
        }

        String kitArgument = args[1];

        if (kitArgument.equalsIgnoreCase("remove")) {
            this.kitSelectionService.removeKit(player);
            return;
        }

        if (kitArgument.equalsIgnoreCase("check")) {
            this.kitSelectionService.checkKit(player);
            return;
        }

        Kit kitSelected = this.kitManager.get(kitArgument);

        this.kitSelectionService.chooseKit(
                player,
                kitSelected);
    }

    private void sendHelp(Player player) {
        List<String> messages = getConfig().getStringList("messages.help");

        for (String message : messages) {
            player.sendMessage(message);
        }
    }

    public void sendMessageToPlayer(
            Player player,
            String path) {

        String message = getConfig().getString(path);

        if (message == null) {
            getLogger().warning(
                    "Mensagem não encontrada: " + path);
            return;
        }

        player.sendMessage(message);
    }

    public void sendMessageToPlayer(
            Player player,
            String path,
            String replaceKey,
            String replaceValue) {

        String message = getConfig().getString(path);

        if (message == null) {
            getLogger().warning(
                    "Mensagem não encontrada: " + path);
            return;
        }

        if (replaceKey != null && replaceValue != null) {
            message = message.replace(
                    replaceKey,
                    replaceValue);
        }

        player.sendMessage(message);
    }

    public FileConfiguration getMainConfig() {
        return getConfig();
    }

    public File getBackupFolder() {
        return backupFolder;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public ArenaMessenger getArenaMessenger() {
        return arenaMessenger;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    public MoneyManager getMoneyManager() {
        return moneyManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}