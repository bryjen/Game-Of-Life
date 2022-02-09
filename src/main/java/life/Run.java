package life;

import life.threads.UpdateFrameThread;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Run extends Thread {

    public final static int globalSize = 20;
    public static boolean paused = false;
    public static Thread mainThread;

    public static void main(String[] args) {
        Run main = new Run();
        mainThread = main;
        main.start();
    }

    @Override
    public synchronized void run() {
        Universe universe = new Universe(globalSize, new Random().nextInt());
        Universe nextUniverse = new Universe(globalSize);

        GameOfLife display = new GameOfLife();
        display.update(universe);


        while (!isInterrupted()) {
            while (paused) {
                try { TimeUnit.MILLISECONDS.sleep(500); } catch (Exception ignored) {}
            }
            if (isInterrupted()) { break; }
            for (int i = 0; i < universe.size; i++) {
                for (int j = 0; j < universe.size; j++) {
                    int adjacent = universe.getNumberOfAdjacentNeighbors(i, j);
                    if (universe.getCell(i, j)) {
                        nextUniverse.setCell(i, j, adjacent == 2 || adjacent == 3);
                    } else {
                        nextUniverse.setCell(i, j, adjacent == 3);
                    }
                }
            }

            if (isInterrupted()) { break; }
            try {
                UpdateFrameThread frame = new UpdateFrameThread(universe, display);

                frame.start();
                display.updateLabels(universe);
                Thread.sleep(500);

                frame.join();

                nextUniverse.copyTo(universe);
                display.update(universe);
                universe.incrementGeneration();

            } catch (InterruptedException e) {
                break;
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
        display.dispose();
    }
}
