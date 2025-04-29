package com.chatcounter;

import com.google.inject.Provides;
import java.time.Instant;
import java.util.Locale;
import javax.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "Pet Roll Counter",
        description = "Tracks Tangleroot pet-roll attempts per patch",
        tags = {"pet", "farming", "counter"}
)
public class PetRollCounterPlugin extends Plugin
{
    @Inject private ConfigManager configManager;
    @Inject private PetRollCounterConfig config;
    @Inject private OverlayManager overlayManager;
    @Inject private PetRollCounterOverlay overlay;

    private boolean seaweedReady;
    private boolean mushroomReady;

    private int seaweedCount,
            cactusCount,
            belladonnaCount,
            mushroomCount,
            papayaCount,
            appleCount,
            teakCount,
            mahoganyCount,
            redwoodCount,
            calquatCount,
            hesporiCount,
            willowCount,
            magicCount,
            yewCount,
            celastrusCount,
            sessionCount;

    private Instant sessionStart;

    @Override
    protected void startUp() throws Exception
    {
        seaweedCount    = config.seaweedCount();
        cactusCount     = config.cactusCount();
        belladonnaCount = config.belladonnaCount();
        mushroomCount   = config.mushroomCount();
        papayaCount     = config.papayaCount();
        appleCount      = config.appleCount();
        teakCount       = config.teakCount();
        mahoganyCount   = config.mahoganyCount();
        redwoodCount    = config.redwoodCount();
        calquatCount    = config.calquatCount();
        hesporiCount    = config.hesporiCount();
        willowCount     = config.willowCount();
        magicCount      = config.magicCount();
        yewCount        = config.yewCount();
        celastrusCount  = config.celastrusCount();

        seaweedReady = true;
        mushroomReady = true;
        sessionCount = 0;
        sessionStart = Instant.now();

        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        ChatMessageType type = event.getType();
        if (type != ChatMessageType.GAMEMESSAGE && type != ChatMessageType.SPAM)
        {
            return;
        }

        String msg = event.getMessage().toLowerCase(Locale.ROOT);

        // Seaweed
        if (msg.contains("you plant a seaweed spore"))
        {
            seaweedReady = true;
        }
        else if (msg.contains("you pick some giant seaweed") && seaweedReady)
        {
            seaweedReady = false;
            seaweedCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "seaweedCount", seaweedCount);
        }

        // Cactus
        if (msg.equals("you pick some cactus spines."))
        {
            cactusCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "cactusCount", cactusCount);
        }

        // Belladonna
        if (msg.equals("you harvest some cave nightshade."))
        {
            belladonnaCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "belladonnaCount", belladonnaCount);
        }

        //
        // MUSHROOM: one line per harvest
        //

        if (msg.contains("you plant a bittercap mushroom"))
        {
            mushroomReady = true;
        }
        else if (msg.equals("you pick a bittercap mushroom.") && mushroomReady)
        {
            mushroomReady = false;
            mushroomCount++;
            sessionCount++;
            configManager.setConfiguration("petRollCounter", "mushroomCount", mushroomCount);
        }

        // Papaya
        if (msg.equals("you examine the health of the papaya tree."))
        {
            papayaCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "papayaCount", papayaCount);
        }

        // Apple
        if (msg.equals("you examine the health of the apple tree."))
        {
            appleCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "appleCount", appleCount);
        }

        // Teak
        if (msg.equals("you examine the health of the teak tree."))
        {
            teakCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "teakCount", teakCount);
        }

        // Mahogany
        if (msg.equals("you examine the health of the mahogany tree."))
        {
            mahoganyCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "mahoganyCount", mahoganyCount);
        }

        // Redwood
        if (msg.equals("you examine the health of the redwood tree."))
        {
            redwoodCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "redwoodCount", redwoodCount);
        }

        // Calquat
        if (msg.equals("you examine the calquat tree for signs of disease and find that it is in perfect health."))
        {
            calquatCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "calquatCount", calquatCount);
        }

        // Hespori
        if (msg.contains("you harvest the hespori"))
        {
            hesporiCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "hesporiCount", hesporiCount);
        }

        // Willow
        if (msg.equals("you examine the health of the willow tree."))
        {
            willowCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "willowCount", willowCount);
        }

        // Magic
        if (msg.equals("you examine the health of the magic tree."))
        {
            magicCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "magicCount", magicCount);
        }

        // Yew
        if (msg.equals("you examine the health of the yew tree."))
        {
            yewCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "yewCount", yewCount);
        }

        // Celastrus
        if (msg.equals("you examine the health of the celastrus tree."))
        {
            celastrusCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter", "celastrusCount", celastrusCount);
        }
    }

    public int getSessionCount() { return sessionCount; }

    public Instant getSessionStart() { return sessionStart; }

    @Provides
    public PetRollCounterConfig provideConfig(ConfigManager manager)
    {
        return manager.getConfig(PetRollCounterConfig.class);
    }
}