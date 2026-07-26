package br.dev.santos.skywar.arena;

import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import br.dev.santos.skywar.player.PlayerData;

public class GameRewardService {

        private final FileConfiguration config;
        private final ArenaMessenger arenaMessenger;

        public GameRewardService(
                        FileConfiguration config,
                        ArenaMessenger arenaMessenger) {

                this.config = config;
                this.arenaMessenger = arenaMessenger;
        }

        public void rewardWinner(PlayerData playerWinner) {
                Player playerWinnerEntity = playerWinner.getPlayerEntity();

                String message = this.config.getString(
                                "messages.winning_message");

                playerWinner.addCredits("vitória", 100);
                playerWinnerEntity.sendMessage(message);
        }

        public void sendGameFinishedMessages(
                        Arena arena,
                        PlayerData playerWinner) {

                Player playerWinnerEntity = playerWinner.getPlayerEntity();

                String messageFinished = this.config
                                .getString("messages.game_finished")
                                .replace(
                                                "{player}",
                                                playerWinnerEntity.getDisplayName());

                this.arenaMessenger.sendMessageToArena(
                                arena,
                                messageFinished);

                String messageLobby = this.config.getString(
                                "messages.lobby_message");

                this.arenaMessenger.sendMessageToArena(
                                arena,
                                messageLobby);
        }

        public void rewardParticipants(Arena arena) {
                for (PlayerData playerData : arena.getPlayers()) {
                        playerData.addCredits("participação", 20);
                }
        }

        public void sendCreditsSummary(Arena arena) {
                String messageCredits = this.config.getString(
                                "messages.credits_count_final_match");

                for (PlayerData playerData : arena.getPlayers()) {
                        Player playerEntity = playerData.getPlayerEntity();

                        playerEntity.sendMessage("");

                        playerEntity.sendMessage(
                                        messageCredits.replace(
                                                        "{credits}",
                                                        String.valueOf(
                                                                        playerData.getCreditsEarn())));

                        playerEntity.sendMessage("");

                        sendCreditsEarned(
                                        playerData,
                                        playerEntity);

                        playerEntity.sendMessage("");
                }
        }

        private void sendCreditsEarned(
                        PlayerData playerData,
                        Player playerEntity) {

                for (Map.Entry<String, Integer> credit : playerData.getCreditsEarnList().entrySet()) {

                        int eliminations = (credit.getValue() / 8);
                        if (credit.getKey().equals("dar o First Flood")) {
                                eliminations++;
                        }

                        if (credit.getKey().equals(
                                        "eliminar {X} jogador")) {

                                playerEntity.sendMessage(
                                                "   §f+ "
                                                                + credit.getValue()
                                                                + " §6por "
                                                                + credit.getKey().replace(
                                                                                "{X}",
                                                                                String.valueOf(eliminations)
                                                                                                + (eliminations > 1
                                                                                                                ? "es"
                                                                                                                : "")));

                        } else if (credit.getKey().equals("vitória")) {
                                playerEntity.sendMessage(
                                                "   §f+ "
                                                                + credit.getValue()
                                                                + " §6pela "
                                                                + credit.getKey());

                        } else {
                                playerEntity.sendMessage(
                                                "   §f+ "
                                                                + credit.getValue()
                                                                + " §6por "
                                                                + credit.getKey());
                        }
                }
        }
}