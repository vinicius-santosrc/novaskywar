package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Arqueiro extends Kit {

    public Arqueiro() {
        super("Arqueiro", true, new ItemStack(Material.BOW), false);

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3); // Power III

        addItem(bow);
    }
}