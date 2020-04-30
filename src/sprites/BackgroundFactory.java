package sprites;
import biuoop.DrawSurface;
import java.awt.Color;
import java.awt.Image;


/**
 * The type Background factory.
 */
public class BackgroundFactory implements Sprite {
    private String path;
    private Color bgColor;
    private boolean isImage = false;
    private boolean isColor = false;
    private Image img;

    /**
     * Instantiates a new Background factory.
     *
     * @param bgDescription the bg description
     */
    public BackgroundFactory(String bgDescription) {
        // case image
        if (bgDescription.contains("image")) {
            int length = bgDescription.length();
            this.path = bgDescription.substring(6, length - 1);
            this.isImage = true;
            img = General.loadImage(path);
        }
        // case color
        if (bgDescription.contains("color")) {
            ColorsParser cp = new ColorsParser();
            this.bgColor = cp.colorFromString(bgDescription);
            this.isColor = true;
        }

    }

    @Override
    public void drawOn(DrawSurface d) {
        if (this.isImage) {
            // Draw the image on a DrawSurface
            d.drawImage(10, 25, img); // draw the image at location 10, 20.
        }
        if (this.isColor) {
            d.setColor(this.bgColor);
            d.fillRectangle(25, 50, 800, 600);
        }
    }

    @Override
    public void timePassed() {

    }


}
