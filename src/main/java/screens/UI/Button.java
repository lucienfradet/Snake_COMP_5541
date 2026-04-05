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
    private final Border whiteBorder;
    private final Border dimmedBorder;
    private final Border pressedBorder;
    private final Border selectedBorder;

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

        whiteBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.WHITE, BORDER_WIDTH),
            BorderFactory.createEmptyBorder(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL)
        );
        dimmedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.DIMMED_WHITE, BORDER_WIDTH),
            BorderFactory.createEmptyBorder(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL)
        );
        pressedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.DIMMED_WHITE, BORDER_WIDTH),
            BorderFactory.createEmptyBorder(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL)
        );
        selectedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.GREEN, BORDER_WIDTH),
            BorderFactory.createEmptyBorder(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL)
        );

        setBorder(whiteBorder);
        Dimension preferredSize = super.getPreferredSize();
        Dimension buttonSize = new Dimension(preferredSize.width, Math.max(preferredSize.height, buttonHeight));
        setPreferredSize(buttonSize);
        setMaximumSize(buttonSize);
        setMinimumSize(buttonSize);

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

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(clampToStyledSize(preferredSize));
    }

    @Override
    public void setMaximumSize(Dimension maximumSize) {
        super.setMaximumSize(clampToStyledSize(maximumSize));
    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(clampToStyledSize(minimumSize));
    }

    private Dimension clampToStyledSize(Dimension requestedSize) {
        Border previousBorder = getBorder();
        super.setBorder(whiteBorder);
        Dimension styledSize = super.getPreferredSize();
        super.setBorder(previousBorder);

        int width = styledSize.width;
        int height = styledSize.height;
        if (requestedSize != null) {
            width = Math.max(width, requestedSize.width);
            height = Math.max(height, requestedSize.height);
        }
        return new Dimension(width, height);
    }
}
