import java.util.ArrayList;
import ec.util.MersenneTwisterFast;

/**
 * Object representing a Twitter user. Has the capability to send and receive tweets.
 * @author Cody Reibsome (creibsom@mail.umw.edu)
 */
public class User {
    
    private int id;
    private float freq;
    private ArrayList<Integer> followees = new ArrayList();
    private MersenneTwisterFast rn;
    
    /**
     * 
     * @param id
     * @param freq
     * @param numFollowees
     * @param rn
    */
    public User(int id, float freq, int numFollowees, MersenneTwisterFast rn) {
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
}
