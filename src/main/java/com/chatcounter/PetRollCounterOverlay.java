package com.chatcounter;

import java.awt.Dimension;
import java.awt.Graphics2D;
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
            return null;

        panel.getChildren().clear();

        panel.getChildren().add(LineComponent.builder().left("Seaweed:").right(String.valueOf(config.seaweedCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Mushroom:").right(String.valueOf(config.mushroomCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Cactus:").right(String.valueOf(config.cactusCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Belladonna:").right(String.valueOf(config.belladonnaCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Papaya:").right(String.valueOf(config.papayaCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Apple:").right(String.valueOf(config.appleCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Teak:").right(String.valueOf(config.teakCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Mahogany:").right(String.valueOf(config.mahoganyCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Redwood:").right(String.valueOf(config.redwoodCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Calquat:").right(String.valueOf(config.calquatCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Hespori:").right(String.valueOf(config.hesporiCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Willow:").right(String.valueOf(config.willowCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Magic:").right(String.valueOf(config.magicCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Yew:").right(String.valueOf(config.yewCount())).build());
        panel.getChildren().add(LineComponent.builder().left("Celastrus:").right(String.valueOf(config.celastrusCount())).build());

        panel.getChildren().add(LineComponent.builder().left("Session:").right(String.valueOf(plugin.getSessionCount())).build());

        return panel.render(g);
    }
}
