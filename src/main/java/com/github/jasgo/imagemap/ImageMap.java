package com.github.jasgo.imagemap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ImageMap extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new ImageManager(), this);
        ImageManager manager = ImageManager.getInstance();
        manager.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("map")) {
            if (args.length == 1) {
                Player player = (Player) sender;
                MapView view = Bukkit.createMap(player.getWorld());
                for (MapRenderer mapRenderer : view.getRenderers()) {
                    view.removeRenderer(mapRenderer);
                }
                ImageRenderer renderer = new ImageRenderer();
                if (!renderer.load(args[0])) {
                    player.sendMessage(ChatColor.RED + "이미지 로딩 오류 발생");
                    return true;
                }
                view.addRenderer(renderer);
                ItemStack map = new ItemStack(Material.FILLED_MAP);
                MapMeta meta = (MapMeta) map.getItemMeta();
                meta.setMapView(view);
                map.setItemMeta(meta);
                player.getInventory().addItem(map);
                ImageManager manager = ImageManager.getInstance();
                manager.saveImage(view.getId(), args[0]);
            }
        }
        return false;
    }
}
