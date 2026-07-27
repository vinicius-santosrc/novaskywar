package br.dev.santos.skywar.kit.kits;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Encantador extends Kit {
    public Encantador() {
        super("Encantador", true, new ItemStack(Material.ENCHANTMENT_TABLE), false,
                new MenuOptions(
                        "§eKit Encantador",
                        Arrays.asList("§fRecebe 5 Poções de EXP"),
                        9, 1),
                1000);
        addItem(new ItemStack(Material.ENCHANTMENT_TABLE));
        addItem(new ItemStack(Material.EXP_BOTTLE, 5));
    }
}
