package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.VidaExtraAbility;

public class VidaExtra extends Kit {

    public VidaExtra(VidaExtraAbility vidaExtraAbility) {
        super("Vida-extra", true, new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1), true,
                new MenuOptions(
                        "§3Kit Vida Extra",
                        Arrays.asList(
                                "§fVolte para sua Ilha se voce cair ou morrer(1 Vez por Jogo)"),
                        3, 3),
                1000,
                vidaExtraAbility);
    }
}