package TileLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class TileGUI extends JFrame {
    private int rows = 4;
    private int cols = 4;
    private JButton buttons[];
    private JPanel gamePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JButton newGame = new JButton("New game");
    private JButton endGame = new JButton("End game");

    public TileGUI() {
        buttons = new JButton[16];

        add(buttonPanel, BorderLayout.NORTH);
        buttonPanel.add(newGame);
        newGame.addActionListener(e -> {
        });
        buttonPanel.add(endGame);
        endGame.addActionListener(e -> {
        });

        setSize(500, 500);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        add("South", gamePanel);
        gamePanel.setLayout(new GridLayout(rows, cols));
        gamePanel.setPreferredSize(new Dimension(400, 400));
        for (int i = 1; i < 16; i++) {
            buttons[i] = new JButton(String.valueOf(i));
            gamePanel.add(buttons[i]);
        }
        gamePanel.remove(6);
        gamePanel.add(new JPanel(), 6);


    }

}