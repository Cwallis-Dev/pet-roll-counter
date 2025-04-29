package com.chatcounter;

import com.google.inject.Provides;
import java.time.Instant;
import java.util.Locale;
import javax.inject.Inject;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.StatChanged;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

@PluginDescriptor(
        name = "Pet Roll Counter",
        description = "Tracks Tangleroot pet-roll attempts",
        tags = {"pet","farming","counter"}
)
public class PetRollCounterPlugin extends Plugin
{
    @Inject private ConfigManager configManager;
    @Inject private PetRollCounterConfig config;
    @Inject private OverlayManager overlayManager;
    @Inject private PetRollCounterOverlay overlay;

    // Chat-based flags
    private boolean seaweedReady = true, mushroomReady = true;

    // Last tree clicked “check-health” on
    private String lastCheckTarget = null;
    private long    lastCheckTime = 0L;
    // Last seen Farming XP
    private int lastFarmingXp = -1;

    // Counters
    private int seaweedCount, mushroomCount,
            cactusCount, belladonnaCount, papayaCount,
            appleCount, teakCount, mahoganyCount,
            redwoodCount, calquatCount, hesporiCount,
            willowCount, magicCount, yewCount, celastrusCount,
            sessionCount;
    private Instant sessionStart;

    @Override
    protected void startUp() throws Exception
    {
        // Load persisted totals
        seaweedCount    = config.seaweedCount();
        mushroomCount   = config.mushroomCount();
        cactusCount     = config.cactusCount();
        belladonnaCount = config.belladonnaCount();
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

        sessionCount   = 0;
        sessionStart   = Instant.now();
        lastFarmingXp  = -1;  // will be set on first StatChanged

        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
    }

    // === SEAWEED, MUSHROOM & HESPORI (chat triggers) ===
    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (event.getType() != ChatMessageType.GAMEMESSAGE &&
                event.getType() != ChatMessageType.SPAM)
        {
            return;
        }

        String msg = event.getMessage().toLowerCase(Locale.ROOT);

        // Seaweed
        if (msg.contains("you plant a seaweed"))
        {
            seaweedReady = true;
        }
        else if (msg.contains("you pick some giant seaweed") && seaweedReady)
        {
            seaweedReady = false;
            seaweedCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter","seaweedCount",seaweedCount);
        }

        // Mushroom
        if (msg.contains("you plant a bittercap mushroom"))
        {
            mushroomReady = true;
        }
        else if (msg.contains("you pick a bittercap mushroom") && mushroomReady)
        {
            mushroomReady = false;
            mushroomCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter","mushroomCount",mushroomCount);
        }

        // Hespori
        if (msg.contains("you harvest the hespori"))
        {
            hesporiCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter","hesporiCount",hesporiCount);
        }
    }

    // === RECORD TREE “CHECK-HEALTH” CLICK ===
    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked ev)
    {
        String option = ev.getMenuOption().toLowerCase(Locale.ROOT);
        // we want both the tree “check-health” and herb “harvest”/“pick” clicks
        if (!option.equals("check-health") &&
                !option.equals("harvest") &&
                !option.equals("pick"))
        {
            return;
        }

        // strip any color tags off the object name
        String target = Text.removeTags(ev.getMenuTarget()).toLowerCase(Locale.ROOT);

        // store whichever object you clicked so the next Farming XP drop (within 10s)
        // can be attributed correctly
        lastCheckTarget = target;
        lastCheckTime   = System.currentTimeMillis();
    }

    // === ONLY COUNT ON ACTUAL FARMING XP GAIN ===
    @Subscribe
    public void onStatChanged(StatChanged e)
    {
        if (e.getSkill() != Skill.FARMING)
        {
            return;
        }

        int xp = e.getXp();
        if (lastFarmingXp < 0)
        {
            // first time we see Farming XP, just record it
            lastFarmingXp = xp;
            return;
        }
        long delta = System.currentTimeMillis() - lastCheckTime;
        if (delta > 15_000L)
        {
            // too slow → ignore this tree click
            lastCheckTarget = null;
            lastFarmingXp   = xp;
            return;
        }
        if (xp <= lastFarmingXp || lastCheckTarget == null)
        {
            // either no xp gain or no pending tree click
            return;
        }

        // real Farming XP gain + a valid tree target → count!
        switch (lastCheckTarget)
        {
            case "cactus":
                cactusCount++;
                configManager.setConfiguration("petRollCounter","cactusCount",cactusCount);
                break;
            case "belladonna":
                belladonnaCount++;
                configManager.setConfiguration("petRollCounter","belladonnaCount",belladonnaCount);
                break;
            case "papaya tree":
                papayaCount++;
                configManager.setConfiguration("petRollCounter","papayaCount",papayaCount);
                break;
            case "apple tree":
                appleCount++;
                configManager.setConfiguration("petRollCounter","appleCount",appleCount);
                break;
            case "teak tree":
                teakCount++;
                configManager.setConfiguration("petRollCounter","teakCount",teakCount);
                break;
            case "mahogany tree":
                mahoganyCount++;
                configManager.setConfiguration("petRollCounter","mahoganyCount",mahoganyCount);
                break;
            case "redwood tree":
                redwoodCount++;
                configManager.setConfiguration("petRollCounter","redwoodCount",redwoodCount);
                break;
            case "calquat tree":
                calquatCount++;
                configManager.setConfiguration("petRollCounter","calquatCount",calquatCount);
                break;
            case "willow tree":
                willowCount++;
                configManager.setConfiguration("petRollCounter","willowCount",willowCount);
                break;
            case "magic tree":
                magicCount++;
                configManager.setConfiguration("petRollCounter","magicCount",magicCount);
                break;
            case "yew tree":
                yewCount++;
                configManager.setConfiguration("petRollCounter","yewCount",yewCount);
                break;
            case "celastrus tree":
                celastrusCount++;
                configManager.setConfiguration("petRollCounter","celastrusCount",celastrusCount);
                break;
            case "hespori":
                hesporiCount++;
                configManager.setConfiguration("petRollCounter","hesporiCount",hesporiCount);
            default:
                // not a tracked tree
                break;
        }

        // update session and clear state
        sessionCount++;
        lastFarmingXp   = xp;
        lastCheckTarget = null;
    }

    public int getSessionCount() { return sessionCount; }
    public Instant getSessionStart() { return sessionStart; }

    @Provides
    public PetRollCounterConfig provideConfig(ConfigManager mgr)
    {
        return mgr.getConfig(PetRollCounterConfig.class);
    }
}
