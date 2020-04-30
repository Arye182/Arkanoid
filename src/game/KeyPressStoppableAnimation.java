package game;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Key press stoppable animation.
 * this is a decorator that wraps other animations that has key pressed stop condition
 * like our pause screen and end screen.
 *
 * @author Arye Amsalem
 * @version 1.00 28 May 2019
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboardSensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed = true;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param sensor    the sensor
     * @param key       the key
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.animation = animation;
        this.key = key;
        this.keyboardSensor = sensor;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);
        // when space-bar is pressed - its time to finish the animation
        //In doOneFrame, when you check if the key m is pressed, don't do anything in case isAlreadyPressed == true.
        // This means that if the key was pressed before the animation started, we ignore the key press.
        //In doOneFrame, if the key m is not pressed, set isAlreadyPressed=false. Now, we know that
        // there was a time point after the animation started in which m was not pressed.
        if (key.equals("space")) {
            if (this.keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
                if (!isAlreadyPressed) {
                    this.stop = true;
                }
            } else {
                isAlreadyPressed = false;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}
