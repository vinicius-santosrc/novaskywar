package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class HomemAranha extends Kit {
    public HomemAranha() {
        super("Homem-Aranha", true, new ItemStack(Material.WEB), false);
        addItem(new ItemStack(Material.WEB, 5));
    }
}
