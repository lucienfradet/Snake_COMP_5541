package screens.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.HierarchyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Button extends JButton {
    private static final int BORDER_WIDTH = 3;
    private static final int PADDING_VERTICAL = 5;
    private static final int PADDING_HORIZONTAL = 10;

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
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setMargin(new Insets(0, 0, 0, 0));

        Border whiteBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.WHITE, BORDER_WIDTH),
            BorderFactory.createEmptyBorder(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL)
        );
        Border dimmedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.DIMMED_WHITE, BORDER_WIDTH),
            BorderFactory.createEmptyBorder(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL)
        );
        Border pressedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.DIMMED_WHITE, BORDER_WIDTH),
            BorderFactory.createEmptyBorder(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL)
        );
        Border selectedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.GREEN, BORDER_WIDTH),
            BorderFactory.createEmptyBorder(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL)
        );

        Dimension preferredSize = super.getPreferredSize();
        Dimension buttonSize = new Dimension(preferredSize.width, buttonHeight);
        setPreferredSize(buttonSize);
        setMaximumSize(buttonSize);

        Runnable applyStyle = () -> {
            ButtonModel model = getModel();
            Color parentBackground = getParent() != null ? getParent().getBackground() : ColorPalette.BLACK;
            boolean selected = Boolean.TRUE.equals(getClientProperty("selected"));

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
