package game;
import java.io.IOException;
import java.io.File;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private int size;
    private List<ScoreInfo> highScores;

    /**
     * Instantiates a new High scores table.
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the size
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.highScores = new ArrayList<ScoreInfo>(size);
//        for (int i = 0; i < size; i++){
//            highScores.add(null);
//        }
    }

    /**
     * Add.
     * Add a high-score.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        this.highScores.add(score);
        Collections.sort(this.highScores, new Comparator<ScoreInfo>() {
            @Override
            public int compare(ScoreInfo o1, ScoreInfo o2) {
                return o2.getScore() - o1.getScore();
            }
        });
        if (this.highScores.size() > this.size) {
            highScores.remove(size);
        }
    }

    /**
     * Size int.
     * Return table size.
     *
     * @return the int
     */
    public int size() {
        return this.size;
    }

    /**
     * Gets high scores.
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * Gets rank.
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        // list is empty
        if (highScores.isEmpty()) {
            return 1;
        }
        // higher in the middle
        for (int i = 0; i < this.highScores.size(); i++) {
            if (score - highScores.get(i).getScore() >= 0) {
                return i + 1;
            }
        }
        // lowest in the list
        return this.size + 1;
    }

    /**
     * Clear.
     * Clears the table
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load.
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void load(File filename) throws IOException {
        this.clear();
        HighScoresTable hst = HighScoresTable.loadFromFile(filename);
        for (ScoreInfo score : hst.getHighScores()) {
            this.add(score);
        }
        this.size = hst.size();
    }

    /**
     * Save.
     * Save table data to the specified file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    /**
     * Load from file high scores table.
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the filename
     * @return the high scores table
     * @throws IOException the io exception
     */
    public static HighScoresTable loadFromFile(File filename) throws IOException {
        HighScoresTable newEmptyHst;
        try {
            FileInputStream fileIn = new FileInputStream(filename.getPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            HighScoresTable hst = (HighScoresTable) in.readObject();
            in.close();
            fileIn.close();
            return hst;
        } catch (IOException i) {
            newEmptyHst = new HighScoresTable(5);
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            throw new RuntimeException("Can't find class!", c);
        }
        return newEmptyHst;
    }
}
