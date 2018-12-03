package minesweeper;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Screen extends JFrame {

    String end;
    public int mx;
    public int my;
    public int width = 806;
    public int height = 689;
    int neigh = 0;
    int totalM = 0;
    int totalR = 0;

    Random rand = new Random();

    boolean[][] mine = new boolean[16][16];
    int[][] other = new int[16][16];
    boolean[][] revealed = new boolean[16][16];
    boolean[][] flagged = new boolean[16][16];

    public Screen() {
        this.setTitle("Minesweeper");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (rand.nextInt(100) < 20) {
                    mine[i][j] = true;
                    totalM++;
                } else {
                    mine[i][j] = false;
                }
                revealed[i][j] = false;
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                neigh = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 16; n++) {
                        if (!(m == i && n == j)) {
                            if (isN(i, j, m, n) == true) {
                                neigh++;
                            }
                        }
                    }
                }
                other[i][j] = neigh;
            }
        }

        Buttons button = new Buttons();
        this.setContentPane(button);
        Move move = new Move();
        this.addMouseMotionListener(move);
        Click click = new Click();
        this.addMouseListener(click);
    }

    public class Buttons extends JPanel {

        public void paintComponent(Graphics g) {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    g.setColor(Color.gray);
                    if (revealed[i][j] == true) {
                        if (mine[i][j] == true) {
                            g.setColor(Color.black);
                        } else {
                            g.setColor(Color.white);
                        }
                    }
                    if (mx >= 6 + i * 600 / 16 + 100 && mx < i * 600 / 16 + 100 + 600 / 16 + 3 && my >= 3 + j * 600 / 16 + 30 + 26 && my < j * 600 / 16 + 30 + 26 + 600 / 16) {
                        g.setColor(Color.LIGHT_GRAY);
                    }
                    g.fillRect(3 + i * 600 / 16 + 100, 3 + j * 600 / 16 + 30, 600 / 16 - 3, 600 / 16 - 3);

                    if (revealed[i][j] == true) {
                        if (mine[i][j] == false) {
                            totalR++;
                            g.setColor(Color.black);
                            g.setFont(new Font("Ariel", Font.BOLD, 23));
                            g.drawString(Integer.toString(other[i][j]), i * 600 / 16 + 100 + 13, j * 600 / 16 + 30 + 26 + 2);
                            if (totalR == 256 - totalM) {
                                end = "VICTORY!";
                                g.setColor(Color.green);
                                g.setFont(new Font("Ariel", Font.BOLD, 70));
                                g.drawString(end, 200, 325);
                            }
                        } else {
                            end = "DEFEAT!";
                            g.setColor(Color.red);
                            g.setFont(new Font("Ariel", Font.BOLD, 70));
                            g.drawString(end, 200, 325);
                        }
                    }
                }
            }
        }
    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            //System.out.println("X: "+mx+" Y: "+my);
        }

    }

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            if (inBoxX() != -1 && inBoxY() != -1) {
                if (mine[inBoxX()][inBoxY()]) {
                    System.out.println("Game Over");
                    /*for (int i = 0; i < 16; i++) {
                        for (int j = 0; j < 16; j++) {
                            revealed[i][j] = true;
                        }
                    }*/

                }
                revealed[inBoxX()][inBoxY()] = true;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    public int inBoxX() {
        for (int i = 0; i <= 16; i++) {
            for (int j = 0; j <= 16; j++) {
                if (mx >= 6 + i * 600 / 16 + 100 && mx < i * 600 / 16 + 100 + 600 / 16 + 3 && my >= 3 + j * 600 / 16 + 30 + 26 && my < j * 600 / 16 + 30 + 26 + 600 / 16) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int inBoxY() {
        for (int i = 0; i <= 16; i++) {
            for (int j = 0; j <= 16; j++) {
                if (mx >= 6 + i * 600 / 16 + 100 && mx < i * 600 / 16 + 100 + 600 / 16 + 3 && my >= 3 + j * 600 / 16 + 30 + 26 && my < j * 600 / 16 + 30 + 26 + 600 / 16) {
                    return j;
                }
            }
        }
        return -1;
    }

    public boolean isN(int mX, int mY, int cX, int cY) {
        if (mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mine[cX][cY] == true) {
            return true;
        }
        return false;
    }

}
