package TileLayout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class TileGUI extends JFrame {
    protected static final int size = 16;
    protected JButton[][] buttons = new JButton[4][4];
    protected JPanel gamePanel;
    protected JPanel buttonPanel = new JPanel();
    protected JButton newGame = new JButton("New game");
    protected String [] winningString = new String[size - 1];

    public ArrayList<Integer> orderedNumbersList = new ArrayList<>(size);
    public int emptyCell;

    public TileGUI() {
        fillWinningArray();

        buttonPanel.add(newGame);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(gamePanel);
                startNewGame();
            }
        });

        add(buttonPanel, BorderLayout.NORTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        startNewGame();
    }

    //-----------------

    private void startNewGame() {
        orderedNumbersList.clear();
        gamePanel = new JPanel();

        for (int i = 0; i < size; i++) {
            orderedNumbersList.add(i, i);
        }

        Collections.shuffle(orderedNumbersList);

        for (int i = 0; i < size; i++) {
            int col = i % 4;
            int row = i / 4;
            buttons[row][col] = new JButton(String.valueOf(orderedNumbersList.get(i)));

            if (orderedNumbersList.get(i) == 0) {
                emptyCell = i;
                buttons[row][col].setVisible(false);
            }

            buttons[row][col].setForeground(Color.black);
            buttons[row][col].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton buttonPressed = (JButton)e.getSource();
                    int indexOfButton = indexOfButtonPressed(buttonPressed.getText());
                    int rowForButtonPressed = indexOfButton / 4;
                    int colForButtonPressed = indexOfButton % 4;
                    int rowForEmpty = emptyCell / 4;
                    int colForEmpty = emptyCell % 4;
                    int rowDifference = rowForEmpty - rowForButtonPressed;
                    int colDifference = colForEmpty - colForButtonPressed;
                    boolean sameRow = (rowForButtonPressed == rowForEmpty);
                    boolean sameCol = (colForButtonPressed == colForEmpty);
                    boolean notDiagonal = (sameRow || sameCol);

                    if (notDiagonal) {
                        int difference = Math.abs(colDifference);

                        if (colDifference < 0 & sameRow) {
                            for (int i = 0; i < difference; i++) {
                                buttons[rowForEmpty][colForEmpty + i].setText(buttons[rowForEmpty][colForEmpty + (i + 1)].getText());
                            }
                        } else if (colDifference > 0 & sameRow) {
                            for (int i = 0; i < difference; i++) {
                                buttons[rowForEmpty][colForEmpty - i].setText(buttons[rowForEmpty][colForEmpty - (i + 1)].getText());
                            }
                        }

                        difference = Math.abs(rowDifference);

                        if (rowDifference < 0 & sameCol) {
                            for (int i = 0; i < difference; i++) {
                                buttons[rowForEmpty + i][colForEmpty].setText(buttons[rowForEmpty + (i + 1)][colForEmpty].getText());
                            }
                        } else if (rowDifference > 0 & sameCol) {
                            for (int i = 0; i < difference; i++) {
                                buttons[rowForEmpty - i][colForEmpty].setText(buttons[rowForEmpty - (i + 1)][colForEmpty].getText());
                            }
                        }

                        buttons[rowForEmpty][colForEmpty].setVisible(true);
                        buttons[rowForButtonPressed][colForButtonPressed].setText(Integer.toString(0));
                        buttons[rowForButtonPressed][colForButtonPressed].setVisible(false);
                        emptyCell = getIndexOfBoard(rowForButtonPressed, colForButtonPressed);

                    }
                        if (isFinished()){
                            deactivateAllButtons();
                            JOptionPane.showMessageDialog(null, "Du vann, grattis!");
                        }
                }
            });

            gamePanel.setLayout(new GridLayout(4, 4));
            gamePanel.add(buttons[row][col]);
        }

        add(gamePanel, BorderLayout.CENTER);
        pack();
        setSize(400, 400);
        repaint();
    }

    public Integer indexOfButtonPressed(String cell) {
        for (int r = 0; r < buttons.length; r++) {
            for (int c = 0; c < buttons[r].length; c++) {
                if (buttons[r][c].getText().equals(cell)) {
                    return (getIndexOfBoard(r, c));
                }
            }
        }
        return null;
    }

    public int getIndexOfBoard(int i, int j) {
        return ((i * 4) + j);
    }

    public void fillWinningArray() {
        for (int i = 1; i < size; i++){
            winningString[i - 1] = Integer.toString(i);
        }
    }

    private boolean isFinished() {
        for (int i = winningString.length - 1; i >= 0; i--) {
            int row = i / 4;
            int col = i % 4;
            String number = buttons[row][col].getText();
            if (!number.equals(winningString[i])) {
                return false;

            }
        }
        return true;
    }

    private void deactivateAllButtons() {
        for (int r = 0; r < buttons.length; r++) {
            for (int c = 0; c < buttons[r].length; c++) {
                buttons[r][c].setEnabled(false);
            }
        }
    }

}