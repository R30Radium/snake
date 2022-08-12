
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Main {

    final String title = "Classic Game Snake";
    final String gameIsOver = "GAME OVER";
    final int pointRadius = 20;
    final int fieldOfWight = 30;
    final int fieldOfHeight = 20;
    final int fieldX = 6;
    final int fieldy = 28;
    final int startLocation = 200;
    final int startSnakeSize = 6;
    final int startSnakeX = 10;
    final int startSnakeY = 10;
    final int delay = 150;
    final int left = 37;
    final int up = 38;
    final int right = 39;
    final int down = 40;
    final int startDirection = right;
    final Color color = Color.black;
    final Color colorByDefault = Color.black;
    final Color ColorOfFood = Color.green;

    Snake snake;
    Food food;

    JFrame frame;
    Canvas canvasPanel;
    Random random = new Random();
    boolean gameOver = false;

    public static void main(String[] args) {
        new Main().go();
    }

    void go() {
        frame = new JFrame(title + " : " + startSnakeSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(fieldOfWight * pointRadius + fieldX,
                fieldOfHeight * pointRadius + fieldy);
        frame.setLocation(startLocation, startLocation);
        frame.setResizable(false);

        canvasPanel = new Canvas();
        canvasPanel.setBackground(Color.white);

        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                snake.setDirection(e.getKeyCode());
            }
        });

        frame.setVisible(true);

        snake = new Snake(startSnakeX, startSnakeY, startSnakeSize, startDirection);
        food = new Food();

        while (!gameOver) {
            snake.move();
            if (food.isEaten()) {
                food.next();
            }
            canvasPanel.repaint();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    class Snake {
        ArrayList<Point> snake = new ArrayList<Point>();
        int direction;

        public Snake(int x, int y, int length, int direction) {
            for (int i = 0; i < length; i++) {
                Point point = new Point(x-i, y);
                snake.add(point);
            }
            this.direction = direction;
        }

        boolean isInsideSnake(int x, int y) {
            for (Point point : snake) {
                if ((point.getX() == x) && (point.getY() == y)) {
                    return true;
                }
            }
            return false;
        }

        boolean isFood(Point food) {
            return ((snake.get(0).getX() == food.getX()) && (snake.get(0).getY() == food.getY()));
        }

        void move() {
            int x = snake.get(0).getX();
            int y = snake.get(0).getY();
            if (direction == left) { x--; }
            if (direction == right) { x++; }
            if (direction == up) { y--; }
            if (direction == down) { y++; }
            if (x > fieldOfWight - 1) { x = 0; }
            if (x < 0) { x = fieldOfWight - 1; }
            if (y > fieldOfHeight - 1) { y = 0; }
            if (y < 0) { y = fieldOfHeight - 1; }
            gameOver = isInsideSnake(x, y); // check for acrooss itselves
            snake.add(0, new Point(x, y));
            if (isFood(food)) {
                food.eat();
                frame.setTitle(title + " : " + snake.size());
            } else {
                snake.remove(snake.size() - 1);
            }
        }

        void setDirection(int direction) {
            if ((direction >= left) && (direction <= down)) {
                if (Math.abs(this.direction - direction) != 2) {
                    this.direction = direction;
                }
            }
        }

        void paint(Graphics g) {
            for (Point point : snake) {
                point.paint(g);
            }
        }
    }

    class Food extends Point {

        public Food() {
            super(-1, -1);
            this.color = ColorOfFood;
        }

        void eat() {
            this.setXY(-1, -1);
        }

        boolean isEaten() {
            return this.getX() == -1;
        }

        void next() {
            int x, y;
            do {
                x = random.nextInt(fieldOfWight);
                y = random.nextInt(fieldOfHeight);
            } while (snake.isInsideSnake(x, y));
            this.setXY(x, y);
        }
    }

    class Point {
        int x, y;
        Color color = colorByDefault;

        public Point(int x, int y) {
            this.setXY(x, y);
        }

        void paint(Graphics g) {
            g.setColor(color);
            g.fillOval(x * pointRadius, y * pointRadius,pointRadius, pointRadius);
        }

        int getX() { return x; }
        int getY() { return y; }

        void setXY(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public class Canvas extends JPanel {


        public void paint(Graphics g) {
            super.paint(g);
            snake.paint(g);
            food.paint(g);
            if (gameOver) {
                g.setColor(Color.red);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                FontMetrics fm = g.getFontMetrics();
                g.drawString(gameIsOver,
                        (fieldOfWight * pointRadius + fieldX - fm.stringWidth(gameIsOver)) / 2,
                        (fieldOfHeight * pointRadius + fieldy) / 2);
            }
        }
    }
}