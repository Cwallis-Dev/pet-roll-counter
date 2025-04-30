package com.chatcounter.ui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.PluginPanel;

public class PetRollCounterPanel extends PluginPanel
{
    private final JLabel title = new JLabel("Pet Roll Counter");
    private final JLabel overallLabel = new JLabel("Overall: —");
    // (you can add more JLabels here for individual accumulative rates)

    public PetRollCounterPanel()
    {
        // Use vertical layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title styling
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(16f));
        add(title);

        // small gap
        add(Box.createVerticalStrut(8));

        // Overall chance line
        overallLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(overallLabel);

        // If you want more lines:
        // JLabel seaweedAccum = new JLabel("Seaweed: —");
        // add(seaweedAccum);
    }

    /**
     * Call this to update the overall chance display.
     */
    public void updateOverall(double pct)
    {
        overallLabel.setText(String.format("Overall: %.2f%%", pct));
        revalidate();
        repaint();
    }

    // You can add methods like updateSeaweed(double pct), etc.
}
