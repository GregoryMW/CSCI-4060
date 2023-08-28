package edu.uga.cs.statecapitalquiz;

/**
 *  Domain class (POJO) representing the score of a quiz.
 */
public class Scores {

    private long id;
    private String score;
    private String dateTime;

    /**
     * Constructor for each quiz.
     */
    public Scores() {
        this.id = -1;
        this.score = null;
        this.dateTime = null;
    }

    /**
     * Constructor for initializing the ID, score, and date time of each quiz.
     */
    public Scores(String score, String dateTime) {
        this.id = -1;
        this.score = score;
        this.dateTime = dateTime;
    }

    /**
     * Gets the ID of a quiz.
     * @return id of the quiz.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of a quiz
     * @param id identifies a particular quiz.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the score of a quiz.
     * @return score of a quiz.
     */
    public String getScore() {
        return score;
    }

    /**
     * Gets the date and time of a quiz taken.
     * @return Date and time of a quiz taken.
     */
    public String getDateTime() {
        return dateTime;
    }
}
