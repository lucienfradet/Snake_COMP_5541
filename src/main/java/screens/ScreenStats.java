package screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenStats extends JPanel implements Screen {

    private static final String[] COLUMN_NAMES = { "ID", "Diff", "Maze", "Score", "Time", "Moves" };

    private final DefaultTableModel statsTableModel;
    private final JTable statsTable;
    private final JLabel currentUserLabel;

    public ScreenStats() {

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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        statsTable.getColumnModel().getColumn(3).setPreferredWidth(68);
        statsTable.getColumnModel().getColumn(4).setPreferredWidth(74);
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

        JLabel loggedInAs = new JLabel("Logged in as");
        loggedInAs.setFont(FontPalette.TEXT);
        loggedInAs.setForeground(ColorPalette.GREEN);
        loggedInAs.setAlignmentX(CENTER_ALIGNMENT);
        loggedInAs.setAlignmentY(CENTER_ALIGNMENT);

        currentUserLabel = new JLabel("Barb Tarbox");
        currentUserLabel.setFont(FontPalette.TEXT);
        currentUserLabel.setForeground(ColorPalette.WHITE);
        currentUserLabel.setAlignmentX(CENTER_ALIGNMENT);
        currentUserLabel.setAlignmentY(CENTER_ALIGNMENT);

        JPanel loginInfoPanel = new JPanel();
        loginInfoPanel.setLayout(new BoxLayout(loginInfoPanel, BoxLayout.Y_AXIS));
        loginInfoPanel.setBackground(ColorPalette.BLACK);
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);
        loginInfoPanel.setAlignmentY(CENTER_ALIGNMENT);

        loginInfoPanel.add(loggedInAs);
        loginInfoPanel.add(currentUserLabel);
        bottomPanel.add(loginInfoPanel);

        add(topPanel);
        add(middlePanel);
        add(Box.createVerticalGlue());
        add(bottomPanel);

        setStatsRows(List.of(
            new GameStatRow(1, "med", 1, 10, "01:23", 137),
            new GameStatRow(2, "med", 1, 10, "01:23", 137),
            new GameStatRow(3, "med", 1, 10, "01:23", 137),
            new GameStatRow(4, "med", 1, 10, "01:23", 137),
            new GameStatRow(5, "med", 1, 10, "01:23", 137),
            new GameStatRow(6, "med", 1, 10, "01:23", 137),
            new GameStatRow(7, "med", 1, 10, "01:23", 137),
            new GameStatRow(8, "med", 1, 10, "01:23", 137),
            new GameStatRow(9, "med", 1, 10, "01:23", 137)
        ));
    }

    public void setCurrentUser(String username) {
        currentUserLabel.setText(username);
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
