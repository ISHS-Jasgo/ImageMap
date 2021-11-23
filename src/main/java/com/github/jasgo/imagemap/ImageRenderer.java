package com.github.jasgo.imagemap;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageRenderer extends MapRenderer {

    private BufferedImage image;
    private boolean done;

    public ImageRenderer() {
        done = false;
    }

    public ImageRenderer(String url) {
        load(url);
        done = false;
    }

    public boolean load(String url) {
        BufferedImage image;
        try {
            image = ImageIO.read(new URL(url));
            image = MapPalette.resizeImage(image);
        } catch (IOException e) {
            return false;
        }
        this.image = image;
        return true;
    }

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        if (done) return;
        canvas.drawImage(0, 0, image);
        map.setTrackingPosition(false);
        done = true;
    }
}
