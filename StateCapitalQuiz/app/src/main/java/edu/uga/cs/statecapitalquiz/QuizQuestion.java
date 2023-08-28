package edu.uga.cs.statecapitalquiz;

/**
 * Domain class (POJO) representing a quiz question.
 */
public class QuizQuestion {

    private long id;
    private String state;
    private String capital;
    private String secondChoice;
    private String thirdChoice;
    private String statehood;
    private String since;
    private String rank;

    /**
     * Constructor for a quiz question.
     */
    public QuizQuestion() {
        this.id = -1;
        this.state = null;
        this.capital = null;
        this.secondChoice = null;
        this.thirdChoice = null;
        this.statehood = null;
        this.since = null;
        this.rank = null;
    }

    /**
     * Constructor initializing the state, capital, second choice, third choice,
     * statehood, since, and rank of a quiz question.
     * @param state the assigned state for a quiz question.
     * @param capital the capital of the assigned state for a quiz question.
     * @param secondChoice the second answer choice of the assigned state for a quiz question.
     * @param thirdChoice the third answer choice of the assigned state for a quiz question.
     * @param statehood the statehood of the assigned state for a quiz question.
     * @param since the since of the assigned state for a quiz question.
     * @param rank the rank of the assigned state for a quiz question.
     */
    public QuizQuestion(String state, String capital, String secondChoice, String thirdChoice, String statehood, String since, String rank) {
        this.id = -1;
        this.state = state;
        this.capital = capital;
        this.secondChoice = secondChoice;
        this.thirdChoice = thirdChoice;
        this.statehood = statehood;
        this.since = since;
        this.rank = rank;
    }

    /**
     * Gets a state to assign to a quiz question.
     * @return a state for a quiz question.
     */
    public String getState() {
        return state;
    }

    /**
     * Gets the ID of a quiz question.
     * @return ID of a quiz question.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of a quiz question.
     * @param id identifies a quiz question.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Sets the state to a quiz question.
     * @param state assigned to a quiz question.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the capital of the assigned state for each quiz question.
     * @return capital of the assigned state.
     */
    public String getCapital() {
        return capital;
    }

    /**
     * Gets the second choice (another city) of a state.
     * @return second choice of a state.
     */
    public String getSecondChoice() {
        return secondChoice;
    }

    /**
     * Gets the third choice (another city) of a state.
     * @return third choice of a state.
     */
    public String getThirdChoice() {
        return thirdChoice;
    }

    /**
     * Gets the statehood of a state.
     * @return statehood of a state.
     */
    public String getStatehood() {
        return statehood;
    }

    /**
     * Gets the since date of a state.
     * @return since date of a state.
     */
    public String getSince() {
        return since;
    }

    /**
     * Gets the rank of a state.
     * @return rank of a state.
     */
    public String getRank() {
        return rank;
    }
}