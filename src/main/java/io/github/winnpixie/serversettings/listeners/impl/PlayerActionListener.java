package io.github.winnpixie.serversettings.listeners.impl;

import io.github.winnpixie.serversettings.Config;
import io.github.winnpixie.serversettings.ServerSettings;
import io.github.winnpixie.serversettings.listeners.BaseListener;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerActionListener extends BaseListener {
    public PlayerActionListener(ServerSettings plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerDamaged(EntityDamageByEntityEvent event) {
        if (Config.ALLOW_PVP) return;
        if (!(event.getEntity() instanceof Player)) return;

        var killer = event.getDamager();
        if (killer instanceof Projectile projectile) {
            if (!(projectile.getShooter() instanceof LivingEntity)) return;
            killer = (LivingEntity) projectile.getShooter();
        }

        if (killer instanceof Player) event.setCancelled(true);
    }
}
