package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class HomemFogo extends Kit {
    public HomemFogo() {
        super("Homem-Fogo", true, new ItemStack(Material.WOOD_SWORD), false);

        ItemStack wood_sword = new ItemStack(Material.WOOD_SWORD);
        wood_sword.addEnchantment(Enchantment.FIRE_ASPECT, 1);

        addItem(wood_sword);
    }
}
