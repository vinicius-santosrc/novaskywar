package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.EndermanAbility;

public class Enderman extends Kit {

    public Enderman(EndermanAbility endermanAbility) {
        super("Enderman", true, new ItemStack(Material.ENDER_PEARL), true,
                new MenuOptions(
                        "§2Kit Enderman",
                        Arrays.asList("§fRecebe uma Ender Pearl +", "§f75% de chance de drop de Ender Pearl ao",
                                "§fmatar um jogador"),
                        5, 3),
                5500,
                endermanAbility);
        addItem(new ItemStack(Material.ENDER_PEARL));
    }
}