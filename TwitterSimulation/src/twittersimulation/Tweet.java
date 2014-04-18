package twittersimulation;

/**
 * Representation of a Tweet object. Mostly a container class.
 * @author Cody Reibsome
 * @date 03/08/14
 */
public class Tweet {

    private final int tweeterID, tweeteeID;
    private final double time;
    
    /**
     * Constructs a new Tweet object.
     * @param tweeterID Unique ID of user sending the tweet
     * @param tweeteeID Unique ID of user receiving the tweet
     * @param time time that the tweet was sent
     */
    public Tweet(int tweeterID, int tweeteeID, double time) {
        this.tweeterID = tweeterID;
        this.tweeteeID = tweeteeID;
        this.time = time;
    }
    
    /**
     * Creates and returns String representation of the Tweet object
     * @return tweeterID, tweeteeID, and hourOfTweet as a CSV line
     */
    @Override
    public String toString() {
        return "" + tweeterID + "," + tweeteeID + "," + time + "\n";
    }
}
