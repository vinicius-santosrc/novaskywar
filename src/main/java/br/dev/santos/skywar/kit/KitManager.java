/**
 * 
 * NovaSkyWar Kit
 * Criado por Vinicius Santos em 21/07/2026.
 * Copyright (c) 2026 Vinicius Santos. Todos os direitos reservados.
 * Obs: Este código é parte do projeto NovaSkyWar e não deve ser distribuído sem autorização.
 * 
 * Essa classe registra e procura kits.
*/

package br.dev.santos.skywar.kit;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.kit.ability.abilities.EndermanAbility;
import br.dev.santos.skywar.kit.ability.abilities.VidaExtraAbility;
import br.dev.santos.skywar.kit.kits.Apple;
import br.dev.santos.skywar.kit.kits.Arqueiro;
import br.dev.santos.skywar.kit.kits.Assassino;
import br.dev.santos.skywar.kit.kits.Construtor;
import br.dev.santos.skywar.kit.kits.Encantador;
import br.dev.santos.skywar.kit.kits.Enderman;
import br.dev.santos.skywar.kit.kits.Esquimo;
import br.dev.santos.skywar.kit.kits.Ferramentas;
import br.dev.santos.skywar.kit.kits.GrandPa;
import br.dev.santos.skywar.kit.kits.HomemAranha;
import br.dev.santos.skywar.kit.kits.HomemBomba;
import br.dev.santos.skywar.kit.kits.HomemDasCavernas;
import br.dev.santos.skywar.kit.kits.HomemFogo;
import br.dev.santos.skywar.kit.kits.Minerador;
import br.dev.santos.skywar.kit.kits.Ninja;
import br.dev.santos.skywar.kit.kits.Paladino;
import br.dev.santos.skywar.kit.kits.PesoPena;
import br.dev.santos.skywar.kit.kits.Poseidon;
import br.dev.santos.skywar.kit.kits.Sacerdote;
import br.dev.santos.skywar.kit.kits.Sopa;
import br.dev.santos.skywar.kit.kits.TheFlash;
import br.dev.santos.skywar.kit.kits.Vampiro;
import br.dev.santos.skywar.kit.kits.VidaExtra;
import br.dev.santos.skywar.kit.kits.Zeus;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.warp.WarpManager;

import java.util.ArrayList;

public class KitManager implements Listener {

    // Array estático para armazenar os kits
    private static ArrayList<Kit> kits = new ArrayList<Kit>();
    private final PlayerManager playerManager;
    private final WarpManager warpManager;
    private final ArenaMessenger arenaMessenger;

    public KitManager(PlayerManager playerManager, WarpManager warpManager, ArenaMessenger arenaMessenger) {
        registerDefaults();
        this.playerManager = playerManager;
        this.arenaMessenger = arenaMessenger;
        this.warpManager = warpManager;
    }

    //Método para dar do kit ao player
    public void giveItemsToPlayer(Player player, Kit kitPlayer) {
        if (kitPlayer == null) {
            return;
        }

        Inventory inventory = player.getInventory();

        for (ItemStack item : kitPlayer.getItems()) {
            if (item != null) {
                inventory.addItem(item.clone());
            }
        }
    }

    // Método para registrar kit
    public void register(Kit kit) {
        kits.add(kit);
    }

    // Método para registrar os kits defaults
    public void registerDefaults() {
        register(new Apple());
        register(new Arqueiro());
        register(new Assassino());
        register(new Construtor());
        register(new Encantador());
        register(new Enderman(new EndermanAbility(this.playerManager)));
        register(new Esquimo());
        register(new Ferramentas());
        register(new GrandPa());
        register(new HomemAranha());
        register(new HomemBomba());
        register(new HomemDasCavernas());
        register(new HomemFogo());
        register(new Minerador());
        register(new Ninja());
        register(new Paladino());
        register(new PesoPena());
        register(new Poseidon());
        register(new Sacerdote());
        register(new Sopa());
        register(new TheFlash());
        register(new Vampiro());
        register(new VidaExtra(new VidaExtraAbility(this.playerManager, this.warpManager, this.arenaMessenger)));
        register(new Zeus());
    }

    // Método para obter todos os kits
    public ArrayList<Kit> getKits() {
        return kits;
    }

    // Método para pegar o kit pelo nome
    public Kit get(String kitName) {
        if (kitName == null) {
            return null;
        }

        for (Kit kit : kits) {
            if (kit != null
                    && kit.getName() != null
                    && kit.getName().equalsIgnoreCase(kitName)) {

                return kit;
            }
        }

        return null;
    }
    
    // Método para obter o ícone de um kit pelo nome
    public ItemStack getKitIcon(Kit kitName) {
        for (Kit kit : kits) {
            if (kit.getName() == kitName.getName()) {
                return kit.getIcon();
            }
        }
        return null;
    }
    
}