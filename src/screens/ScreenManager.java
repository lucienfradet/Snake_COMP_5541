package screens;

import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public final class ScreenManager {

    public static final String START_MENU = "start_menu";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String MAIN_MENU = "main_menu";
    public static final String GAME = "game";
    public static final String PAUSE = "pause";
    public static final String GAME_OVER = "game_over";

    private static final ScreenManager INSTANCE = new ScreenManager();

    private final Map<String, Screen> screens = new HashMap<>();
    private final Map<String, JPanel> panels = new HashMap<>();
    private final Map<String, ScreenFactory> factories = new LinkedHashMap<>();
    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);
    private JFrame frame;

    private ScreenManager() {}

    public static ScreenManager getInstance() {
        return INSTANCE;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
        frame.setContentPane(root);
    }

    public void addScreen(String name, ScreenFactory factory) {
        factories.put(name, factory);
    }

    public void showScreen(String name) {
        Runnable action = () -> {
            ensureScreen(name);

            Screen screen = screens.get(name);
            JPanel panel = panels.get(name);
            if (screen == null || panel == null) {
                throw new IllegalArgumentException("Unknown screen: " + name);
            }

            screen.onShow();
            layout.show(root, name);
            root.revalidate();
            root.repaint();
            panel.requestFocusInWindow();

            if (frame != null) {
                frame.setTitle(titleFor(name));
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            action.run();
        } else {
            SwingUtilities.invokeLater(action);
        }
    }

    private void ensureScreen(String name) {
        if (screens.containsKey(name)) {
            return;
        }

        ScreenFactory factory = factories.get(name);
        if (factory == null) {
            throw new IllegalArgumentException("No factory registered for screen: " + name);
        }

        Screen screen = factory.create();
        JPanel panel = (JPanel) screen;
        screens.put(name, screen);
        panels.put(name, panel);
        root.add(panel, name);
    }

    public void refreshScreen(String name) {
        Runnable action = () -> {
            JPanel oldPanel = panels.remove(name);
            Screen oldScreen = screens.remove(name);
            if (oldPanel != null) {
                root.remove(oldPanel);
            }

            ensureScreen(name);
            if (oldScreen != null) {
                root.revalidate();
                root.repaint();
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            action.run();
        } else {
            SwingUtilities.invokeLater(action);
        }
    }

    private String titleFor(String name) {
        return switch (name) {
            case START_MENU -> "Start Menu";
            case LOGIN -> "Login";
            case REGISTER -> "Register";
            case MAIN_MENU -> "Main Menu";
            case GAME -> "Snake Game";
            case PAUSE -> "Pause";
            case GAME_OVER -> "Game Over";
            default -> "Snake Game";
        };
    }

    @FunctionalInterface
    public interface ScreenFactory {
        Screen create();
    }
}
