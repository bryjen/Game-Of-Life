package life;

import java.util.*;

public class Universe {

    boolean[][] universe;
    int size;

    private int generation;

    public synchronized void incrementGeneration() {
        generation++;
    }

    public synchronized int getGeneration() {
        return generation;
    }

    public synchronized int getAliveCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getCell(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * creates a buffer universe - to store the changes in a generation -> which will be copied into the current generation, and then iterated
     */
    public Universe(int size) {
        this.universe = new boolean[size][size];
    }

    public Universe(int size, int seed) {
        this.size = size;
        universe = new boolean[size][size];
        Random random = new Random(seed);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                universe[i][j] = random.nextInt(2) == 1;
            }
        }
    }

    public synchronized boolean getCell(int i, int j) {
        return universe[i][j];
    }

    synchronized void setCell(int i, int j, boolean state) {
        universe[i][j] = state;
    }

    synchronized int getNumberOfAdjacentNeighbors(int i, int j) {
        int aliveCount = 0;

        if (universe[i][(j + 1) % size]) { //rightward search
            aliveCount++;
        }
        if (universe[i][(j - 1 + size) % size]) { //leftward search
            aliveCount++;
        }
        if (universe[(i - 1 + size) % size][j]) { //above search
            aliveCount++;
        }
        if (universe[(i + 1) % size][j]) { //below search
            aliveCount++;
        }

        if (universe[(i - 1 + size) % size][(j + 1 ) % size]) { //upper right search
            aliveCount++;
        }
        if (universe[(i + 1) % size][(j + 1) % size]) { //lower right search
            aliveCount++;
        }
        if (universe[(i - 1 + size) % size][(j - 1 + size) % size]) { //upper left search
            aliveCount++;
        }
        if (universe[(i + 1) % size][(j - 1 + size) % size]) { //lower left search
            aliveCount++;
        }
        return aliveCount;
    }

    /**
     * Copy the cells from this world to the other;
     * @param thisUniverse the world to be transferred to;
     */
    synchronized void copyTo(life.Universe thisUniverse) {
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                thisUniverse.setCell(i, j, this.getCell(i, j));
            }
        }
    }

    /**
     * @deprecated since 26/1/2021 only used to debug output directly (set param to true)
     *
     * Displays the current universe
     * @param withLines displayes either with "-" if set to true, or " "(spaces) if set to false;
     */
    public void display(boolean withLines) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(universe[i][j]? "O" : (withLines? "-" : " "));
            }
            System.out.println("");
        }
    }
}