package game;

/**
 * The type Counter.
 * this is the class that let us create Counter objects. we will use this
 * counters to count things like score, lives, balls and etc.
 * this counter has some basic methods like increasing or decreasing the value
 * it holds.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public class Counter {
    private int value; // the value the counter holds

    /**
     * Instantiates a new Counter with starting value.
     *
     * @param starting the starting value
     */
    public Counter(int starting) {
        this.value = starting;
    }

    /**
     * Increase.
     * add number to current value of counter.
     *
     * @param number the number
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * Decrease.
     * subtract number from current value of counter.
     *
     * @param number the number
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * Gets value.
     * get current count.
     *
     * @return the value
     */
    public int getValue() {
        return this.value;
    }
}
