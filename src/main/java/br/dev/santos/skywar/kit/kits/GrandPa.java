package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class GrandPa extends Kit {
    public GrandPa() {
        super("GrandPa", true, new ItemStack(Material.GOLD_SPADE), false);
        ItemStack gold_spade = new ItemStack(Material.GOLD_SPADE);
        gold_spade.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
        addItem(gold_spade);
    }
}
