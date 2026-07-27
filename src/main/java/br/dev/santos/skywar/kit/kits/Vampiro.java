package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Vampiro extends Kit {
    public Vampiro() {
        super("Vampiro", true, new ItemStack(Material.POTION, 1, (short) 1), false,
                new MenuOptions(
                        "§bKit Vampiro",
                        Arrays.asList(
                                "§fRecupera 5 corações ao matar um jogador."),
                        8, 3),
                1000);
    }
}
