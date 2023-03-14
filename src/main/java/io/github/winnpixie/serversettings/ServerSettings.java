package io.github.winnpixie.serversettings;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.winnpixie.hukkit.configs.AnnotatedConfigurationManager;
import io.github.winnpixie.hukkit.configs.adapters.BukkitAdapter;
import io.github.winnpixie.serversettings.commands.BaseCommand;
import io.github.winnpixie.serversettings.commands.impl.admin.ServerSettingsCommand;
import io.github.winnpixie.serversettings.listeners.BaseListener;
import io.github.winnpixie.serversettings.listeners.impl.ConnectionListener;

public class ServerSettings extends JavaPlugin {
    public final AnnotatedConfigurationManager configManager = new AnnotatedConfigurationManager();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        BukkitAdapter adapter = new BukkitAdapter();
        adapter.setBukkitConfig(this.getConfig());
        configManager.setAdapter(adapter).linkClass(Config.class).load();

        this.registerListener(new ConnectionListener(this));

        this.registerCommand(new ServerSettingsCommand(this));

        getLogger().info("ServerSettings init DONE.");
    }

    private void registerListener(BaseListener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerCommand(BaseCommand command) {
        var pluginCommand = getServer().getPluginCommand(command.getName());
        if (pluginCommand == null)
            return;

        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }

    @Override
    public void onDisable() {
        getLogger().info("Unload complete.");
    }
}
