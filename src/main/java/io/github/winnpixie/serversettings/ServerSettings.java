package io.github.winnpixie.serversettings;

import io.github.winnpixie.hukkit.Hukkit;
import io.github.winnpixie.hukkit.configs.AnnotatedConfigurationManager;
import io.github.winnpixie.hukkit.configs.adapters.BukkitAdapter;
import io.github.winnpixie.serversettings.commands.admin.ServerSettingsCommand;
import io.github.winnpixie.serversettings.listeners.ConnectionListener;
import io.github.winnpixie.serversettings.listeners.PlayerActionListener;
import io.github.winnpixie.serversettings.tasks.BroadcastToLANTask;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerSettings extends JavaPlugin {
    public final AnnotatedConfigurationManager configManager = new AnnotatedConfigurationManager();

    @Override
    public void onEnable() {
        super.saveDefaultConfig();

        configManager.setAdapter(new BukkitAdapter(super.getConfig())).linkClass(Config.class).load();

        getServer().getScheduler().runTaskTimer(this, new BroadcastToLANTask(this), 0L, 30L);

        Hukkit.addListener(new ConnectionListener(this));
        Hukkit.addListener(new PlayerActionListener(this));

        Hukkit.addCommand(new ServerSettingsCommand(this));

        getLogger().info("ServerSettings init DONE.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ServerSettings unload DONE.");
    }
}
