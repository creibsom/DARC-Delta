package twittersimulation;

import java.util.ArrayList;
import ec.util.MersenneTwisterFast;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.distribution.Exponential;

/**
 * Object representing a Twitter user. Has the capability to send and receive Tweet objects.
 * @author Cody Reibsome (creibsom@mail.umw.edu)
 * @date 03/29/2014
 */
public class User implements Steppable {
    
    private final int id;
    private final double freq;
    private final ArrayList<Integer> tweetables = new ArrayList();
    private MersenneTwisterFast rn;
    private Exponential exp;
    
    /**
     * Constructs a User object.
     * @param id Unique user ID
     * @param freq Frequency that the user sends out tweets
     * @param numTweetables Number of users the user can tweet to
     * @param rn Random number generator for picking followees
     * @param exp Exponential distribution used to schedule tweeting
    */
    public User(int id, double freq, int numTweetables, MersenneTwisterFast rn, sim.util.distribution.Exponential exp) {
        //Store params into class vars
        this.id = id;
        this.freq = freq;
        this.rn = rn;
        this.exp = exp;
        
        //Generate a list of followees and add them to the ArrayList
        int temp;
        double alpha = 2.730885;
        for(int i = 0; i < numTweetables; i++) {
           temp = (int) Math.floor(sim.util.distribution.Distributions.nextPowLaw(alpha, TwitterSimul.NUM_USERS, this.rn));
           while(temp == id || tweetables.contains(temp)) {
               temp = (int) Math.floor(sim.util.distribution.Distributions.nextPowLaw(alpha, TwitterSimul.NUM_USERS, this.rn));
           }
           tweetables.add(temp);
        }
    }
    
    /**
     * 
     * @param state 
     */
    @Override
    public void step(SimState state) {
        TwitterSimul sim = (TwitterSimul) state;
        //Send a tweet to a uniform randomly generated user in tweetables
        //Schedule self to run again at a time determined by an exponential dist.
           if(tweetables.size() > 0) {
               int userToID = tweetables.get(Math.abs(rn.nextInt() % tweetables.size()));
               double time = sim.schedule.getTime();
               sim.catchTweet(sendTweet(userToID, time));
               sim.schedule.scheduleOnce(time + Math.abs(exp.nextDouble(freq)), this);
           } 
    }
    
    public double getFreq() {
        return freq;
    }
    
    /**
     * 
     * @param userIDTo
     * @param time
     * @return 
     */
    public Tweet sendTweet(int userIDTo, double time) {
        return new Tweet(id, userIDTo, time);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        String out = "";
        for(int i = 0; i < tweetables.size(); i++) {
            out += id + "," + tweetables.get(i) + "\n";
        }
        return out;
    }
}
