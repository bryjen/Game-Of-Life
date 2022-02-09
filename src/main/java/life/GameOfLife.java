package life;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {

    JLabel generations;
    JLabel aliveCount;
    JPanel panel;

    private static final Font font = new Font("Courier", Font.BOLD, 13);

    public GameOfLife() {
        super("The Game of Life");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        this.generations = new JLabel("generations:");
        generations.setName("GenerationLabel");
        generations.setBounds(42, 30, 200, 20);
        generations.setFont(font);
        add(generations);

        this.aliveCount = new JLabel("alive:");
        aliveCount.setName("AliveLabel");
        aliveCount.setBounds(42, 50, 100, 20);
        aliveCount.setFont(font);
        add(aliveCount);

        this.panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(242, 20, 720, 720);
        add(panel);

        initInteractiveComponents();

        setVisible(true);
    }

    public void initInteractiveComponents() {
        //configs - pause, continue, and reset
        JButton pause = new JButton("P");
        JButton resume = new JButton("R");
        JButton reset = new JButton("E");

        pause.setBounds(40, 100, 50, 30);
        pause.addActionListener(actionEvent -> {
            Run.paused = true;
            System.out.println("paused");
        });
        add(pause);

        resume.setBounds(100, 100, 50, 30);
        resume.addActionListener(actionEvent -> {
            Run.paused = false;
            System.out.println("resumed");
        });
        add(resume);

        reset.setBounds(160, 100, 50, 30);
        reset.addActionListener(actionEvent -> {
            System.out.println("reset");
            try {
                Run.mainThread.interrupt();
                Run.mainThread.join(1000);
                System.out.println(Run.mainThread.getState());
            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                Run newMain = new Run();
                Run.mainThread = newMain;
                newMain.start();
            }
            System.out.println("hehrehr");
        });
        add(reset);
    }

    public synchronized void update(Universe universe) {
        this.panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(242, 20, 720, 720);
        add(panel);

        panel.setLayout(new GridLayout(Run.globalSize, Run.globalSize, 1, 1));
        try {
            generateUpperPortion thread1 = new generateUpperPortion(universe, panel);
            generateBottomPortion thread2 = new generateBottomPortion(universe, panel);

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* //deprecated
        for (int i = 0; i < Run.globalSize; i++) {
            for (int j = 0; j < Run.globalSize; j++) {
                JPanel state = new JPanel();
                if (universe.getCell(i, j)) {
                    state.setBackground(Color.lightGray);
                    //state.setBackground(new Random().nextBoolean() ? Color.gray : (new Random().nextBoolean() ? Color.darkGray : Color.lightGray));
                }
                panel.add(state);
            }
        }
         */
    }

    public synchronized void updateLabels(Universe universe) {
        this.generations.setText("Generation #" + universe.getGeneration());
        this.aliveCount.setText("Alive: " + universe.getAliveCount());
    }
}

class generateBottomPortion extends Thread {
    private final Universe universe;
    private final JPanel panel;

    public generateBottomPortion(Universe universe, JPanel panel) {
        this.universe = universe;
        this.panel = panel;
    }

    @Override
    public void run() {
        for (int i = Run.globalSize / 2; i < Run.globalSize; i++) {
            for (int j = 0; j < Run.globalSize; j++) {
                JPanel state = new JPanel();
                if (universe.getCell(i, j)) {
                    state.setBackground(Color.lightGray);
                }
                panel.add(state);
            }
        }
    }
}

class generateUpperPortion extends Thread {
    private final Universe universe;
    private final JPanel panel;

    public generateUpperPortion(Universe universe, JPanel panel) {
        this.universe = universe;
        this.panel = panel;
    }

    @Override
    public void run() {
        for (int i = 0; i < Run.globalSize / 2; i++) {
            for (int j = 0; j < Run.globalSize; j++) {
                JPanel state = new JPanel();
                if (universe.getCell(i, j)) {
                    state.setBackground(Color.lightGray);
                }
                panel.add(state);
            }
        }
    }
}