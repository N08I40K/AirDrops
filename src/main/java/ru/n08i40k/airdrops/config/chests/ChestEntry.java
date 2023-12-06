package ru.n08i40k.airdrops.config;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ChestEntry {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public List<String> getOn_spawn() {
        return on_spawn;
    }

    public void setOn_spawn(List<String> on_spawn) {
        this.on_spawn = on_spawn;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    String name;
    Material material;
    int chance;
    Sound sound;
    List<String> on_spawn;
    List<ItemStack> items;

    public ChestEntry() {
    }
}
