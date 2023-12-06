package ru.n08i40k.totp;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.n08i40k.totp.config.MainConfig;
import ru.n08i40k.totp.config.UserEntry;
import ru.n08i40k.totp.locale.Locale;

import java.net.UnknownHostException;

public class EventsListener implements Listener {
    private final TOTPPlugin plugin;

    public EventsListener() {
        plugin = TOTPPlugin.getInstance();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        String name = event.getPlayer().getName().toLowerCase();
        String uuid = event.getPlayer().getUniqueId().toString();

        MainConfig mainConfig = plugin.getMainConfig();

        for (UserEntry userEntry : mainConfig.getUsers()) {
            if (userEntry.getNickname().equals(name)) {
                Server server = plugin.getServer();

                for (String cmd : mainConfig.getLogoutCommands()) {
                    server.dispatchCommand(
                            server.getConsoleSender(),
                            cmd.replace("{name}", userEntry.getNickname()));
                }

                for (String cmd : userEntry.getLogoutCommands()) {
                    server.dispatchCommand(
                            server.getConsoleSender(),
                            cmd.replace("{name}", userEntry.getNickname()));
                }
            }
        }

        for (UserEntry userEntry : mainConfig.getUsers()) {
            if (
                    (name.equals(userEntry.getNickname()) && !uuid.equals(userEntry.getUuid())) ||
                    (!name.equals(userEntry.getNickname()) && uuid.equals(userEntry.getUuid()))
            ) {
                plugin.getSLF4JLogger().warn("Current name: {}", name);
                plugin.getSLF4JLogger().warn("Original name: {}", userEntry.getNickname());
                plugin.getSLF4JLogger().warn("Current UUID: {}", uuid);
                plugin.getSLF4JLogger().warn("Original UUID: {}", userEntry.getUuid());
                event.getPlayer().kick(Component.text("Don't spoof plz"));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String name = event.getPlayer().getName().toLowerCase();

        MainConfig mainConfig = plugin.getMainConfig();

        for (UserEntry userEntry : mainConfig.getUsers()) {
            if (userEntry.getNickname().equals(name)) {
                Server server = plugin.getServer();

                for (String cmd : mainConfig.getLogoutCommands()) {
                    server.dispatchCommand(
                            server.getConsoleSender(),
                            cmd.replace("{name}", userEntry.getNickname()));
                }

                for (String cmd : userEntry.getLogoutCommands()) {
                    server.dispatchCommand(
                            server.getConsoleSender(),
                            cmd.replace("{name}", userEntry.getNickname()));
                }
            }
        }
    }
}
