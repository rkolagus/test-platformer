package platformer01;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Platformer01 implements Runnable {

    Canvas canvas;
    BufferStrategy bufferStrategy;
    JFrame frame;
    
    Controls controls;
    Level level;
    
    int levelCounter;
    final String levelNames[] = {"level_01.txt", 
                                 "level_02.txt", 
                                 "level_03.txt"};
    final int width = 800,
            height = 600;
    long desiredFPS = 60,
            desiredDeltaLoop = (1000 * 1000 * 1000) / desiredFPS;
    /* Desired delta loop has an extra  (* 1000) compared to original */
    boolean running = true, debugMode = true;

    public Platformer01() throws Exception{
        frame = new JFrame("Shameless Clone");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, width, height);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        
        controls = new Controls();
        canvas.addKeyListener(controls);

        canvas.requestFocus();
        levelCounter = 0;
        level = new Level(this);
    }

    @Override
    public void run() {
        long beginLoopTime,
                endLoopTime,
                currentUpdateTime = System.nanoTime(),
                lastUpdateTime,
                deltaLoop;

        while (running) {
            beginLoopTime = System.nanoTime();

            render();

            lastUpdateTime = currentUpdateTime;
            currentUpdateTime = System.nanoTime();
            update((int) ((currentUpdateTime - lastUpdateTime) / (1000 * 1000)));

            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;

            if (deltaLoop > desiredDeltaLoop) {
                //Do nothing. We are already late.
            } else {
                try {
                    Thread.sleep((desiredDeltaLoop - deltaLoop) / (1000 * 1000));
                } catch (InterruptedException e) {
                    //Do nothing
                }
            }
            if (level.completed){
                levelCounter++;
                try {
                    if (levelCounter < levelNames.length){
                    level = new Level(this, levelNames[levelCounter]);
                    } else {
                        System.exit(0);
                    }
                } catch (Exception e) {
                    System.out.println("No more levels found: " + e);
                    System.exit(0);
                }
            }
        }
    }

    protected void update(int deltaTime) {
        level.update(controls);
    }


    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        render(g);
        g.dispose();
        bufferStrategy.show();
    }

    protected void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        this.level.render(g);
    }

    public static void main(String[] args) throws Exception {
        Platformer01 game = new Platformer01();
        new Thread(game).start();
    }
}
