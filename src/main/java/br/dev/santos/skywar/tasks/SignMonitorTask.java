package br.dev.santos.skywar.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.arena.ArenaManager;
import br.dev.santos.skywar.listeners.SignClickListener;

public final class SignMonitorTask extends BukkitRunnable {

    private final ArenaManager arenaManager;

    public SignMonitorTask(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (BlockState state : chunk.getTileEntities()) {
                    if (!(state instanceof Sign)) {
                        continue;
                    }

                    Sign sign = (Sign) state;

                    if (!isSkyWarSign(sign)) {
                        continue;
                    }

                    SignClickListener.updateSign(
                            sign,
                            arenaManager);
                }
            }
        }
    }

    private boolean isSkyWarSign(Sign sign) {
        String firstLine = sign.getLine(0);
        String secondLine = sign.getLine(1);

        return firstLine.contains("Aberta")
                || firstLine.contains("VIP")
                || firstLine.contains("Em Jogo")
                || firstLine.contains("Finalizando")
                || firstLine.contains("Resetando")
                || firstLine.contains("Inválida")
                || firstLine.contains("Fechada")
                || secondLine.startsWith("SkyWar ");
    }
}