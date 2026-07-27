package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class GrandPa extends Kit {
    public GrandPa() {
        super("GrandPa", true, new ItemStack(Material.GOLD_SPADE), false,
                new MenuOptions(
                        "§dKit Grandpa",
                        Arrays.asList(
                                "§fRecebe uma pá com Repulsão I com 10 hits"),
                        7, 2),
                1000);
        ItemStack gold_spade = new ItemStack(Material.GOLD_SPADE);
        gold_spade.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
        addItem(gold_spade);
    }
}
