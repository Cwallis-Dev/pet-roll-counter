package com.chatcounter;

import com.google.inject.Provides;
import java.time.Instant;
import java.util.Locale;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.StatChanged;
import com.chatcounter.ui.PetRollCounterPanel;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

@PluginDescriptor(
        name = "Tangletracker",
        description = "Tracks Tangleroot pet-roll attempts",
        tags = {"pet","farming","counter"}
)
public class PetRollCounterPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private ConfigManager configManager;
    @Inject private PetRollCounterConfig config;
    @Inject private OverlayManager overlayManager;
    @Inject private PetRollCounterOverlay overlay;


    // Chat-based flags
    private boolean seaweedReady = true, mushroomReady = true;

    //Tree Click
    private String lastCheckTarget = null;
    private long    lastCheckTime = 0L;

    //Baseline xp
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
    // === DROP-RATE FORMULAS ===

    private int rate(int base)
    {
        int lvl = client.getRealSkillLevel(Skill.FARMING);
        return Math.max(1, base - lvl * 25);
    }

    public int getRateSeaweed()    { return rate(7500); }
    public int getRateMushroom()   { return rate(7500); }
    public int getRateCactus()     { return rate(7000); }
    public int getRateBelladonna() { return rate(8000); }
    public int getRatePapaya()     { return rate(9000); }
    public int getRateApple()      { return rate(9000); }
    public int getRateTeak()       { return rate(5000); }
    public int getRateMahogany()   { return rate(5000); }
    public int getRateRedwood()    { return rate(5000); }
    public int getRateCalquat()    { return rate(6000); }
    public int getRateWillow()     { return rate(16059); }
    public int getRateMagic()      { return rate(9368); }
    public int getRateYew()        { return rate(11242); }
    public int getRateCelastrus()  { return rate(9000); }
    public int getRateHespori()    { return rate(7000); }

    /** Combined chance of at least one roll succeeding across all counts **/
    public double getCombinedChance()
    {
        double product = 1.0;
        product *= Math.pow(1 - 1.0/getRateSeaweed(),    seaweedCount);
        product *= Math.pow(1 - 1.0/getRateMushroom(),   mushroomCount);
        product *= Math.pow(1 - 1.0/getRateCactus(),     cactusCount);
        product *= Math.pow(1 - 1.0/getRateBelladonna(), belladonnaCount);
        product *= Math.pow(1 - 1.0/getRatePapaya(),     papayaCount);
        product *= Math.pow(1 - 1.0/getRateApple(),      appleCount);
        product *= Math.pow(1 - 1.0/getRateTeak(),       teakCount);
        product *= Math.pow(1 - 1.0/getRateMahogany(),   mahoganyCount);
        product *= Math.pow(1 - 1.0/getRateRedwood(),    redwoodCount);
        product *= Math.pow(1 - 1.0/getRateCalquat(),    calquatCount);
        product *= Math.pow(1 - 1.0/getRateWillow(),     willowCount);
        product *= Math.pow(1 - 1.0/getRateMagic(),      magicCount);
        product *= Math.pow(1 - 1.0/getRateYew(),        yewCount);
        product *= Math.pow(1 - 1.0/getRateCelastrus(),  celastrusCount);
        product *= Math.pow(1 - 1.0/getRateHespori(),    hesporiCount);
        return 1.0 - product;
    }
    public int getSessionCount() { return sessionCount; }
    public Instant getSessionStart() { return sessionStart; }

    @Provides
    public PetRollCounterConfig provideConfig(ConfigManager mgr)
    {
        return mgr.getConfig(PetRollCounterConfig.class);
    }
}
