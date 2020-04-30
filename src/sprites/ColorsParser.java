package sprites;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Colors parser.
 */
public class ColorsParser {
    // map of colors
    private Map<String, Color> colorMap = new HashMap<>();
    private Color color;

    /**
     * Instantiates a new Colors parser.
     */
    public ColorsParser() {
        colorMap.put("black", Color.black);
        colorMap.put("blue", Color.blue);
        colorMap.put("cyan", Color.cyan);
        colorMap.put("gray", Color.gray);
        colorMap.put("lightGray", Color.lightGray);
        colorMap.put("green", Color.green);
        colorMap.put("orange", Color.orange);
        colorMap.put("pink", Color.pink);
        colorMap.put("red", Color.red);
        colorMap.put("white", Color.white);
        colorMap.put("yellow", Color.yellow);
    }

    /**
     * Color from string java . awt . color.
     *
     * @param s the s
     * @return the java . awt . color
     */
// parse color definition and return the specified color.
    public java.awt.Color colorFromString(String s) {
        String colorString;
        if (s.contains("stroke")) {
            colorString = s.substring(7, s.length() - 1);
        } else {
            colorString = s.substring(6, s.length() - 1);
        }
        if (colorMap.containsKey(colorString)) {
            color = colorMap.get(colorString);
        } else {
            String rgbString = colorString.substring(4, colorString.length() - 1);
            String[] rgb = rgbString.split(",");
            int r = Integer.parseInt(rgb[0]);
            int g = Integer.parseInt(rgb[1]);
            int b = Integer.parseInt(rgb[2]);
            color = new Color(r, g, b);
        }
        return color;
    }
}
