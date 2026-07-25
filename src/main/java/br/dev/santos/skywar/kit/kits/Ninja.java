package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Ninja extends Kit{
    public Ninja() {
        super("Ninja", true, new ItemStack(Material.WOOL, 1, (short) 15), false);
        
        ItemStack leather_chestplace = new ItemStack(Material.LEATHER_CHESTPLATE);
        leather_chestplace.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        addItem(leather_chestplace);
    }
}
