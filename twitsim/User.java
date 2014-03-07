import java.util.ArrayList;
import ec.util.MersenneTwisterFast;

/**
 * Object representing a Twitter user. Has the capability to send and receive Tweet objects.
 * @author Cody Reibsome (creibsom@mail.umw.edu)
 * @date 03/07/2014
 */
public class User {
    
    private int id;
    private double freq;
    private ArrayList<Integer> followees = new ArrayList();
    private MersenneTwisterFast rn;
    
    /**
     * Constructs a User object.
     * @param id Unique user ID
     * @param freq Frequency that the user sends out tweets
     * @param numFollowees Number of followees the user has
     * @param rn Random number generator for picking followees
    */
    public User(int id, double freq, int numFollowees, MersenneTwisterFast rn) {
        //Store params into class vars
        this.id = id;
        this.freq = freq;
        this.rn = rn;
        
        //Generate a list of followees and add them to the ArrayList
        int temp;
        for(int i = 0; i < numFollowees; i++) {
           temp = this.rn.nextInt() % TwitterSimul.NUM_USERS;
           while(followees.contains(temp)) {
               temp = this.rn.nextInt() % TwitterSimul.NUM_USERS;
           }
           followees.add(temp);
        }
    }
    
    /**
     * 
     * @param userIDTo
     * @param hour
     * @return 
     */
    public Tweet sendTweet(int userIDTo, int hour) {
        return new Tweet(id, userIDTo, hour);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        String out = "";
        for(int i = 0; i < followees.size(); i++) {
            out += id + "," + followees.get(i) + "\n";
        }
        return out;
    }
}
