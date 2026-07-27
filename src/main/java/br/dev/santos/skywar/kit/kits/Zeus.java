package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.ZeusAbility;

public class Zeus extends Kit {
    public Zeus() {
        super("Zeus", true, new ItemStack(Material.BLAZE_ROD), false,
                new MenuOptions(
                        "§aKit Zeus",
                        Arrays.asList(
                                "§fRecebe 3 Blaze Rods",
                                "§fQue podem soltar raios"),
                        2, 3),
                1000,
                new ZeusAbility());
        addItem(new ItemStack(Material.BLAZE_ROD));
    }
}