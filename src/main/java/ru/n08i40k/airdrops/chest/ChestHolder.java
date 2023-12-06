package ru.n08i40k.airdrops.gui;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GuiHolder implements InventoryHolder {
    public ChanceChangingLine getChanceChangingLine() {
        return chanceChangingLine;
    }

    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public @NotNull List<ItemStackWithChance> getCustomInventory() {
        return customInventory;
    }

    public String getChestName() {
        return this.chestName;
    }

    public boolean isChangingChances() {
        return changingChances;
    }

    public void switchChangingChances() {
        this.changingChances = chanceChangingLine.switchChanging();
    }

    public void updateInventory() {
        chanceChangingLine.updateInventory();

        inventory.clear();

        for (int i = 0; i < customInventory.size(); ++i) {
            inventory.setItem(i, customInventory.get(i));
        }

        List<ItemStack> line = chanceChangingLine.getLine();

        for (int i = 0; i < 9; ++i) {
            inventory.setItem(inventory.getSize() - 9 + i, line.get(i));
        }
    }

    private final Inventory inventory;
    private final List<ItemStackWithChance> customInventory;
    private final String chestName;
    private boolean changingChances;
    private final ChanceChangingLine chanceChangingLine;

    public GuiHolder(TextComponent title, String chestName) {
        this.inventory = Bukkit.createInventory(this, 54, title);
        this.chestName = chestName;
        customInventory = new ArrayList<>(54-9);
        chanceChangingLine = new ChanceChangingLine(this);
        changingChances = false;
    }
}