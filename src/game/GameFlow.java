package game;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Game flow.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public class GameFlow {

    // game fields
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private GUI gui;
    private boolean running;
    private Counter scoreCounter;
    private Counter livesCounter;
    private HighScoresTable highScoresTable;
    private DialogManager dialogManager;
    private ScoreInfo player;
    private File file;


    /**
     * Instantiates a new Game flow.
     *
     * @param gui             the gui
     * @param ar              the animation runner
     * @param ks              the keyboardSensor
     * @param highScoresTable the high scores table
     * @param dialogManager   the dialog manager
     * @param file            the file
     */
    public GameFlow(GUI gui, AnimationRunner ar, KeyboardSensor ks, HighScoresTable highScoresTable,
                    DialogManager dialogManager, File file) {
        this.keyboard = ks;
        this.runner = ar;
        this.gui = gui;
        this.highScoresTable = highScoresTable;
        this.dialogManager = dialogManager;
        this.file = file;
        // scores
        this.scoreCounter = new Counter(0);
        // lives
        this.livesCounter = new Counter(7);
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        // play all the levels in the game
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboard,
                    this.runner, this.scoreCounter, this.livesCounter);
            level.initialize();
            while (level.getBlocksCounter().getValue() != 0 && level.getLivesCounter().getValue() > 0) {
                level.playOneTurn();
                if (level.getBallsCounter().getValue() == 0) {
                    livesCounter.decrease(1); // -- lives due turn end
                }
                level.getBallsCounter().increase(levelInfo.numberOfBalls()); // +2 to balls counter
                if (livesCounter.getValue() <= 0) {
                    break;
                }
            }
            if (livesCounter.getValue() <= 0) {
                break;
            }
        }
        // if the player is suitable to get to top 5 players :)
        if (highScoresTable.getRank(this.scoreCounter.getValue()) <= highScoresTable.size()) {
            String name = this.dialogManager.showQuestionDialog("Enter Name", "What is your name?", "Anonymous");
            player = new ScoreInfo(name, this.scoreCounter.getValue());
            this.highScoresTable.add(this.player);
            try {
                this.highScoresTable.save(file);
            } catch (IOException e) {
                System.err.println("Failed saving the high scores table");
                e.printStackTrace(System.err);
            }
        }
        // END SCREEN
        runner.run(new KeyPressStoppableAnimation(keyboard, "space",
                new EndScreen(scoreCounter, livesCounter)));
        runner.run(new KeyPressStoppableAnimation(keyboard, "space",
                new HighScoresAnimation(highScoresTable)));
    }
}
