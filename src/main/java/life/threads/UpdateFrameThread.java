package life.threads;

import life.GameOfLife;
import life.Universe;

public class UpdateFrameThread extends Thread {

    Universe universe;
    GameOfLife display;

    public UpdateFrameThread(Universe universe, GameOfLife display) {
        this.universe = universe;
        this.display = display;
    }

    @Override
    public void run() {
        display.update(universe);
    }
}
