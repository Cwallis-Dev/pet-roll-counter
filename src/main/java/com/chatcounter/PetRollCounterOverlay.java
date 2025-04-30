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
        {
            return null;
        }

        panel.getChildren().clear();

        // Helper: format accumulated chance for a given count & rate
        java.util.function.BiFunction<Integer,Integer,String> fmtAccum = (count, rate) -> {
            if (count == 0) return "---";
            double p = 1.0 - Math.pow(1.0 - 1.0 / rate, count);
            return String.format("%.2f%%", 100 * p);
        };

        // Seaweed
        int swCount = config.seaweedCount();
        int swRate  = plugin.getRateSeaweed();
        panel.getChildren().add(LineComponent.builder()
                .left("Seaweed:")
                .right(fmtAccum.apply(swCount, swRate))
                .build());

        // Mushroom
        int muCount = config.mushroomCount();
        int muRate  = plugin.getRateMushroom();
        panel.getChildren().add(LineComponent.builder()
                .left("Mushroom:")
                .right(fmtAccum.apply(muCount, muRate))
                .build());

        // Cactus
        int caCount = config.cactusCount();
        int caRate  = plugin.getRateCactus();
        panel.getChildren().add(LineComponent.builder()
                .left("Cactus:")
                .right(fmtAccum.apply(caCount, caRate))
                .build());

        // Belladonna
        int beCount = config.belladonnaCount();
        int beRate  = plugin.getRateBelladonna();
        panel.getChildren().add(LineComponent.builder()
                .left("Belladonna:")
                .right(fmtAccum.apply(beCount, beRate))
                .build());

        // Papaya
        int paCount = config.papayaCount();
        int paRate  = plugin.getRatePapaya();
        panel.getChildren().add(LineComponent.builder()
                .left("Papaya:")
                .right(fmtAccum.apply(paCount, paRate))
                .build());

        // Apple
        int apCount = config.appleCount();
        int apRate  = plugin.getRateApple();
        panel.getChildren().add(LineComponent.builder()
                .left("Apple:")
                .right(fmtAccum.apply(apCount, apRate))
                .build());

        // Teak
        int teCount = config.teakCount();
        int teRate  = plugin.getRateTeak();
        panel.getChildren().add(LineComponent.builder()
                .left("Teak:")
                .right(fmtAccum.apply(teCount, teRate))
                .build());

        // Mahogany
        int maCount = config.mahoganyCount();
        int maRate  = plugin.getRateMahogany();
        panel.getChildren().add(LineComponent.builder()
                .left("Mahogany:")
                .right(fmtAccum.apply(maCount, maRate))
                .build());

        // Redwood
        int reCount = config.redwoodCount();
        int reRate  = plugin.getRateRedwood();
        panel.getChildren().add(LineComponent.builder()
                .left("Redwood:")
                .right(fmtAccum.apply(reCount, reRate))
                .build());

        // Calquat
        int clCount = config.calquatCount();
        int clRate  = plugin.getRateCalquat();
        panel.getChildren().add(LineComponent.builder()
                .left("Calquat:")
                .right(fmtAccum.apply(clCount, clRate))
                .build());

        // Willow
        int wiCount = config.willowCount();
        int wiRate  = plugin.getRateWillow();
        panel.getChildren().add(LineComponent.builder()
                .left("Willow:")
                .right(fmtAccum.apply(wiCount, wiRate))
                .build());

        // Magic
        int mgCount = config.magicCount();
        int mgRate  = plugin.getRateMagic();
        panel.getChildren().add(LineComponent.builder()
                .left("Magic:")
                .right(fmtAccum.apply(mgCount, mgRate))
                .build());

        // Yew
        int ywCount = config.yewCount();
        int ywRate  = plugin.getRateYew();
        panel.getChildren().add(LineComponent.builder()
                .left("Yew:")
                .right(fmtAccum.apply(ywCount, ywRate))
                .build());

        // Celastrus
        int ceCount = config.celastrusCount();
        int ceRate  = plugin.getRateCelastrus();
        panel.getChildren().add(LineComponent.builder()
                .left("Celastrus:")
                .right(fmtAccum.apply(ceCount, ceRate))
                .build());

        // Hespori
        int heCount = config.hesporiCount();
        int heRate  = plugin.getRateHespori();
        panel.getChildren().add(LineComponent.builder()
                .left("Hespori:")
                .right(fmtAccum.apply(heCount, heRate))
                .build());

        // Combined overall chance
        double combined = plugin.getCombinedChance() * 100.0;
        panel.getChildren().add(LineComponent.builder()
                .left("Overall:")
                .right(String.format("%.2f%%", combined))
                .build());

        return panel.render(g);
    }
}
