package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Ferramentas extends Kit {
    public Ferramentas() {
        super("Ferramentas", true, new ItemStack(Material.WORKBENCH), false,
                new MenuOptions(
                        "§aKit Ferramentas",
                        Arrays.asList(
                                "§fRecebe 1 mesa de trabalho,",
                                "§f1 fornalha, 1 machado de pedra,",
                                "§f1 picareta de pedra e 1 pá de madeira"),
                        6, 2),
                1000);
        addItem(new ItemStack(Material.WORKBENCH));
        addItem(new ItemStack(Material.FURNACE));
        addItem(new ItemStack(Material.STONE_AXE));
        addItem(new ItemStack(Material.STONE_PICKAXE));
        addItem(new ItemStack(Material.WOOD_SPADE));
    }
}
