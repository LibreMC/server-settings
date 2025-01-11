package io.github.winnpixie.serversettings;

import io.github.winnpixie.commons.spigot.SpigotHelper;
import io.github.winnpixie.commons.spigot.configs.adapters.BukkitAdapter;
import io.github.winnpixie.commons.spigot.configurations.AnnotatedConfigurator;
import io.github.winnpixie.commons.spigot.configurations.adapters.BukkitConfigurationAdapter;
import io.github.winnpixie.serversettings.commands.admin.ServerSettingsCommand;
import io.github.winnpixie.serversettings.listeners.ConnectionListener;
import io.github.winnpixie.serversettings.listeners.PlayerActionListener;
import io.github.winnpixie.serversettings.tasks.BroadcastToLANTask;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerSettings extends JavaPlugin {
    public final AnnotatedConfigurator configuration = new AnnotatedConfigurator();

    @Override
    public void onEnable() {
        super.saveDefaultConfig();

        configuration.setAdapter(new BukkitConfigurationAdapter(super.getConfig())).linkClass(Config.class).load();

        getServer().getScheduler().runTaskTimer(this, new BroadcastToLANTask(this), 0L, 30L);

        SpigotHelper.addListener(new ConnectionListener(this));
        SpigotHelper.addListener(new PlayerActionListener(this));

        SpigotHelper.addCommand(new ServerSettingsCommand(this));

        getLogger().info("ServerSettings init DONE.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ServerSettings unload DONE.");
    }
}
