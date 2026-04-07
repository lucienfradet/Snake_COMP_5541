package screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.Main;
import db.*;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenStats extends JPanel implements Screen {

    private static final String[] COLUMN_NAMES = { "ID", "Lvl", "Maze", "Score", "Time", "Moves" };
    private static final Font SMALL_FONT = FontPalette.TEXT.deriveFont(13f);
    private final DefaultTableModel statsTableModel;
    private final JTable statsTable;
    private final JPanel loginInfoPanel;

    private final UserDataSorter sorter = new UserDataSorter();
    private UserData[] stats; // set to NULL when exiting screen to help garbage collector??????

    public ScreenStats() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(ColorPalette.BLACK);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(ColorPalette.BLACK); 

        JButton back = new Button("Back");
        back.setPreferredSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));
        
        topPanel.add(back);
        topPanel.add(Box.createHorizontalGlue());

        JLabel pastGames = new JLabel("Past Games");
        pastGames.setFont(FontPalette.TITLE);
        pastGames.setForeground(ColorPalette.WHITE);
        pastGames.setAlignmentX(CENTER_ALIGNMENT);

        statsTableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        statsTable = new JTable(statsTableModel);
        statsTable.setFont(SMALL_FONT);
        statsTable.setForeground(ColorPalette.RED);
        statsTable.setBackground(ColorPalette.WHITE);
        statsTable.setSelectionBackground(ColorPalette.WHITE);
        statsTable.setSelectionForeground(ColorPalette.RED);
        statsTable.setGridColor(ColorPalette.WHITE);
        statsTable.setRowHeight(28);
        statsTable.setFillsViewportHeight(true);
        statsTable.setShowGrid(false);
        statsTable.setIntercellSpacing(new Dimension(0, 0));
        statsTable.setFocusable(false);
        statsTable.setRowSelectionAllowed(false);
        statsTable.setBorder(null);
        statsTable.getColumnModel().getColumn(0).setPreferredWidth(38);
        statsTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        statsTable.getColumnModel().getColumn(2).setPreferredWidth(69);
        statsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        statsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
        statsTable.getColumnModel().getColumn(5).setPreferredWidth(80);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setForeground(ColorPalette.RED);
        cellRenderer.setBackground(ColorPalette.WHITE);
        cellRenderer.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        for (int column = 0; column < statsTable.getColumnModel().getColumnCount(); column++) {
            statsTable.getColumnModel().getColumn(column).setCellRenderer(cellRenderer);
        }

        JTableHeader header = statsTable.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = statsTable.columnAtPoint(e.getPoint());
                String attribute = COLUMN_NAMES[column];

                String sortKey = switch (attribute) {
                    case "ID"    -> "gameId";
                    case "Diff"  -> "difficulty";
                    case "Maze"  -> "maze";
                    case "Score" -> "score";
                    case "Time"  -> "time";
                    case "Moves" -> "moves";
                    default -> "gameId";
                };
                sorter.sortBy(stats, sortKey);
                refreshStatsTable();
            }            
        });

        header.setFont(FontPalette.TEXT);
        header.setForeground(ColorPalette.GREEN);
        header.setBackground(ColorPalette.WHITE);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setPreferredSize(new Dimension(410, 40));
        header.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

        JScrollPane tableScrollPane = new JScrollPane(statsTable);
        tableScrollPane.setAlignmentX(CENTER_ALIGNMENT);
        tableScrollPane.setPreferredSize(new Dimension(450, 180));
        tableScrollPane.setMaximumSize(new Dimension(450, 200));
        tableScrollPane.setMinimumSize(new Dimension(450, 200));
        tableScrollPane.setBackground(ColorPalette.WHITE);
        tableScrollPane.getViewport().setBackground(ColorPalette.WHITE);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE, 6, true));
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar = tableScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setBackground(ColorPalette.WHITE);
        verticalScrollBar.setForeground(ColorPalette.GREEN);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setAlignmentX(CENTER_ALIGNMENT);
        tablePanel.setBackground(ColorPalette.BLACK);
        tablePanel.setPreferredSize(new Dimension(450, 250));
        tablePanel.setMaximumSize(new Dimension(450, 250));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        middlePanel.add(Box.createVerticalGlue());
        middlePanel.add(pastGames);
        middlePanel.add(tablePanel);
        middlePanel.add(Box.createVerticalGlue()); // pushes content up

        loginInfoPanel = ScreenManager.displayUserInfo(Main.loginUser.getUsername());
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.add(loginInfoPanel);
        bottomPanel.add(Box.createHorizontalGlue());

        this.add(topPanel);
        this.add(middlePanel);
        this.add(Box.createVerticalGlue()); // add before bottomPanel
        this.add(bottomPanel);

        try {
            stats = UserDB.getUserData(Main.loginUser.getId(), false);
            refreshStatsTable();
        } catch (Exception e1) {
            System.err.println(e1.getMessage());
        }
    }

    private void refreshStatsTable() {
        clearStatsRows();
        for (UserData u : stats) {
            addStatsRow(new GameStatRow(
                u.getGameId(),
                u.getDifficulty().toString(),
                u.getMaze(),
                u.getScore(),
                formatTime(u.getGameTime()),
                u.getTotalMoveCount()
            ));
        }
    }

    private static String formatTime(long ms) {
        long seconds = ms / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void clearStatsRows() {
        statsTableModel.setRowCount(0);
    }

    public void addStatsRow(GameStatRow row) {
        statsTableModel.addRow(row.toTableRow());
    }

    public void setStatsRows(List<GameStatRow> rows) {
        clearStatsRows();
        for (GameStatRow row : rows) {
            addStatsRow(row);
        }
    }

    @Override
    public void onShow() {
        ScreenManager.refreshUserInfoPanel(loginInfoPanel);
    }

    public static final class GameStatRow {
        private final int id;
        private final String difficulty;
        private final int maze;
        private final int score;
        private final String time;
        private final int moves;

        public GameStatRow(int id, String difficulty, int maze, int score, String time, int moves) {
            this.id = id;
            this.difficulty = difficulty;
            this.maze = maze;
            this.score = score;
            this.time = time;
            this.moves = moves;
        }

        public Object[] toTableRow() {
            return new Object[] { id, difficulty, maze, score, time, moves };
        }
    }

}
