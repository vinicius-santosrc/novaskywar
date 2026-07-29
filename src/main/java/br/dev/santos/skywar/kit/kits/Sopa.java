package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import br.dev.santos.skywar.kit.ability.abilities.SopaAbility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Sopa extends Kit {
    public Sopa(SopaAbility sopaAbility) {
        super("Sopa", true, new ItemStack(Material.MUSHROOM_SOUP), false,
                new MenuOptions(
                        "§aKit Sopa",
                        Arrays.asList(
                                "§fRecebe 3 sopas que regeneram 3 corações, cada"),
                        7, 3),
                1000, sopaAbility);

        addItem(new ItemStack(Material.MUSHROOM_SOUP, 3));
    }

}
