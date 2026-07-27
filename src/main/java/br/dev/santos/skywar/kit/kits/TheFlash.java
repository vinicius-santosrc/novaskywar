package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class TheFlash extends Kit {
    public TheFlash() {
        super("The-Flash", true, new ItemStack(Material.POTION, 1, (short) 2), false,
                new MenuOptions(
                        "§dKit Flash",
                        Arrays.asList(
                                "§fRecebe uma Poção de velocidade"),
                        8, 1),
                1000);
        addItem(new ItemStack(Material.POTION, 1, (short) 2));
    }
}
