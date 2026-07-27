package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.AssassinoAbility;

public class Assassino extends Kit {
    public Assassino() {
        super("Assassino", true, new ItemStack(Material.FIREWORK), false,
                new MenuOptions(
                        "§eKit Assassino",
                        Arrays.asList("§f30 segundos de Força após matar um jogador"),
                        4, 3
                ),
                1000
            , new AssassinoAbility());
    }
}
