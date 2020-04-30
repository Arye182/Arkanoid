package game;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level set reader.
 */
public class LevelSetReader {
    private String levelSetPath;
    private List<LevelSet> levelSets;
    private int countTwoLines = 2;

    /**
     * Instantiates a new Level set reader.
     *
     * @param path the path
     */
    public LevelSetReader(String path) {
        this.levelSetPath = path;
        this.levelSets = new ArrayList<LevelSet>();

    }

    /**
     * From reader list.
     *
     * @return the list
     */
    public List<LevelSet> fromReader() {

        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetPath);
            LineNumberReader lineReader = new LineNumberReader(new InputStreamReader(is));
            String line;
            LevelSet current = null;

            while (true) {
                line = lineReader.readLine();
                if (countTwoLines == 2) {
                    if (current != null) {
                        levelSets.add(current);
                    }
                    current = new LevelSet();
                    countTwoLines = 0;
                }
                if (line == null) {
                    break;
                }
                if (lineReader.getLineNumber() % 2 == 0) {
                    current.setPath(line.trim());
                    ++countTwoLines;
                } else {
                    String[] lineParts = line.trim().split(":");
                    current.setKey(lineParts[0]);
                    current.setMessage(lineParts[1]);
                    ++countTwoLines;
                }
            }
            is.close();
            return levelSets;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
