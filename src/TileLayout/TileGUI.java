package TileLayout;

import TileModel.TileLogic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class TileGUI extends JFrame {
    protected static final int size = 16;
    protected JButton buttons[][] = new JButton[4][4];
    protected JPanel gamePanel;
    protected JPanel buttonPanel = new JPanel();
    protected JButton newGame = new JButton("New game");

    public ArrayList<Integer> numberList = new ArrayList<>(size);
    public int emptyCell;

    public TileGUI() {


        buttonPanel.add(newGame);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tar bort game pane och skapar upp ett nytt igen.
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

    private void startNewGame() {
        numberList.clear();
        gamePanel = new JPanel();

        //Addar nummer från 1-15 till listan och sedan shufflar det.
        for (int i = 0; i < size; i++) {
            numberList.add(i, i);
        }
        Collections.shuffle(numberList);

        for (int i = 0; i < size; i++) {
            int col = i % 4;
            int row = i / 4;
            buttons[row][col] = new JButton(String.valueOf(numberList.get(i)));

            if (numberList.get(i) == 0) {
                emptyCell = i;
                buttons[row][col].setVisible(false);
            }

            buttons[row][col].setForeground(Color.black);

            //KNAPPARNAS ACTION LISTENER
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

                    System.out.println("ROW DIFF: " + rowDifference + "\n" +
                            "COL DIFF: " + colDifference);

                    boolean sameRow = (rowForButtonPressed == rowForEmpty);
                    boolean sameCol = (colForButtonPressed == colForEmpty);
                    boolean notDiagonal = (sameRow || sameCol);
                    System.out.println("SAME ROW: " + sameRow + "\n" +
                            "SAME COL: " + sameCol);

                    System.out.println("DIAGONAL: " + notDiagonal);
                    if (notDiagonal) {
                        int difference = Math.abs(colDifference);
                        System.out.println(difference);

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
                        System.out.println(difference);

                        if (rowDifference < 0 & sameCol) {
                            for (int i = 0; i < difference; i++) {
                                System.out.println("INDEXINROW: " + i);
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

}