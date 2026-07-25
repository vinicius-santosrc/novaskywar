/**
 * 
 * NovaSkyWar Kit
 * Criado por Vinicius Santos em 21/07/2026.
 * Copyright (c) 2026 Vinicius Santos. Todos os direitos reservados.
 * Obs: Este código é parte do projeto NovaSkyWar e não deve ser distribuído sem autorização.
 * 
 * Essa classe representa o modelo do Kit no jogo.
*/

package br.dev.santos.skywar.kit;
import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.ability.Ability;

public abstract class Kit {
    private final String name;
    private final Boolean enabled;
    private final ItemStack icon;
    private final Boolean exclusiveVip;
    private Ability ability = null;
    private ArrayList<ItemStack> items;

    protected Kit(String name, Boolean enabled, ItemStack icon, Boolean exclusiveVip) {
        this(name, enabled, icon, exclusiveVip, null);
    }

    protected Kit(String name, Boolean enabled, ItemStack icon, Boolean exclusiveVip, Ability ability) {
        this.name = name;
        this.enabled = enabled;
        this.icon = icon;
        this.exclusiveVip = exclusiveVip;
        this.ability = ability;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public ItemStack getIcon() {
        return this.icon.clone();
    }

    public Boolean getExclusiveVip() {
        return this.exclusiveVip;
    }

    public Ability getAbility() {
        return this.ability;
    }

    public ArrayList<ItemStack> getItems() {
        return this.items;
    }

    public void addItem(ItemStack item) {
        this.items.add(item);
    }
}