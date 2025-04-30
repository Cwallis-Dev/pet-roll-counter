package com.chatcounter.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import net.runelite.client.ui.PluginPanel;

public class PetRollCounterPanel extends PluginPanel
{
    private final JLabel title           = new JLabel("Pet Roll Counter");
    private final JLabel seaweedValue    = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel mushroomValue   = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel cactusValue     = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel belladonnaValue = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel papayaValue     = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel appleValue      = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel teakValue       = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel mahoganyValue   = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel redwoodValue    = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel calquatValue    = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel willowValue     = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel magicValue      = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel yewValue        = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel celastrusValue  = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel hesporiValue    = new JLabel("—", SwingConstants.RIGHT);
    private final JLabel overallValue    = new JLabel("—", SwingConstants.RIGHT);

    public PetRollCounterPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Title
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);
        add(Box.createVerticalStrut(8));
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(Box.createVerticalStrut(8));

        // Rows
        add(createRow("Seaweed:",    seaweedValue));
        add(createRow("Mushroom:",   mushroomValue));
        add(createRow("Cactus:",     cactusValue));
        add(createRow("Belladonna:", belladonnaValue));
        add(createRow("Papaya:",     papayaValue));
        add(createRow("Apple:",      appleValue));
        add(createRow("Teak:",       teakValue));
        add(createRow("Mahogany:",   mahoganyValue));
        add(createRow("Redwood:",    redwoodValue));
        add(createRow("Calquat:",    calquatValue));
        add(createRow("Willow:",     willowValue));
        add(createRow("Magic:",      magicValue));
        add(createRow("Yew:",        yewValue));
        add(createRow("Celastrus:",  celastrusValue));
        add(createRow("Hespori:",    hesporiValue));
        add(Box.createVerticalStrut(8));
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(Box.createVerticalStrut(8));
        add(createRow("Overall:",    overallValue));

        // Consume extra space
        add(Box.createVerticalGlue());
    }

    private JPanel createRow(String name, JLabel value)
    {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, value.getPreferredSize().height));
        row.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
        row.add(nameLabel, BorderLayout.WEST);

        value.setFont(value.getFont().deriveFont(Font.PLAIN));
        row.add(value, BorderLayout.EAST);

        return row;
    }

    // Update methods called from your plugin
    public void updateSeaweed   (String txt) { seaweedValue.   setText(txt); }
    public void updateMushroom  (String txt) { mushroomValue.  setText(txt); }
    public void updateCactus    (String txt) { cactusValue.    setText(txt); }
    public void updateBelladonna(String txt) { belladonnaValue.setText(txt); }
    public void updatePapaya    (String txt) { papayaValue.    setText(txt); }
    public void updateApple     (String txt) { appleValue.     setText(txt); }
    public void updateTeak      (String txt) { teakValue.      setText(txt); }
    public void updateMahogany  (String txt) { mahoganyValue.  setText(txt); }
    public void updateRedwood   (String txt) { redwoodValue.   setText(txt); }
    public void updateCalquat   (String txt) { calquatValue.   setText(txt); }
    public void updateWillow    (String txt) { willowValue.    setText(txt); }
    public void updateMagic     (String txt) { magicValue.     setText(txt); }
    public void updateYew       (String txt) { yewValue.       setText(txt); }
    public void updateCelastrus (String txt) { celastrusValue. setText(txt); }
    public void updateHespori   (String txt) { hesporiValue.   setText(txt); }
    public void updateOverall   (String txt) { overallValue.   setText(txt); }
}
