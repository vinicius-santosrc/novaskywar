package br.dev.santos.skywar.kit.ability;

import org.bukkit.event.Listener;

public abstract class Ability implements Listener {

    private final String name;

    protected Ability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}