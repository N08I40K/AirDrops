package ru.n08i40k.antipvpleave.commands;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ru.n08i40k.antipvpleave.AntiPvPLeave;
import ru.n08i40k.antipvpleave.commands.subcommands.AdminCommand;
import ru.n08i40k.antipvpleave.commands.subcommands.LocaleCommand;
import ru.n08i40k.antipvpleave.locale.Locale;
import ru.n08i40k.antipvpleave.utils.PluginPermissionBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommand extends Command {
    AntiPvPLeave plugin;
    Logger logger;
    Locale locale;

    List<SubCommand> subcommands = new ArrayList<>();

    public MainCommand() {
        super(AntiPvPLeave.PLUGIN_NAME_LOWER, "Main " + AntiPvPLeave.PLUGIN_NAME + " command.", "/" + AntiPvPLeave.PLUGIN_NAME_LOWER, new ArrayList<>());

        plugin = AntiPvPLeave.getInstance();

        locale = plugin.getLocale();
        logger = plugin.getSLF4JLogger();

        PluginPermissionBuilder permissionBuilder =
                new PluginPermissionBuilder(null, AntiPvPLeave.PLUGIN_NAME_LOWER)
                        .extend("commands");

        subcommands.add(new LocaleCommand(null, permissionBuilder));
        subcommands.add(new AdminCommand(null, permissionBuilder));
    }

    @Override
    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        if (args.length > 1)
            for (SubCommand subcommand : subcommands)
                if (subcommand.getName().equals(args[0]))
                    return subcommand.tabComplete(sender, alias, Arrays.copyOfRange(args, 1, args.length));

        String lastWord = args[0];
        List<String> matchedSubcommands = new ArrayList<>();

        for (SubCommand subcommand : subcommands) {
            if (!subcommand.getPermissionBuilder().check(sender))
                continue;

            if (lastWord.isEmpty() || StringUtil.startsWithIgnoreCase(subcommand.getName(), lastWord))
                matchedSubcommands.add(subcommand.getName());
        }

        matchedSubcommands.sort(String.CASE_INSENSITIVE_ORDER);
        return matchedSubcommands;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1)
            for (SubCommand subcommand : subcommands)
                if (subcommand.getName().equals(args[0]))
                    return subcommand.tryExecute(sender, label, Arrays.copyOfRange(args, 1, args.length));

        return false;
    }
}
