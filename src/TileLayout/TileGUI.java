package TileLayout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class TileGUI extends JFrame {
    private static final int size = 16;
    private JButton buttons[][] = new JButton[4][4];
    private JPanel gamePanel;
    private JPanel buttonPanel = new JPanel();
    private JButton newGame = new JButton("New game");

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
                repaint();
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
        System.out.println(numberList);

        //Addar nummer från 1-15 till listan och sedan shufflar det.
        for (int i = 0; i < size; i++) {
            numberList.add(i, i);
        }
        Collections.shuffle(numberList);

        for (int i = 0; i < size; i++) {
            int col = i % 4;
            int row = i / 4;
            buttons[row][col] = new JButton(String.valueOf(numberList.get(i)));
            System.out.println(numberList.get(i));

            if (numberList.get(i) == 0) {
                emptyCell = i;
                buttons[row][col].setVisible(false);
            }

            //Stylar text och lägger till actionlistener till knapparna.
            buttons[row][col].setForeground(Color.black);
            buttons[row][col].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("TAPPED: " + e.getSource());
                }
            });

            gamePanel.setLayout(new GridLayout(4, 4));
            gamePanel.add(buttons[row][col]);
        }

        add(gamePanel, BorderLayout.CENTER);
        repaint();
    }

}