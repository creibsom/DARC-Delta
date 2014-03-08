package twittersimulation;

import java.util.ArrayList;
import ec.util.MersenneTwisterFast;
import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * Object representing a Twitter user. Has the capability to send and receive Tweet objects.
 * @author Cody Reibsome (creibsom@mail.umw.edu)
 * @date 03/08/2014
 */
public class User implements Steppable {
    
    private final int id;
    private final double freq;
    private final ArrayList<Integer> followees = new ArrayList();
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
           while(temp == id || followees.contains(temp)) {
               temp = this.rn.nextInt() % TwitterSimul.NUM_USERS;
           }
           followees.add(temp);
        }
    }
    
    /**
     * 
     * @param state 
     */
    @Override
    public void step(SimState state) {
        TwitterSimul sim = (TwitterSimul) state;
        //Generate a random boolean with probability equal to the user's tweet
        //frequency. If true, tweet to a random user. If not, do nothing.
           if(rn.nextBoolean(freq)) {
               int userToID = followees.get(rn.nextInt() % followees.size());
               int stepNum = (int) sim.schedule.getSteps();
               sim.catchTweet(sendTweet(userToID, stepNum));
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
