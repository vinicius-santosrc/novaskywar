package br.dev.santos.skywar.kit;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.economy.MoneyManager;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;

public final class KitShopService {

    private final PlayerManager playerManager;
    private final KitManager kitManager;
    private final MoneyManager moneyManager;

    public KitShopService(PlayerManager playerManager, KitManager kitManager, MoneyManager moneyManager) {
        this.kitManager = kitManager;
        this.moneyManager = moneyManager;
        this.playerManager = playerManager;
    }

    public void buyNewKit(Player player, String kit) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();
        String Credits = "1000";

        PlayerData playerData = this.playerManager.get(player);

        List<String> helpMessages = config.getStringList("messages.buy_new_kit");

        for (String message : helpMessages) {
            player.sendMessage(message
                    .replace("{kit}", kit)
                    .replace("{credits}", Credits));
        }
    }
}