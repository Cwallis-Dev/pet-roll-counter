package com.chatcounter;

import com.chatcounter.ui.PetRollCounterPanel;
import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.Locale;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.StatChanged;

import net.runelite.client.events.ConfigChanged;            // correct import
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

@PluginDescriptor(
        name = "TangleTracker",
        description = "Tracks Tangleroot pet-roll attempts in a panel",
        tags = {"pet","farming","counter"}
)
public class PetRollCounterPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private ClientToolbar clientToolbar;
    @Inject private ConfigManager configManager;
    @Inject private PetRollCounterConfig config;

    private PetRollCounterPanel panel;
    private NavigationButton navButton;

    // Chat-based flags
    private boolean seaweedReady = true, mushroomReady = true;

    // XP-based tree logic
    private String lastCheckTarget = null;
    private long   lastCheckTime   = 0L;
    private int    lastFarmingXp   = -1;

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
        // — Sidebar panel setup —
        panel = new PetRollCounterPanel();
        BufferedImage iconImg = ImageUtil.loadImageResource(getClass(), "/counter_icon.png");
        navButton = NavigationButton.builder()
                .tooltip("Pet Roll Counter")
                .icon(iconImg)
                .panel(panel)
                .priority(5)
                .build();
        clientToolbar.addNavigation(navButton);

        // — Initialize state —
        loadCounts();
        sessionCount   = 0;
        sessionStart   = Instant.now();
        lastFarmingXp  = -1;

        // ** Populate your panel immediately **
        refreshPanel();
    }

    @Override
    protected void shutDown() throws Exception
    {
        clientToolbar.removeNavigation(navButton);
    }

    private void loadCounts()
    {
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
    }

    private double accum(int count, int rate)
    {
        if (count <= 0) return 0;
        return 1.0 - Math.pow(1.0 - 1.0 / rate, count);
    }
    private String fmt(int count, double pct)
    {
        return String.format("%d (%.2f%%)", count, pct * 100.0);
    }
    private void refreshPanel()
    {
        if (!config.showPanel()) return;

        panel.updateSeaweed   (fmt(seaweedCount,    accum(seaweedCount,    getRateSeaweed())));
        panel.updateMushroom  (fmt(mushroomCount,   accum(mushroomCount,   getRateMushroom())));
        panel.updateCactus    (fmt(cactusCount,     accum(cactusCount,     getRateCactus())));
        panel.updateBelladonna(fmt(belladonnaCount, accum(belladonnaCount, getRateBelladonna())));
        panel.updatePapaya    (fmt(papayaCount,     accum(papayaCount,     getRatePapaya())));
        panel.updateApple     (fmt(appleCount,      accum(appleCount,      getRateApple())));
        panel.updateTeak      (fmt(teakCount,       accum(teakCount,       getRateTeak())));
        panel.updateMahogany  (fmt(mahoganyCount,   accum(mahoganyCount,   getRateMahogany())));
        panel.updateRedwood   (fmt(redwoodCount,    accum(redwoodCount,    getRateRedwood())));
        panel.updateCalquat   (fmt(calquatCount,    accum(calquatCount,    getRateCalquat())));
        panel.updateWillow    (fmt(willowCount,     accum(willowCount,     getRateWillow())));
        panel.updateMagic     (fmt(magicCount,      accum(magicCount,      getRateMagic())));
        panel.updateYew       (fmt(yewCount,        accum(yewCount,        getRateYew())));
        panel.updateCelastrus (fmt(celastrusCount,  accum(celastrusCount,  getRateCelastrus())));
        panel.updateHespori   (fmt(hesporiCount,    accum(hesporiCount,    getRateHespori())));
        panel.updateOverall   (String.format("%.2f%%", getCombinedChance() * 100.0));
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (event.getType() != ChatMessageType.GAMEMESSAGE &&
                event.getType() != ChatMessageType.SPAM) return;

        String msg = event.getMessage().toLowerCase(Locale.ROOT);

        if (msg.contains("you plant a seaweed"))
        {
            seaweedReady = true;
        }
        else if (msg.contains("you pick some giant seaweed") && seaweedReady)
        {
            seaweedReady = false;
            seaweedCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter","seaweedCount",seaweedCount);
            refreshPanel();
        }

        if (msg.contains("you plant a bittercap mushroom"))
        {
            mushroomReady = true;
        }
        else if (msg.contains("you pick a bittercap mushroom") && mushroomReady)
        {
            mushroomReady = false;
            mushroomCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter","mushroomCount",mushroomCount);
            refreshPanel();
        }

        if (msg.contains("you harvest the hespori"))
        {
            hesporiCount++; sessionCount++;
            configManager.setConfiguration("petRollCounter","hesporiCount",hesporiCount);
            refreshPanel();
        }
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked ev)
    {
        if (!ev.getMenuOption().equalsIgnoreCase("check-health"))
            return;

        lastCheckTarget = Text.removeTags(ev.getMenuTarget()).toLowerCase(Locale.ROOT);
        lastCheckTime   = System.currentTimeMillis();
    }

    @Subscribe
    public void onStatChanged(StatChanged e)
    {
        if (e.getSkill() != Skill.FARMING) return;

        int xp = e.getXp();
        if (lastFarmingXp < 0)
        {
            lastFarmingXp = xp;
            return;
        }

        if (xp <= lastFarmingXp || lastCheckTarget == null) return;
        if (System.currentTimeMillis() - lastCheckTime > 20_000L)
        {
            lastCheckTarget = null;
            lastFarmingXp   = xp;
            return;
        }

        switch (lastCheckTarget)
        {
            case "cactus":
                cactusCount++;
                configManager.setConfiguration("petRollCounter","cactusCount",cactusCount);
                break;
            case "cave nightshade":
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
                break;
        }

        sessionCount++;
        lastCheckTarget = null;
        lastFarmingXp   = xp;
        refreshPanel();
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("petRollCounter")) return;
        loadCounts();
        refreshPanel();
    }

    private int rate(int base)
    {
        int lvl = client.getRealSkillLevel(Skill.FARMING);
        if (lvl <= 0)
        {
            lvl = 99;
        }
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

    public double getCombinedChance()
    {
        double prod = 1.0;
        prod *= Math.pow(1 - 1.0/getRateSeaweed(),    seaweedCount);
        prod *= Math.pow(1 - 1.0/getRateMushroom(),   mushroomCount);
        prod *= Math.pow(1 - 1.0/getRateCactus(),     cactusCount);
        prod *= Math.pow(1 - 1.0/getRateBelladonna(), belladonnaCount);
        prod *= Math.pow(1 - 1.0/getRatePapaya(),     papayaCount);
        prod *= Math.pow(1 - 1.0/getRateApple(),      appleCount);
        prod *= Math.pow(1 - 1.0/getRateTeak(),       teakCount);
        prod *= Math.pow(1 - 1.0/getRateMahogany(),   mahoganyCount);
        prod *= Math.pow(1 - 1.0/getRateRedwood(),    redwoodCount);
        prod *= Math.pow(1 - 1.0/getRateCalquat(),    calquatCount);
        prod *= Math.pow(1 - 1.0/getRateWillow(),     willowCount);
        prod *= Math.pow(1 - 1.0/getRateMagic(),      magicCount);
        prod *= Math.pow(1 - 1.0/getRateYew(),        yewCount);
        prod *= Math.pow(1 - 1.0/getRateCelastrus(),  celastrusCount);
        prod *= Math.pow(1 - 1.0/getRateHespori(),    hesporiCount);
        return 1.0 - prod;
    }

    @Provides
    public PetRollCounterConfig provideConfig(ConfigManager mgr)
    {
        return mgr.getConfig(PetRollCounterConfig.class);
    }
}
