package com.chatcounter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("petRollCounter")
public interface PetRollCounterConfig extends Config
{
    @ConfigItem(keyName = "seaweedCount",    name = "Seaweed rolls",    description = "Giant seaweed pet-roll attempts", position = 0)
    default int seaweedCount()    { return 0; }

    @ConfigItem(keyName = "mushroomCount",   name = "Mushroom rolls",   description = "Bittercap mushroom pet-roll attempts", position = 1)
    default int mushroomCount()   { return 0; }

    @ConfigItem(keyName = "cactusCount",     name = "Cactus rolls",     description = "Cactus pet-roll attempts", position = 2)
    default int cactusCount()     { return 0; }

    @ConfigItem(keyName = "belladonnaCount", name = "Belladonna rolls", description = "Cave nightshade pet-roll attempts", position = 3)
    default int belladonnaCount() { return 0; }

    @ConfigItem(keyName = "papayaCount",     name = "Papaya rolls",     description = "Papaya tree pet-roll attempts", position = 4)
    default int papayaCount()     { return 0; }

    @ConfigItem(keyName = "appleCount",      name = "Apple rolls",      description = "Apple tree pet-roll attempts", position = 5)
    default int appleCount()      { return 0; }

    @ConfigItem(keyName = "teakCount",       name = "Teak rolls",       description = "Teak tree pet-roll attempts", position = 6)
    default int teakCount()       { return 0; }

    @ConfigItem(keyName = "mahoganyCount",   name = "Mahogany rolls",   description = "Mahogany tree pet-roll attempts", position = 7)
    default int mahoganyCount()   { return 0; }

    @ConfigItem(keyName = "redwoodCount",    name = "Redwood rolls",    description = "Redwood tree pet-roll attempts", position = 8)
    default int redwoodCount()    { return 0; }

    @ConfigItem(keyName = "calquatCount",    name = "Calquat rolls",    description = "Calquat tree pet-roll attempts", position = 9)
    default int calquatCount()    { return 0; }

    @ConfigItem(keyName = "willowCount",     name = "Willow rolls",     description = "Willow tree pet-roll attempts", position = 10)
    default int willowCount()     { return 0; }

    @ConfigItem(keyName = "magicCount",      name = "Magic rolls",      description = "Magic tree pet-roll attempts", position = 11)
    default int magicCount()      { return 0; }

    @ConfigItem(keyName = "yewCount",        name = "Yew rolls",        description = "Yew tree pet-roll attempts", position = 12)
    default int yewCount()        { return 0; }

    @ConfigItem(keyName = "celastrusCount",  name = "Celastrus rolls",  description = "Celastrus tree pet-roll attempts", position = 13)
    default int celastrusCount()  { return 0; }

    @ConfigItem(keyName = "hesporiCount",    name = "Hespori rolls",    description = "Hespori pet-roll attempts", position = 14)
    default int hesporiCount()    { return 0; }

    @ConfigItem(keyName = "showPanel",       name = "Show Panel",       description = "Toggle the docked Pet Roll Counter panel", position = 15)
    default boolean showPanel()    { return true; }
}
