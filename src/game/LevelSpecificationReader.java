package game;
import collidable.Velocity;
import sprites.BackgroundFactory;
import sprites.Block;
import sprites.Sprite;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    private int numberOfBalls = -1;
    private List<Velocity> initialBallVelocities = new ArrayList<Velocity>();
    private Map<String, Integer> paddle = new HashMap<>();
    private int paddleSpeed = -1;
    private int paddleWidth = -1;
    private String levelName = null;
    private Sprite getBackground = null;
    private List<Block> blocks = new ArrayList<Block>();
    private int numberOfBlocksToRemove = -1;
    private BlocksFromSymbolsFactory blocksFactory = null;
    private int x = -1;
    private int y = -1;
    private int rowHeight = -1;
    private String line;
    private BufferedReader bufferedReader;
    private List<LevelInformation> levels = new ArrayList<LevelInformation>();

    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        bufferedReader = new BufferedReader(reader);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                int levelFieldCountCheck = 10;
                // ignore empty lines and # lines
                if (line.contains("#") || line.isEmpty()) {
                    continue;
                }
                // iterate lines in a level paragraph
                if (line.equals("START_LEVEL")) {
                    line = bufferedReader.readLine();
                    while (!line.equals("END_LEVEL")) {
                        // read current line
                        String[] lineSplitted = line.split(":");
                        String lineSplitted0 = lineSplitted[0].trim();
                        String lineSplitted1 = lineSplitted[1].trim();
                        // importing relevant info from line
                        if (lineSplitted0.equals("level_name")) {
                            levelName = lineSplitted1;
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("ball_velocities")) {
                            String[] velocities = lineSplitted1.split(" ");
                            numberOfBalls = velocities.length;
                            for (int i = 0; i < velocities.length; i++) {
                                String[] veloSplit = velocities[i].split(",");
                                double angle = Double.parseDouble(veloSplit[0]);
                                double speed = Double.parseDouble(veloSplit[1]);
                                initialBallVelocities.add(Velocity.fromAngleAndSpeed(angle, speed));
                            }
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("background")) {
                            getBackground = new BackgroundFactory((lineSplitted1));
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("paddle_speed")) {
                            paddleSpeed = Integer.parseInt(lineSplitted1);
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("paddle_width")) {
                            paddleWidth = Integer.parseInt(lineSplitted1);
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("block_definitions")) {
                            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(lineSplitted1);
                            blocksFactory =
                                    BlocksDefinitionReader.fromReader(new BufferedReader(new InputStreamReader(is)));
                            is.close();
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("blocks_start_x")) {
                            x = Integer.parseInt(lineSplitted1);
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("blocks_start_y")) {
                            y = Integer.parseInt(lineSplitted1);
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("row_height")) {
                            rowHeight = Integer.parseInt(lineSplitted1);
                            --levelFieldCountCheck;
                        }
                        if (lineSplitted0.equals("num_blocks")) {
                            numberOfBlocksToRemove = Integer.parseInt(lineSplitted1);
                            --levelFieldCountCheck;
                        }
                        line = bufferedReader.readLine();
                        if (line.equals("START_BLOCKS")) {
                            blockCreation();
                        }
                    }
                    // update paddle map
                    paddle.put("speed", paddleSpeed);
                    paddle.put("width", paddleWidth);
                    if (levelFieldCountCheck == 0) {
                        // finally create a level information as we know it
                        LevelInformationGenerator newLevel =
                                new LevelInformationGenerator(numberOfBalls, initialBallVelocities, paddle,
                                        levelName, getBackground, blocks, numberOfBlocksToRemove);
                        levels.add(newLevel);
                        resetVars();
                    } else {
                        throw new RuntimeException("Error while reading Level Definitions. one of the fields was missing!");
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
        return levels;
    }

    /**
     * Block creation.
     */
    public void blockCreation() {
        try {
            line = bufferedReader.readLine();
            while (!line.equals("END_BLOCKS")) {
                int xNew = x;
                for (int i = 0; i < line.length(); i++) {

                    String symbol = String.valueOf(line.charAt(i));
                    if (blocksFactory != null && blocksFactory.isBlockSymbol(symbol)) {
                        blocks.add(blocksFactory.getBlock(symbol, xNew, y));
                        xNew += ((BlockCreation) (blocksFactory.getBlockCreators().get(symbol))).getWidth();
                    }
                    if (blocksFactory != null && blocksFactory.isSpaceSymbol(symbol)) {
                        xNew += blocksFactory.getSpaceWidth(symbol);
                    }
                }
                y += rowHeight;
                line = bufferedReader.readLine();
            }
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset vars.
     */
    public void resetVars() {
        numberOfBalls = -1;
        initialBallVelocities = new ArrayList<Velocity>();
        paddleSpeed = -1;
        paddleWidth = -1;
        levelName = null;
        getBackground = null;
        blocks = new ArrayList<Block>();
        paddle = new HashMap<>();
        numberOfBlocksToRemove = -1;
        //getBallPoint = new ArrayList<Point>();
        blocksFactory = null;
        x = -1;
        y = -1;
        rowHeight = -1;
    }
}
