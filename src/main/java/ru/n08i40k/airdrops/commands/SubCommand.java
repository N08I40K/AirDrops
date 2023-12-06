package ru.n08i40k.antipvpleave.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.n08i40k.antipvpleave.AntiPvPLeave;
import ru.n08i40k.antipvpleave.utils.PluginPermissionBuilder;
import ru.n08i40k.antipvpleave.utils.PluginUse;

import java.util.*;

public abstract class SubCommand extends PluginUse {
    protected final SubCommandMap subcommands;

    private final PluginPermissionBuilder permissionBuilder;

    public SubCommand(@Nullable SubCommand parentCommand, @Nullable PluginPermissionBuilder permissionBuilder) {
        if (parentCommand == null && permissionBuilder == null)
            throw new IllegalArgumentException("Both empty arguments is not allowed!");

        PluginPermissionBuilder parentPermissionBuilder = permissionBuilder == null ?
                Objects.requireNonNull(parentCommand).getPermissionBuilder() : permissionBuilder;

        this.permissionBuilder = parentPermissionBuilder.extend(getName());

        subcommands = new SubCommandMap();
    }

    @NotNull
    public abstract String getName();

    @NotNull
    protected PluginPermissionBuilder getPermissionBuilder() {
        return permissionBuilder;
    }

    public abstract boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args);

    public boolean tryExecute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1 && subcommands.containsKey(args[0]))
            return subcommands.get(args[0]).tryExecute(sender, label, Arrays.copyOfRange(args, 1, args.length));

        if (!permissionBuilder.check(sender)) {
            locale.get("dont-have-permission").getSingle().sendMessage(sender);
            return false;
        }

        return execute(sender, label, args);
    }

    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (!permissionBuilder.check(sender))
            return ImmutableList.of();

        if (args.length == 0)
            return ImmutableList.of();

        if (args.length == 1)
            return getAutocompletion(args[0], subcommands.keySet());

        if (subcommands.containsKey(args[0]))
            return subcommands.get(args[0]).tabComplete(sender, alias, Arrays.copyOfRange(args, 1, args.length));

        return ImmutableList.of();
    }

    @NotNull
    protected static List<String> getAutocompletion(@NotNull String arg, @NotNull Set<String> variants) {
        if (arg.isEmpty()) {
            return new ArrayList<>(variants);
        }
        List<String> matched = new ArrayList<>();

        for (String variant : variants) {
            if (StringUtil.startsWithIgnoreCase(variant, arg)) {
                matched.add(variant);
            }
        }

        return matched;
    }
}
