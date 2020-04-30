package game;
import sprites.ColorsParser;
import java.awt.Color;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {


    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {

        // factory returning parameters
        BufferedReader blocksBufferedReader = new BufferedReader(reader);
        Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
        Map<String, BlockCreator> blockCreators = new HashMap<String, BlockCreator>();
        ColorsParser cp = new ColorsParser();

        // block definition parameters
        String symball = null;
        int height = -1;
        int heightDefault = -1;
        int width = -1;
        int widthDefault = -1;
        int hitPoints = -1;
        int hitPointsDefault = -1;
        Color strokeColor = null;
        Color strokeColorDefault = null;
        String imgPath = null;
        String imgPathDefault = null;
        Map<Integer, String> hitColors = new HashMap<Integer, String>();
        Map<Integer, String> hitColorsDefault = new HashMap<Integer, String>();


        try {
            String line;
            while ((line = blocksBufferedReader.readLine()) != null) {
                String[] splited = line.split(" ");
                // ignore empty lines and # lines
                if (line.contains("#") || line.isEmpty()) {
                    continue;
                }
                if (splited[0].equals("default") || splited[0].equals("bdef")) {
                    for (int i = 0; i < splited.length; i++) {
                        String[] property = splited[i].split(":");
                        if (property[0].equals("symbol")) {
                            symball = property[1];
                        }
                        if (property[0].equals("height")) {
                            height = Integer.parseInt(property[1]);
                            if (splited[0].equals("default")) {
                                heightDefault = Integer.parseInt(property[1]);
                            }
                        }
                        if (property[0].equals("width")) {
                            width = Integer.parseInt(property[1]);
                            if (splited[0].equals("default")) {
                                widthDefault = Integer.parseInt(property[1]);
                            }
                        }
                        if (property[0].equals("hit_points")) {
                            hitPoints = Integer.parseInt(property[1]);
                            if (splited[0].equals("default")) {
                                hitPointsDefault = Integer.parseInt(property[1]);
                            }
                        }
                        if (property[0].equals("fill")) {
                            if (property[1].contains("image")) {
                                int length = property[1].length();
                                imgPath = property[1].substring(6, length - 1);
                                hitColors.put(1, property[1].substring(6, length - 1));
                                if (splited[0].equals("default")) {
                                    imgPathDefault = property[1].substring(6, length - 1);
                                    hitColorsDefault.put(1, property[1].substring(6, length - 1));
                                }
                            }
                            // case color
                            if (property[1].contains("color")) {
                                hitColors.put(1, property[1]);
                                if (splited[0].equals("default")) {
                                    hitColorsDefault.put(1, property[1]);
                                }
                            }
                        }
                        if (property[0].contains("fill-")) {
                            int hitNum = Integer.parseInt(String.valueOf(property[0].charAt(5)));
                            if (property[1].contains("image")) {
                                int length = property[1].length();
                                hitColors.put(hitNum, property[1].substring(6, length - 1));
                                if (splited[0].equals("default")) {
                                    hitColorsDefault.put(hitNum, property[1].substring(6, length - 1));
                                }
                            }
                            // case color
                            if (property[1].contains("color")) {
                                hitColors.put(hitNum, property[1]);
                                if (splited[0].equals("default")) {
                                    hitColorsDefault.put(hitNum, property[1]);
                                }
                            }
                        }
                        if (property[0].equals("stroke")) {
                            strokeColor = cp.colorFromString(property[1]);
                            if (splited[0].equals("default")) {
                                strokeColorDefault = cp.colorFromString(property[1]);
                            }
                        }
                    }
                    if (symball != null) {
                        blockCreators.put(symball, new BlockCreation(symball, height, width,
                                hitPoints, strokeColor, imgPath, hitColors));
                        symball = null;
                        height = heightDefault;
                        width = widthDefault;
                        hitPoints = hitPointsDefault;
                        strokeColor = strokeColorDefault;
                        imgPath = imgPathDefault;
                        hitColors = new HashMap<Integer, String>();
                        if (!hitColorsDefault.isEmpty()) {
                            hitColors.putAll(hitColorsDefault);
                        }
                    }
                }
                // read the spacers
                if (splited[0].equals("sdef")) {
                    for (int i = 0; i < splited.length; i++) {
                        String[] property = splited[i].split(":");
                        if (property[0].equals("symbol")) {
                            symball = property[1];
                        }
                        if (property[0].equals("width")) {
                            width = Integer.parseInt(property[1]);
                        }
                        if (symball != null && width != -1) {
                            spacerWidths.put(symball, width);
                        }
                    }
                }
            }
            // finish reading the txt file and returning the blocks factory
            return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
