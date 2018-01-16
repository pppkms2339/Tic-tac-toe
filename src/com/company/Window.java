package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Window extends JFrame {
    private char[][] field = new char[3][3];
    private int[][] directions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private final char EMPTY_CELL_SYMBOL = '.';
    private final char HUMAN_MOVE_SYMBOL = 'X';
    private final char PC_MOVE_SYMBOL = 'O';

    private Random random = new Random();
    JButton[] buttons = new JButton[9];

    public Window() {
        setTitle("Игра 'Крестики-Нолики'");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int formWidth = 300;
        int formHeight = 300;
        setBounds((screenSize.width - formWidth) / 2, (screenSize.height - formHeight) / 2, formWidth, formHeight);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", java.awt.Font.BOLD, 30));
            add(buttons[i]);
        }

        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(0)) {
                    game();
                }
            }
        });

        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(1)) {
                    game();
                }
            }
        });

        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(2)) {
                    game();
                }
            }
        });

        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(3)) {
                    game();
                }
            }
        });

        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(4)) {
                    game();
                }
            }
        });

        buttons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(5)) {
                    game();
                }
            }
        });

        buttons[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(6)) {
                    game();
                }
            }
        });

        buttons[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(7)) {
                    game();
                }
            }
        });

        buttons[8].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanMove(8)) {
                    game();
                }
            }
        });

        setVisible(true);
        startGame();
    }

    private void startGame() {
        fieldInitialize();
        fieldPrint();

        Object[] options = {"Человек", "Компьютер"};
        int result = JOptionPane.showOptionDialog(null, "Кто будет ходить первым?", "Крестики-нолики",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == 0) {
            //Человек ходит первым
            return;
        } else {
            //Компютер ходит первым
            pcMove();
            fieldPrint();
        }
    }

    private void endGame() {
        int result = JOptionPane.showConfirmDialog(null, "Повторить игру?", "Крестики-нолики", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            startGame();
        } else {
            this.dispose();
        }
    }

    private void fieldInitialize() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = EMPTY_CELL_SYMBOL;
            }
        }
    }

    private void fieldPrint() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] != EMPTY_CELL_SYMBOL) {
                    buttons[3 * i + j].setText(String.valueOf(field[i][j]));
                } else {
                    buttons[3 * i + j].setText("");
                }
            }
        }
    }

    private boolean isValidCell(int line, int column) {
        if (line < 0 || line >= 3 || column < 0 || column >= 3) {
            return false;
        }
        if (field[line][column] == EMPTY_CELL_SYMBOL)
            return true;
        return false;
    }

    private boolean humanMove(int cellNumber) {
        int line = cellNumber / 3;
        int column = cellNumber % 3;
        if (isValidCell(line, column)) {
            field[line][column] = HUMAN_MOVE_SYMBOL;
            return true;
        } else {
            return false;
        }
    }

    private void pcMove() {
        //Пытаемся выиграть
        int emptyCellNumber = emptyCell(PC_MOVE_SYMBOL);
        if (emptyCellNumber != 9) {
            pcMoveByCellNumber(emptyCellNumber);
            return;
        }
        //Пытаемся блокировать человека
        emptyCellNumber = emptyCell(HUMAN_MOVE_SYMBOL);
        if (emptyCellNumber != 9) {
            pcMoveByCellNumber(emptyCellNumber);
            return;
        }
        //Делаем случайный ход
        int line;
        int column;
        do {
            line = random.nextInt(3);
            column = random.nextInt(3);
        } while (!isValidCell(line, column));
        field[line][column] = PC_MOVE_SYMBOL;
    }

    private void pcMoveByCellNumber(int cellNumber) {
        int line = cellNumber / 3;
        int column = cellNumber % 3;
        field[line][column] = PC_MOVE_SYMBOL;
    }

    private int emptyCell(char symbol) {
        int line;
        int column;
        int countFull;
        int countEmpty;
        int result = 0;
        for (int i = 0; i < 8; i++) {
            countFull = 0;
            countEmpty = 0;
            for (int j = 0; j < 3; j++) {
                line = directions[i][j] / 3;
                column = directions[i][j] % 3;
                if (field[line][column] == symbol) {
                    countFull++;
                }
                if (field[line][column] == EMPTY_CELL_SYMBOL) {
                    countEmpty++;
                    result = directions[i][j];
                }
            }
            if (countFull == 2 && countEmpty == 1) {
                return result;
            }
        }
        return 9;
    }

    private void game() {
        fieldPrint();
        if (isVictory(HUMAN_MOVE_SYMBOL)) {
            JOptionPane.showMessageDialog(null, "Победил человек!", "Крестики-нолики", JOptionPane.INFORMATION_MESSAGE);
            endGame();
            return;
        }
        if (isFieldFull()) {
            JOptionPane.showMessageDialog(null, "Ничья!", "Крестики-нолики", JOptionPane.INFORMATION_MESSAGE);
            endGame();
            return;
        }
        pcMove();
        fieldPrint();
        if (isVictory(PC_MOVE_SYMBOL)) {
            JOptionPane.showMessageDialog(null, "Победил компьютер!", "Крестики-нолики", JOptionPane.INFORMATION_MESSAGE);
            endGame();
            return;
        }
        if (isFieldFull()) {
            JOptionPane.showMessageDialog(null, "Ничья!", "Крестики-нолики", JOptionPane.INFORMATION_MESSAGE);
            endGame();
        }
    }

    private boolean isVictory(char symbol) {
        int line;
        int column;
        int count;
        for (int i = 0; i < 8; i++) {
            count = 0;
            for (int j = 0; j < 3; j++) {
                line = directions[i][j] / 3;
                column = directions[i][j] % 3;
                if (field[line][column] == symbol) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isFieldFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == EMPTY_CELL_SYMBOL) {
                    return false;
                }
            }
        }
        return true;
    }
}
