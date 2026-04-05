package screens.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.HierarchyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.Border;

public class Button extends JButton {

    public Button(String name) {
        
        super(name);

        int buttonHeight = 40;

        setFont(FontPalette.TEXT);
        setFocusPainted(false);
        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(true);
        setRolloverEnabled(true);
        setBackground(ColorPalette.BLACK);

        Border whiteBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.WHITE, 3),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );
        Border dimmedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.DIMMED_WHITE, 3),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );
        Border pressedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.DIMMED_WHITE, 3),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        Dimension preferredSize = super.getPreferredSize();
        Dimension buttonSize = new Dimension(preferredSize.width, buttonHeight);
        setPreferredSize(buttonSize);
        setMaximumSize(buttonSize);

        Runnable applyStyle = () -> {
            ButtonModel model = getModel();
            Color parentBackground = getParent() != null ? getParent().getBackground() : ColorPalette.BLACK;
            boolean selected = Boolean.TRUE.equals(getClientProperty("selected"));
            Border selectedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorPalette.GREEN, 3),
                BorderFactory.createEmptyBorder(5, 20, 5, 20)
            );

            if (!model.isEnabled()) {
                setForeground(ColorPalette.DIMMED_WHITE);
                setBackground(parentBackground);
                setBorder(dimmedBorder);
                setBorderPainted(true);
                setContentAreaFilled(true);
            } else if (selected) {
                setForeground(ColorPalette.GREEN);
                setBackground(model.isRollover() ? ColorPalette.DIMMED_WHITE : parentBackground);
                setBorder(selectedBorder);
                setBorderPainted(true);
                setContentAreaFilled(true);
            } else if (model.isPressed()) {
                setForeground(ColorPalette.BLACK);
                setBackground(ColorPalette.DIMMED_WHITE);
                setBorder(pressedBorder);
                setBorderPainted(true);
                setContentAreaFilled(true);
            } else if (model.isRollover()) {
                setForeground(ColorPalette.WHITE);
                setBackground(ColorPalette.DIMMED_WHITE);
                setBorder(whiteBorder);
                setBorderPainted(true);
                setContentAreaFilled(true);
            } else {
                setForeground(ColorPalette.WHITE);
                setBackground(parentBackground);
                setBorder(whiteBorder);
                setBorderPainted(true);
                setContentAreaFilled(true);
            }
        };

        getModel().addChangeListener(e -> applyStyle.run());
        addPropertyChangeListener("selected", e -> applyStyle.run());
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0) {
                applyStyle.run();
            }
        });

        applyStyle.run();
    }
}
