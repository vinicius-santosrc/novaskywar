package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class PesoPena extends Kit {
    public PesoPena() {
        super("Peso-pena", true, new ItemStack(Material.FEATHER), false);
        ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);
        ironBoots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        addItem(ironBoots);
    }
}
