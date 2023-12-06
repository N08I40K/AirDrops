package ru.n08i40k.totp.commands.subcommands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import ru.n08i40k.totp.TOTPPlugin;
import ru.n08i40k.totp.commands.HighLayerCommand;
import ru.n08i40k.totp.locale.Locale;

import java.util.ArrayList;
import java.util.List;

public class LocaleCommand extends HighLayerCommand {
    private final List<String> actions = new ArrayList<>();

    TOTPPlugin plugin;
    Locale locale;
    public LocaleCommand() {
        super("locale");

        plugin = TOTPPlugin.getInstance();
        locale = plugin.getLocale();

        actions.add("reload");
        actions.add("set");
    }



    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(locale.getC("locale-select-action", String.join(", ", actions)));
            return false;
        }

        switch (args[0]) {
            case ("reload"): {
                if (!(sender.isOp() || sender.hasPermission("totp.locale.reload"))) {
                    sender.sendMessage(locale.getC("dont-have-permission"));
                    return false;
                }
                locale.reload();
                sender.sendMessage(locale.getC("locale-reload-has-been-reloaded"));
                return true;
            }
            case ("set"): {
                if (!(sender.isOp() || sender.hasPermission("totp.locale.set"))) {
                    sender.sendMessage(locale.getC("dont-have-permission"));
                    return false;
                }

                List<String> locales = locale.getAvailableLocalesNames();
                if (args.length < 2) {
                    sender.sendMessage(locale.getC("locale-set-select-locale", String.join(", ", locales)));
                    return false;
                }
                if (!locale.getAvailableLocalesNames().contains(args[1])) {
                    sender.sendMessage(locale.getC("locale-set-incorrect-locale", String.join(", ", locales)));
                    return false;
                } else {
                    plugin.getMainConfig().setLang(args[1]);
                    plugin.saveConfig();

                    locale.reload();

                    sender.sendMessage(locale.getC("locale-set-successful"));
                    return true;
                }

            }
            default: {
                sender.sendMessage(locale.getC("locale-incorrect-action", String.join(", ", actions)));
                return false;
            }
        }
    }

    @Override
    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            if (args[0].isEmpty()) {
                return actions;
            }

            String lastWord = args[0];
            List<String> matchedActions = new ArrayList<>();

            for (String action : actions) {
                if (StringUtil.startsWithIgnoreCase(action, lastWord)) {
                    matchedActions.add(action);
                }
            }

            return matchedActions;
        }

        if (args.length >= 1) {
            switch (args[0]) {
                case ("reload"): {
                    return ImmutableList.of();
                }
                case ("set"): {
                    if (!(sender.isOp() || sender.hasPermission("totp.locale.set"))) {
                        return ImmutableList.of();
                    }

                    List<String> locales = locale.getAvailableLocalesNames();

                    if (args[1].isEmpty()) {
                        return locales;
                    }

                    String lastWord = args[1];
                    List<String> matchedLocales = new ArrayList<>();

                    for (String locale : locales) {
                        if (StringUtil.startsWithIgnoreCase(locale, lastWord)) {
                            matchedLocales.add(locale);
                        }
                    }

                    return matchedLocales;
                }
                default:
                    break;
            }
        }


        return ImmutableList.of();
    }
}
