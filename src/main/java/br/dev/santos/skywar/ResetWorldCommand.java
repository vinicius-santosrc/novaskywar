package br.dev.santos.skywar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;

public class ResetWorldCommand implements CommandExecutor {

    private final String worldName;
    private final File worldBackupFolder;

    public ResetWorldCommand(String worldName, File worldBackupFolder) {
        this.worldName = worldName;
        this.worldBackupFolder = worldBackupFolder;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem executar este comando.");
            return false;
        }

        Player player = (Player) sender;
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            player.sendMessage(ChatColor.RED + "O mundo especificado não foi encontrado.");
            return false;
        }

        // Descarrega o mundo
        Bukkit.unloadWorld(world, false);
        player.sendMessage(ChatColor.YELLOW + "Mundo descarregado.");

        // Exclui o mundo atual
        File worldFolder = world.getWorldFolder();
        deleteFolder(worldFolder);

        // Copia o backup para o diretório de mundos
        File backupFolder = new File(worldBackupFolder, worldName);
        copyFolder(backupFolder, worldFolder);

        // Recarrega o mundo
        Bukkit.createWorld(new WorldCreator(worldName));
        player.sendMessage(ChatColor.GREEN + "O mundo foi restaurado com sucesso!");

        return true;
    }

    private void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                deleteFolder(file);
            }
        }
        folder.delete();
    }

    private void copyFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }
            for (String file : source.list()) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                copyFolder(srcFile, destFile);
            }
        } else {
            try (InputStream in = new FileInputStream(source);
                 OutputStream out = new FileOutputStream(destination)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
