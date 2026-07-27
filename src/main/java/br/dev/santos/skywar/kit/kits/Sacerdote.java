package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Sacerdote extends Kit {
    public Sacerdote() {
        super("Sacerdote", true, new ItemStack(Material.POTION), false,
                new MenuOptions(
                        "§1Kit Sacerdote",
                        Arrays.asList(
                                "§fRecebe 3 Poções de Vida e 3 Poções de Regeneração"),
                        4, 1),
                1000);

        ItemStack lifePotion = new ItemStack(Material.POTION, 3, (short) 1);
        ItemStack regenerationPotion = new ItemStack(Material.POTION, 3, (short) 1);

        addItem(lifePotion);
        addItem(regenerationPotion);
    }
}
