package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Construtor extends Kit{
    public Construtor() {
        super("Construtor", true, new ItemStack(Material.STONE), false,
                new MenuOptions(
                        "§bKit Construtor",
                        Arrays.asList("§fRecebe 32 blocos de pedra"),
                        1, 2),
                1000
                    );
        addItem(new ItemStack(Material.STONE, 32));
    }
}
