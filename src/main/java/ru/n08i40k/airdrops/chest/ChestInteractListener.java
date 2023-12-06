package ru.n08i40k.airdrops.gui;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.n08i40k.airdrops.AirDrops;
import ru.n08i40k.airdrops.config.chests.ChestEntry;
import ru.n08i40k.airdrops.config.chests.ItemStackEntry;
import ru.n08i40k.airdrops.config.chests.ItemStackEntryAccessor;
import ru.n08i40k.airdrops.locale.Locale;

import java.util.List;
import java.util.Optional;

public class GuiInteractListener implements Listener {
    static class UpdateInventoryTask implements Runnable {
        private GuiHolder holder;

        public UpdateInventoryTask(GuiHolder holder) {
            this.holder = holder;
        }

        @Override
        public void run() {
            holder.updateInventory();
        }
    }

    private final AirDrops plugin;
    private final Locale locale;

    public GuiInteractListener() {
        plugin = AirDrops.getInstance();
        locale = plugin.getLocale();
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() != null && inventory.getHolder() instanceof GuiHolder holder) {
            Optional<ChestEntry> chestEntry = plugin.getChestsConfig().getChests().stream()
                    .filter((entry) -> entry.getName().equals(holder.getChestName()))
                    .findFirst();
            assert chestEntry.isPresent();

            chestEntry.get().getItems().clear();

            for (ItemStackWithChance itemStackWithChance : holder.getCustomInventory()) {
                itemStackWithChance.revertDescription();
                chestEntry.get().getItems().add(new ItemStackEntry(itemStackWithChance));
            }

            plugin.saveChestsConfig();

            locale.get("inventory.close").getSingle().sendMessage(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() != null && inventory.getHolder() instanceof GuiHolder holder) {
            List<ItemStackWithChance> customInventory = holder.getCustomInventory();

            for (Integer slot : event.getNewItems().keySet()) {
                if (slot > 9 * 5) {
                    event.setCancelled(true);
                    return;
                }
            }

            for (ItemStack itemStack : event.getNewItems().values()) {
                plugin.getSLF4JLogger().info(itemStack.toString());

                customInventory.add(new ItemStackWithChance(itemStack, 100.f));

                plugin.getScheduler().runTaskLater(plugin, new UpdateInventoryTask(holder), 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() != null && inventory.getHolder() instanceof GuiHolder holder) {

            List<ItemStackWithChance> customInventory = holder.getCustomInventory();

            event.setCancelled(true);

            if (event.getClickedInventory() == inventory) {

                if (event.getSlot() >= inventory.getSize() - 9) {

                    int slot = event.getSlot() - inventory.getSize() + 9;

                    ChanceChangingLine chanceChangingLine = holder.getChanceChangingLine();

                    switch (slot) {
                        case 0: {
                            chanceChangingLine.setCurrentChance(chanceChangingLine.getCurrentChance() - 10.f);
                            break;
                        }
                        case 1: {
                            chanceChangingLine.setCurrentChance(chanceChangingLine.getCurrentChance() - 1.f);
                            break;
                        }
                        case 2: {
                            chanceChangingLine.setCurrentChance(chanceChangingLine.getCurrentChance() - 0.1f);
                            break;
                        }
                        case 4: {
                            holder.switchChangingChances();
                            break;
                        }
                        case 6: {
                            chanceChangingLine.setCurrentChance(chanceChangingLine.getCurrentChance() + 0.1f);
                            break;
                        }
                        case 7: {
                            chanceChangingLine.setCurrentChance(chanceChangingLine.getCurrentChance() + 1.f);
                            break;
                        }
                        case 8: {
                            chanceChangingLine.setCurrentChance(chanceChangingLine.getCurrentChance() + 10.f);
                            break;
                        }
                        default: {
                            throw new IllegalArgumentException("Illegal item slot in gui!");
                        }
                    }

                    holder.updateInventory();
                    return;
                }

                if (holder.isChangingChances()) {
                    if (event.getSlot() > customInventory.size() - 1)
                        return;

                    ItemStackWithChance itemStackWithChance = customInventory.get(event.getSlot());

                    itemStackWithChance.setChance(holder.getChanceChangingLine().getCurrentChance());
                    itemStackWithChance.applyDescription();

                    holder.updateInventory();
                    return;
                }

                int slot = event.getSlot();

                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    if (slot > customInventory.size() - 1)
                        return;

                    event.setCancelled(false);
                    customInventory.remove(slot);

                    plugin.getScheduler().runTaskLater(plugin, new UpdateInventoryTask(holder), 1);

                    return;
                } else if (event.getAction() == InventoryAction.PLACE_ALL) {
                    ItemStack cursor = event.getCursor();
                    if (cursor == null || cursor.getType() == Material.AIR)
                        return;

                    event.setCancelled(false);

                    customInventory.add(new ItemStackWithChance(cursor, 100.f));

                    plugin.getScheduler().runTaskLater(plugin, new UpdateInventoryTask(holder), 1);

                    return;
                } else if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                    if (slot > customInventory.size() - 1)
                        return;

                    ItemStack cursor = event.getCursor();
                    if (cursor == null || cursor.getType() == Material.AIR)
                        return;

                    ItemStackWithChance cursorWithChance = new ItemStackWithChance(cursor, 100.f);

                    customInventory.set(slot, cursorWithChance);
                }

                //holder.updateInventory();
                return;
            }

            //holder.updateInventory();

            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                customInventory.add(new ItemStackWithChance(event.getCurrentItem(), 100.f));

                event.getClickedInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));

                holder.updateInventory();
            } else if (event.getAction() != InventoryAction.HOTBAR_SWAP) {
                event.setCancelled(false);
            }
        }
    }
}
