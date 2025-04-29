package com.chatcounter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("petRollCounter")
public interface PetRollCounterConfig extends Config
{
    @ConfigItem(
            keyName = "seaweedCount",
            name = "Seaweed rolls",
            description = "Number of seaweed patch pet-roll attempts",
            position = 0
    )
    default int seaweedCount() { return 0; }

    @ConfigItem(
            keyName = "cactusCount",
            name = "Cactus rolls",
            description = "Number of cactus patch pet-roll attempts",
            position = 1
    )
    default int cactusCount() { return 0; }

    @ConfigItem(
            keyName = "belladonnaCount",
            name = "Belladonna rolls",
            description = "Number of cave nightshade patch pet-roll attempts",
            position = 2
    )
    default int belladonnaCount() { return 0; }

    @ConfigItem(
            keyName = "mushroomCount",
            name = "Mushroom rolls",
            description = "Number of bittercap mushroom patch pet-roll attempts",
            position = 3
    )
    default int mushroomCount() { return 0; }

    @ConfigItem(
            keyName = "papayaCount",
            name = "Papaya rolls",
            description = "Number of papaya tree check pet-roll attempts",
            position = 4
    )
    default int papayaCount() { return 0; }

    @ConfigItem(
            keyName = "appleCount",
            name = "Apple rolls",
            description = "Number of apple tree check pet-roll attempts",
            position = 5
    )
    default int appleCount() { return 0; }

    @ConfigItem(
            keyName = "showOverlay",
            name = "Show overlay panel",
            description = "Toggle the pet-roll counter panel on or off",
            position = 6
    )
    default boolean showOverlay() { return true; }
}
