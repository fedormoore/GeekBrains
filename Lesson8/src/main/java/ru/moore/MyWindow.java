package ru.moore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import static ru.moore.Task1.*;

public class MyWindow extends JFrame {

    private LinkedList<Point> xPoint = new LinkedList<Point>();
    private LinkedList<Point> oPoint = new LinkedList<Point>();

    private int sectionGrid;

    public MyWindow() {
        sectionGrid = SIZE_WINDOWS / SIZE_MAP;

        setTitle("Test Window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(SIZE_WINDOWS, SIZE_WINDOWS);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(SIZE_MAP, SIZE_MAP));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                for (int i = 1; i <= SIZE_MAP; i++) {
                    for (int j = 0; j <= SIZE_MAP; j++) {
                        if (e.getX() >= sectionGrid * i - sectionGrid && e.getX() <= sectionGrid * i && e.getY() >= sectionGrid * j - sectionGrid && e.getY() <= sectionGrid * j) {
                            humanTurn(j, i);
                        }
                    }
                }
            }
        });

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        simpleDraw(g);
    }

    private void simpleDraw(Graphics g2d) {
        for (int i = 1; i <= SIZE_MAP; i++) {
            g2d.drawLine(sectionGrid * i, 0, sectionGrid * i, SIZE_WINDOWS);
            g2d.drawLine(0, sectionGrid * i, SIZE_WINDOWS, sectionGrid * i);
        }

        for (int i = 0; i < xPoint.size(); i++) {
            g2d.drawLine(xPoint.get(i).x, xPoint.get(i).y, xPoint.get(i).x + sectionGrid, xPoint.get(i).y + sectionGrid);
            g2d.drawLine(xPoint.get(i).x + sectionGrid, xPoint.get(i).y, xPoint.get(i).x, xPoint.get(i).y + sectionGrid);
        }

        for (int i = 0; i < oPoint.size(); i++) {
            g2d.drawOval(oPoint.get(i).x, oPoint.get(i).y, sectionGrid, sectionGrid);
        }
    }

    protected void setX(int rowIndex, int colIndex) {
        xPoint.add(new Point(sectionGrid * colIndex - sectionGrid, sectionGrid * rowIndex - sectionGrid));
        repaint();
    }

    protected void setO(int rowIndex, int colIndex) {
        oPoint.add(new Point(sectionGrid * colIndex - sectionGrid, sectionGrid * rowIndex - sectionGrid));
        repaint();
    }

}
