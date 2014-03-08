/**
 * Representation of a Tweet object. Mostly a container class.
 * @author Cody Reibsome
 * @date 03/08/14
 */
public class Tweet {

    private final int tweeterID, tweeteeID;
    private final int hourOfTweet;
    
    /**
     * Constructs a new Tweet object.
     * @param tweeterID Unique ID of user sending the tweet
     * @param tweeteeID Unique ID of user receiving the tweet
     * @param hourOfTweet Hour that the tweet was sent
     */
    public Tweet(int tweeterID, int tweeteeID, int hourOfTweet) {
        this.tweeterID = tweeterID;
        this.tweeteeID = tweeteeID;
        this.hourOfTweet = hourOfTweet;
    }
    
    /**
     * Creates and returns String representation of the Tweet object
     * @return tweeterID, tweeteeID, and hourOfTweet as a CSV line
     */
    @Override
    public String toString() {
        return "" + tweeterID + "," + tweeteeID + "," + hourOfTweet + "\n";
    }
}
