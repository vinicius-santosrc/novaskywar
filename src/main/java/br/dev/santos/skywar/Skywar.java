package br.dev.santos.skywar;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Skywar extends JavaPlugin implements Listener {
    private static File backupFolder;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("Skywar Plugin Enabled");
        getLogger().info("SkyWar Plugin habilitado!");
        getServer().getPluginManager().registerEvents(new GameManager(), this);
        this.getCommand("salvararea").setExecutor(new SaveAreaCommand(this));
        this.getCommand("resetarea").setExecutor(new ResetAreaCommand(this));
        backupFolder = new File(getDataFolder(), "backups");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("resetworld").setExecutor(new ResetWorldCommand("SkyLands", backupFolder));
        Kit.initializeKits();
    }

    @Override
    public void onDisable() {
        System.out.println("Skywar Plugin Disabled");
        Bukkit.getLogger().info("§cPlugin SkyWar desabilitado");
    }

    public static void resetWorldByArena(String arena) {
        new ResetWorldCommand(arena, backupFolder);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // Verifique se o item clicado é nulo ou ar
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        // Impedir que o jogador interaja com o inventário se for o baú ou a esmeralda
        if (clickedItem.getType() == Material.CHEST || clickedItem.getType() == Material.EMERALD) {
            event.setCancelled(true); // Cancela o evento para prevenir a remoção do item

            // Verificar se o clique foi com o botão direito do mouse ou esquerdo
            if (event.getClick().isRightClick() || event.getClick().isLeftClick()) {
                if (clickedItem.getType() == Material.CHEST) {
                    // Executa o comando para selecionar kit
                    Bukkit.dispatchCommand(player, "swkits"); // Use Bukkit.dispatchCommand para garantir que o comando seja executado
                    player.sendMessage("Abrindo seleção de kits...");
                    Bukkit.getLogger().info(player.getName() + " clicou no baú para selecionar um kit.");
                } else if (clickedItem.getType() == Material.EMERALD) {
                    // Executa o comando para abrir a loja
                    Bukkit.dispatchCommand(player, "swloja");
                    player.sendMessage("Abrindo loja...");
                    Bukkit.getLogger().info(player.getName() + " clicou na esmeralda para abrir a loja.");
                }
            }
        }
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = this.getConfig();

        if (command.getName().equalsIgnoreCase("skywar")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (args.length == 0) {
                    player.sendMessage(config.getString("messages.incorrect_usage"));
                    return true;
                } else if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(config.getString("messages.help"));
                    return true;
                } else if (args[0].equalsIgnoreCase("entrar")) {
                    if (args.length > 1) {
                        GameManager.joinGame(player, args[1]);
                        return true;
                    } else {
                        player.sendMessage(config.getString("messages.unknown_command"));
                        return true;
                    }
                }
                else if (args[0].equalsIgnoreCase("sair")) {
                    GameManager.leaveGame(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    return true;
                }
                else if(args[0].equalsIgnoreCase("leaveafterwin")) {
                    GameManager.leaveGameAfterFinished(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("start")) {
                    if (args.length > 1) {
                        GameManager.startGame(player, args[1]);
                        return true;
                    } else {
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    player.sendMessage(config.getString("messages.config_reloaded"));
                    return true;
                } else if (args[0].equalsIgnoreCase("kit")) {
                    if (args.length > 1) {
                        if (GameManager.getPlayerData(player).equals("Playing")) {
                            if (args[1].equalsIgnoreCase("remove")) {
                                GameManager.removeKit(player);
                                return true;
                            } else if (args[1].equalsIgnoreCase("check")) {
                                GameManager.checkKit(player);
                                return true;
                            } else {
                                GameManager.chooseKit(player, args[1]);
                                return true;
                            }
                        } else {
                            player.sendMessage(config.getString("messages.unknown_command"));
                            return true;
                        }
                    } else {
                        player.sendMessage(config.getString("messages.unknown_command"));
                        return true;
                    }
                } else {
                    player.sendMessage(config.getString("messages.unknown_command"));
                    return true;
                }
            } else {
                sender.sendMessage("Este comando só pode ser executado por jogadores.");
                return true;
            }
        }

        return true;
    }
}
