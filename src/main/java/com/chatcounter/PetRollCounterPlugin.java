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

    private boolean seaweedReady,
            cactusReady,
            belladonnaReady,
            mushroomReady,
            papayaReady,
            appleReady;

    private int seaweedCount,
            cactusCount,
            belladonnaCount,
            mushroomCount,
            papayaCount,
            appleCount,
            sessionCount;

    private Instant sessionStart;

    @Override
    protected void startUp() throws Exception
    {
        // Load persisted totals
        seaweedCount    = config.seaweedCount();
        cactusCount     = config.cactusCount();
        belladonnaCount = config.belladonnaCount();
        mushroomCount   = config.mushroomCount();
        papayaCount     = config.papayaCount();
        appleCount      = config.appleCount();

        // All patches ready immediately after login
        seaweedReady = cactusReady = belladonnaReady =
                mushroomReady = papayaReady = appleReady = true;

        // Start session counter
        sessionCount = 0;
        sessionStart = Instant.now();

        // Always register overlay; it will hide itself if disabled
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

        // SEAWEED
        if (msg.contains("you plant a seaweed spore"))
        {
            seaweedReady = true;
        }
        else if (msg.contains("you pick some giant seaweed") && seaweedReady)
        {
            seaweedReady = false;
            seaweedCount++;
            sessionCount++;
            configManager.setConfiguration("petRollCounter", "seaweedCount", seaweedCount);
        }

        // CACTUS
        if (msg.equals("you plant a cactus seed in the patch."))
        {
            cactusReady = true;
        }
        else if (msg.equals("you pick some cactus spines.") && cactusReady)
        {
            cactusReady = false;
            cactusCount++;
            sessionCount++;
            configManager.setConfiguration("petRollCounter", "cactusCount", cactusCount);
        }

        // BELLADONNA
        if (msg.equals("you plant a cave nightshade seed in the patch."))
        {
            belladonnaReady = true;
        }
        else if (msg.equals("you harvest some cave nightshade.") && belladonnaReady)
        {
            belladonnaReady = false;
            belladonnaCount++;
            sessionCount++;
            configManager.setConfiguration("petRollCounter", "belladonnaCount", belladonnaCount);
        }

        // MUSHROOM
        if (msg.equals("you plant a bittercap mushroom spore in the patch."))
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

        // PAPAYA TREE
        if (msg.equals("you plant a papaya tree sapling in the patch."))
        {
            papayaReady = true;
        }
        else if (msg.equals("you examine the health of the papaya tree.") && papayaReady)
        {
            papayaReady = false;
            papayaCount++;
            sessionCount++;
            configManager.setConfiguration("petRollCounter", "papayaCount", papayaCount);
        }

        // APPLE TREE
        if (msg.equals("you plant an apple tree sapling in the patch."))
        {
            appleReady = true;
        }
        else if (msg.equals("you examine the health of the apple tree.") && appleReady)
        {
            appleReady = false;
            appleCount++;
            sessionCount++;
            configManager.setConfiguration("petRollCounter", "appleCount", appleCount);
        }
    }

    public int getSessionCount()
    {
        return sessionCount;
    }

    public Instant getSessionStart()
    {
        return sessionStart;
    }

    @Provides
    public PetRollCounterConfig provideConfig(ConfigManager manager)
    {
        return manager.getConfig(PetRollCounterConfig.class);
    }
}
