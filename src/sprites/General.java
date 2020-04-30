package sprites;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * The type General.
 */
public class General {
    /**
     * Draw background.
     *
     * @param d           the d
     * @param blockColors the block colors
     * @param colorIndex  the color index
     * @param id          represents who the grid belongs too (a level or end screen)
     */
    public static void drawBackground(DrawSurface d, Color[] blockColors, int[][] colorIndex, String id) {
        int x = 0, y = 0;
        if (id.equals("level")) {
            y = 50;
        }
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 40; j++) {
                d.setColor(blockColors[colorIndex[i][j]]);
                d.fillRectangle(x, y, 20, 20);
                //d.setColor(Color.green);
                //d.drawRectangle(x, y, 20, 20 );
                x += 20;
            }
            x = 0;
            y += 20;
        }
    }

    /**
     * Gets random color.
     *
     * @return the random color
     */
    public static Color getRandomColor() {
        Random rand = new Random(); // create a random-number generator
        // random a color combination (RGB = red, green blue)
        float r = rand.nextFloat(); // red ingredient
        float g = rand.nextFloat(); // green ingredient
        float b = rand.nextFloat(); // blue ingredient
        return new Color(r, g, b); // creating new randomly color mix!
    }

    /**
     * Load image image.
     *
     * @param path the path
     * @return the image
     */
    public static Image loadImage(String path) {
        // load the image data into an java.awt.Image object
        Image img = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            img = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
