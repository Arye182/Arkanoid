package game;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.io.IOException;
import java.io.File;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static game.GameLevel.SCREEN_HEIGHT;
import static game.GameLevel.SCREEN_WIDTH;

/**
 * The type Ass 7 game.
 * this class just runs the game by creating new game, initializing it and
 * runs it.
 *
 * @author Arye Amsalem
 * @version 3.00 21 June 2019
 */
public class Ass7Game {

    /**
     * Load table high scores table.
     *
     * @param highScoresTable the high scores table
     * @param hs1             the hs 1
     * @return the high scores table
     */
    public HighScoresTable loadTable(HighScoresTable highScoresTable, File hs1) {
        try {
            if (hs1.createNewFile()) {
                //  file does not exist, and we create a new table and immediately save it to a file.
                System.out.println("File is created!");
                highScoresTable = new HighScoresTable(5);
                highScoresTable.save(hs1);
            } else {
                // a file will exist, and so the table will be read from that file
                System.out.println("File already exists.");
                highScoresTable = HighScoresTable.loadFromFile(hs1);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
        return highScoresTable;
    }

    /**
     * Load levels list.
     *
     * @param levelDefinitions the level definitions
     * @return the list
     */
    public List<LevelInformation> loadLevels(String levelDefinitions) {
        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
        Reader reader = null;
        // creating reader to reads from files
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelDefinitions);
            // setting up a list of levels
            List<LevelInformation> levels =  levelSpecificationReader.fromReader(new InputStreamReader(is));
            // close the reader
            is.close();
            return levels;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The entry point of application.
     * here all starts. first create a game, create levels list and run them.
     *
     * @param args the input arguments which are the levels.
     */
    public static void main(String[] args) {

        // fields
        final Ass7Game game = new Ass7Game();
        final File highScores = new File("highscores.ser");
        final HighScoresTable highScoresTable = game.loadTable(new HighScoresTable(5), highScores);

        // creating other things for game
        final GUI gui = new GUI("Arkanoid", SCREEN_WIDTH, SCREEN_HEIGHT);
        final KeyboardSensor keyboard = gui.getKeyboardSensor();
        final DialogManager dialogManager = gui.getDialogManager();
        final AnimationRunner runner = new AnimationRunner(60, gui);

        // levels variables
        String levelSetPath = "level_sets.txt";
        List<LevelSet> levelsSets;
        Map<String, List<LevelInformation>> levelsMap = new HashMap<String, List<LevelInformation>>();
        // supporting args as level set path
        if (args.length > 0) {
            levelSetPath = args[0];
        }
        LevelSetReader levelSetReader = new LevelSetReader(levelSetPath);
        levelsSets = levelSetReader.fromReader();

        // creating sub-menu
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>("Level Set", keyboard, runner, false);
        for (int i = 0; i < levelsSets.size(); i++) {
            String key = levelsSets.get(i).getPath();
            subMenu.addSelection(levelsSets.get(i).getKey(), levelsSets.get(i).getMessage(), new Task<Void>() {
                public Void run() {
                    GameFlow gameFlow = new GameFlow(gui, runner, keyboard, highScoresTable, dialogManager, highScores);
                    List<LevelInformation> levels = game.loadLevels(key);
                    gameFlow.runLevels(levels);
                    return null;
                }
            });
        }

        // creating new main menu
        Menu<Task<Void>> mainMenu = new MenuAnimation<Task<Void>>("Arkanoid", keyboard, runner, true);
        mainMenu.addSubMenu("s", "LevelSets", subMenu);
        mainMenu.addSelection("h", "High Scores Table", new Task<Void>() {
            public Void run() {
                runner.run(new KeyPressStoppableAnimation(keyboard, "space", new HighScoresAnimation(highScoresTable)));
                return null;
            }
        });
        mainMenu.addSelection("q", "Quit", new Task<Void>() {
            public Void run() {
                System.exit(0);
                return null;
            }
        });

        // run the game according to main menu and user selection
        while (true) {
            runner.run(mainMenu);
            Task<Void> task = mainMenu.getStatus();
            task.run();
            ((MenuAnimation) mainMenu).reset();
        }
    }
}
