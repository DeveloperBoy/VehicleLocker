package me.wouter.vehiclelocker.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.legofreak107.vehiclesplus.api.events.VehicleEnterEvent;

public class VehiclesPlusPluginListener implements Listener {

    @EventHandler
    public void onEnterVehicle(VehicleEnterEvent e) {
        Player player = e.getDriver();
        UUID ownerUUID = UUID.fromString(e.getVehicle().getOwnerUUID());
        if (ownerUUID != player.getUniqueId()) {
            if (Bukkit.getPlayer(ownerUUID) != null) {
                Player owner = Bukkit.getPlayer(ownerUUID);
                if (DataFile.getInstance().getData().getBoolean(owner.getUniqueId() + ".Locked")) {
                    player.sendMessage(Main.cc("VehicleIsLocked").replaceAll("<Player>", owner.getName()));
                    e.setCancelled(true);
                }
            }
        } else {
            if (DataFile.getInstance().getData().getBoolean(player.getUniqueId() + ".Locked")) {
                player.sendMessage(Main.cc("VehicleIsLockedAlert"));
            }
        }
    }

}
