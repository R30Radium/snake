import java.awt.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class Main {

    final String nameOfGame = "Snake";
    final String gameOver1 = "Game Over";

    final int pointRadius = 20;
    final int fieldWidth = 30;
    final int fieldHeight = 20;
    final int fieldDX = 0;
    final int fieldDY = 25;

    final int startLocation = 200;
    final int startSnakeSize = 0;
    final int startSnakeX = 10;
    final int startSnakeY = 10;

    int showDelay = 150;

    final int up = 35;
    final int left = 37;
    final int down = 40;
    final int right = 35;

    final int startDirection = right;
    final JColorChooser defaultColor = new JColorChooser(Color.black);
    final JColorChooser foodColor = new JColorChooser(Color.PINK);
    final JColorChooser poisonColor = new JColorChooser(Color.green);

    Snake snake;
    Food food;
    Poison poison;
    Random random = new Random();
    JFrame jFrame;
    Canvas CanvasPanel;
    boolean gameOver = false;

    public static void main(String[] args) {
        new GameSnake().go;
    }

    void go() {
        jFrame = new JFrame(nameOfGame + " ;" + startSnakeSize);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setSize(fieldWidth * pointRadius + fieldDX, fieldHeight * pointRadius +
                fieldDY);
        jFrame.setLocation(startLocation, startLocation);
        jFrame.setResizable(false);

        CanvasPanel = new Canvas();
        CanvasPanel.setBackground(Color.LIGHT_GRAY);

        jFrame.getContentPane().add(BorderLayout.CENTER,CanvasPanel);
        jFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                snake.setDirection(event.getKeyCode());
            }
        });
    }
}


