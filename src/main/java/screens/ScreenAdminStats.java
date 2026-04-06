package screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.Main;
import db.*;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenAdminStats extends JPanel implements Screen {

    private static final String[] COLUMN_NAMES = { "ID", "Diff", "Maze", "Score", "Time", "Moves" };
    private final DefaultTableModel statsTableModel;
    private final JTable statsTable;

    private final UserDataSorter sorter = new UserDataSorter();
    private UserData[] stats;

    public ScreenAdminStats() {

        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ColorPalette.BLACK);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(ColorPalette.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(ColorPalette.BLACK);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JButton back = new Button("Back");
        back.setPreferredSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));
        topPanel.add(back);

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
        statsTable.setFont(FontPalette.TEXT);
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
        statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        statsTable.getColumnModel().getColumn(0).setPreferredWidth(38);
        statsTable.getColumnModel().getColumn(1).setPreferredWidth(56);
        statsTable.getColumnModel().getColumn(2).setPreferredWidth(66);
        statsTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        statsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
        statsTable.getColumnModel().getColumn(5).setPreferredWidth(86);

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
                    default      -> "gameId";
                };
                sorter.sortBy(stats, sortKey);
                refreshStatsTable(stats);
            }
        });

        header.setFont(FontPalette.TEXT);
        header.setForeground(ColorPalette.GREEN);
        header.setBackground(ColorPalette.WHITE);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setPreferredSize(new Dimension(390, 40));
        header.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

        JScrollPane tableScrollPane = new JScrollPane(statsTable);
        tableScrollPane.setAlignmentX(CENTER_ALIGNMENT);
        tableScrollPane.setPreferredSize(new Dimension(410, 220));
        tableScrollPane.setMaximumSize(new Dimension(410, 220));
        tableScrollPane.setMinimumSize(new Dimension(410, 220));
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
        tablePanel.setPreferredSize(new Dimension(410, 300));
        tablePanel.setMaximumSize(new Dimension(410, 300));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        middlePanel.add(Box.createVerticalStrut(2));
        middlePanel.add(pastGames);
        middlePanel.add(Box.createVerticalStrut(8));
        middlePanel.add(tablePanel);

        JLabel filterLabel = new JLabel("Filter by username:");
        filterLabel.setFont(FontPalette.TEXT);
        filterLabel.setForeground(ColorPalette.WHITE);

        JTextField filterField = new JTextField();
        filterField.setFont(FontPalette.TEXT);
        filterField.setForeground(ColorPalette.RED);
        filterField.setBackground(ColorPalette.WHITE);
        filterField.setCaretColor(ColorPalette.RED);
        filterField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorPalette.WHITE, 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        filterField.setMaximumSize(new Dimension(200, 32));
        filterField.setPreferredSize(new Dimension(200, 32));

        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { onFilterChanged(filterField.getText()); }
            @Override public void removeUpdate(DocumentEvent e)  { onFilterChanged(filterField.getText()); }
            @Override public void changedUpdate(DocumentEvent e) { onFilterChanged(filterField.getText()); }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(6, 20, 10, 20));
        bottomPanel.add(filterLabel);
        bottomPanel.add(Box.createHorizontalStrut(10));
        bottomPanel.add(filterField);
        bottomPanel.add(Box.createHorizontalGlue());

        this.add(topPanel);
        this.add(middlePanel);
        this.add(bottomPanel);

        try {
            stats = UserDB.getUserData(0, Main.loginUser.isAdmin()); // load all users, not just the logged-in one
            refreshStatsTable(stats);
        } catch (Exception e1) {
            System.err.println(e1.getMessage());
        }
    }

    private void onFilterChanged(String searchPattern) {
        if (searchPattern == null || searchPattern.isBlank()) {
            refreshStatsTable(stats);
        } else {
            UserData[] filtered = UserDataSorter.searchUsername(stats, searchPattern);
            refreshStatsTable(filtered.length > 0 ? filtered : stats);
        }
    }

    private void refreshStatsTable(UserData[] data) {
        clearStatsRows();
        for (UserData u : data) {
            addStatsRow(new GameStatRow(
                u.getGameId(),
                u.getDifficulty().toString().substring(0, 1),
                u.getMaze(),
                u.getScore(),
                formatTime(u.getGameTime()),
                u.getTotalMoveCount()
            ));
        }
    }

    private String formatTime(long ms) {
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
    public void onShow() {}

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
