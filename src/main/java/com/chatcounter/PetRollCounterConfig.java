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
            description = "Number of belladonna patch pet-roll attempts",
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
            keyName = "teakCount",
            name = "Teak rolls",
            description = "Number of teak tree check pet-roll attempts",
            position = 6
    )
    default int teakCount() { return 0; }

    @ConfigItem(
            keyName = "mahoganyCount",
            name = "Mahogany rolls",
            description = "Number of mahogany tree check pet-roll attempts",
            position = 7
    )
    default int mahoganyCount() { return 0; }

    @ConfigItem(
            keyName = "redwoodCount",
            name = "Redwood rolls",
            description = "Number of redwood tree check pet-roll attempts",
            position = 8
    )
    default int redwoodCount() { return 0; }

    @ConfigItem(
            keyName = "calquatCount",
            name = "Calquat rolls",
            description = "Number of calquat tree check pet-roll attempts",
            position = 9
    )
    default int calquatCount() { return 0; }

    @ConfigItem(
            keyName = "hesporiCount",
            name = "Hespori rolls",
            description = "Number of Hespori harvest pet-roll attempts",
            position = 10
    )
    default int hesporiCount() { return 0; }

    @ConfigItem(
            keyName = "willowCount",
            name = "Willow rolls",
            description = "Number of willow tree check pet-roll attempts",
            position = 11
    )
    default int willowCount() { return 0; }

    @ConfigItem(
            keyName = "magicCount",
            name = "Magic rolls",
            description = "Number of magic tree check pet-roll attempts",
            position = 12
    )
    default int magicCount() { return 0; }

    @ConfigItem(
            keyName = "yewCount",
            name = "Yew rolls",
            description = "Number of yew tree check pet-roll attempts",
            position = 13
    )
    default int yewCount() { return 0; }

    @ConfigItem(
            keyName = "celastrusCount",
            name = "Celastrus rolls",
            description = "Number of celastrus tree check pet-roll attempts",
            position = 14
    )
    default int celastrusCount() { return 0; }

    @ConfigItem(
            keyName = "showOverlay",
            name = "Show overlay panel",
            description = "Toggle the pet-roll counter panel on or off",
            position = 15
    )
    default boolean showOverlay() { return true; }
}
