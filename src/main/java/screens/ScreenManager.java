package screens;

import db.UserData;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public final class ScreenManager {

    public static final String START_MENU = "start_menu";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String MAIN_MENU = "main_menu";
    public static final String MAP_SELECT = "map_select";
    public static final String ACCOUNT_MANAGER = "account_manager";
    public static final String UPDATE_ACCOUNT = "update_account";
    public static final String DELETE_ACCOUNT = "delete_account";
    public static final String STATS = "stats";
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

    public static JPanel displayUserInfo(){
        JLabel loggedInAs = new JLabel("Logged in as");
        loggedInAs.setFont(FontPalette.TEXT);
        loggedInAs.setForeground(ColorPalette.GREEN);
        loggedInAs.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel currentUser = new JLabel("Bard Tarbox");
        currentUser.setFont(FontPalette.TEXT);
        currentUser.setForeground(ColorPalette.WHITE);
        currentUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel loginInfoPanel = new JPanel();
        loginInfoPanel.setLayout(new BoxLayout(loginInfoPanel, BoxLayout.Y_AXIS));
        loginInfoPanel.setBackground(ColorPalette.BLACK);
        loginInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loginInfoPanel.add(loggedInAs);
        loginInfoPanel.add(currentUser);
        loggedInAs.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return loginInfoPanel;
    }

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

    public Screen getScreen(String name) {
        return screens.get(name);
    }

    private String titleFor(String name) {
        return switch (name) {
            case START_MENU -> "Start Menu";
            case LOGIN -> "Login";
            case REGISTER -> "Register";
            case MAIN_MENU -> "Main Menu";
            case MAP_SELECT -> "Map Select";
            case ACCOUNT_MANAGER -> "Manage Account";
            case UPDATE_ACCOUNT -> "Update Account";
            case DELETE_ACCOUNT -> "Delete Account";
            case STATS -> "Stats";
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
