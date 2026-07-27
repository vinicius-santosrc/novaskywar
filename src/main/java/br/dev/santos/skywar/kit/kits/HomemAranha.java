package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class HomemAranha extends Kit {
    public HomemAranha() {
        super("Homem-Aranha", true, new ItemStack(Material.WEB), false,
                new MenuOptions(
                        "§bKit Homem Aranha",
                        Arrays.asList(
                                "§fRecebe 5 Teias"),
                        1, 3),
                1000);
        addItem(new ItemStack(Material.WEB, 5));
    }
}
