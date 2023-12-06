package ru.n08i40k.airdrops.commands.subcommands.admin;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ru.n08i40k.airdrops.commands.SubCommand;
import ru.n08i40k.airdrops.config.chests.ChestEntry;
import ru.n08i40k.airdrops.gui.GuiUtils;

import java.util.*;

public class MenuCommand extends SubCommand {
    @Override
    public @NotNull String getName() {
        return "menu";
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (!(sender.isOp() || sender.hasPermission("airdrops.admin.menu"))) {
            locale.get("dont-have-permission").getSingle().sendMessage(sender);
            return false;
        }

        List<String> chests = new ArrayList<>();
        plugin.getChestsConfig().getChests().forEach(
                chestEntry -> chests.add(chestEntry.getName())
        );
        // Get chest name

        if (args.length == 0) {
            locale.get("admin.give_signal.chest-is-not-present", String.join(", ", chests)).getSingle().sendMessage(sender);
            return false;
        }

        String chestName = args[0];

        if (!chests.contains(chestName)) {
            locale.get("admin.give_signal.incorrect-chest", String.join(", ", chests)).getSingle().sendMessage(sender);
            return false;
        }

        Player player = (Player) sender;
        Optional<Inventory> inventory = GuiUtils.getInventory(chestName);

        if (inventory.isEmpty())
            return false;

        player.openInventory(inventory.get());
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            Set<String> chests = new HashSet<>();
            plugin.getChestsConfig().getChests().forEach(
                    chestEntry -> chests.add(chestEntry.getName())
            );

            return getAutocompletion(args[0], chests);
        }

        return ImmutableList.of();
    }
}
