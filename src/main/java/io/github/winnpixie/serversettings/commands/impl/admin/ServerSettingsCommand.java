package io.github.winnpixie.serversettings.commands.impl.admin;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.winnpixie.hukkit.configs.adapters.BukkitAdapter;
import io.github.winnpixie.serversettings.ServerSettings;
import io.github.winnpixie.serversettings.commands.BaseCommand;
import io.github.winnpixie.serversettings.utilities.ChatHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ServerSettingsCommand extends BaseCommand {
    private final BaseComponent[] reloadedMessage = new ComponentBuilder("Settings reloaded!")
            .color(ChatColor.GREEN)
            .create();

    public ServerSettingsCommand(ServerSettings plugin) {
        super("server-settings", plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("server-settings.command") && !sender.isOp()) {
            sender.spigot().sendMessage(ChatHelper.NO_PERMISSION);
            return true;
        }

        if (args.length < 1) return true;

        switch (args[0].toLowerCase()) {
            case "reload", "rl" -> {
                this.getPlugin().reloadConfig();

                BukkitAdapter adapter = (BukkitAdapter) this.getPlugin().configManager.getAdapter();
                adapter.setBukkitConfig(this.getPlugin().getConfig());
                this.getPlugin().configManager.load();

                sender.spigot().sendMessage(reloadedMessage);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.singletonList("reload");
    }
}
