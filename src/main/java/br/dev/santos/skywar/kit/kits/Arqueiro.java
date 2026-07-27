package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Arqueiro extends Kit {

    public Arqueiro() {
        super("Arqueiro", true, new ItemStack(Material.BOW), false, 
            new MenuOptions(
                        "§bArqueiro",
                        Arrays.asList("§fRecebe um Arco Fire Aspect 1"),
                        1, 1
                ),
                1000);

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3); // Power III

        addItem(bow);
    }
}