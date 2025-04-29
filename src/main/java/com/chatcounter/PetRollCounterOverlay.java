package com.chatcounter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.time.Instant;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

public class PetRollCounterOverlay extends Overlay
{
    private final PanelComponent panel = new PanelComponent();
    private final PetRollCounterConfig config;
    private final PetRollCounterPlugin plugin;

    @Inject
    public PetRollCounterOverlay(PetRollCounterConfig config, PetRollCounterPlugin plugin)
    {
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D g)
    {
        if (!config.showOverlay())
        {
            return null;
        }

        panel.getChildren().clear();

        panel.getChildren().add(LineComponent.builder()
                .left("Seaweed:")
                .right(String.valueOf(config.seaweedCount()))
                .build());

        panel.getChildren().add(LineComponent.builder()
                .left("Cactus:")
                .right(String.valueOf(config.cactusCount()))
                .build());

        panel.getChildren().add(LineComponent.builder()
                .left("Belladonna:")
                .right(String.valueOf(config.belladonnaCount()))
                .build());

        panel.getChildren().add(LineComponent.builder()
                .left("Mushrooms:")
                .right(String.valueOf(config.mushroomCount()))
                .build());

        panel.getChildren().add(LineComponent.builder()
                .left("Papaya:")
                .right(String.valueOf(config.papayaCount()))
                .build());

        panel.getChildren().add(LineComponent.builder()
                .left("Apple:")
                .right(String.valueOf(config.appleCount()))
                .build());

        // Per-session rolls
        panel.getChildren().add(LineComponent.builder()
                .left("Session:")
                .right(String.valueOf(plugin.getSessionCount()))
                .build());

        // Average rate = sessionCount / hours
        Instant now = Instant.now();
        double hours = (now.toEpochMilli() - plugin.getSessionStart().toEpochMilli()) / 3_600_000.0;
        String rate = hours > 0
                ? String.format("%.2f/hr", plugin.getSessionCount() / hours)
                : "---";
        panel.getChildren().add(LineComponent.builder()
                .left("Rate:")
                .right(rate)
                .build());

        return panel.render(g);
    }
}
