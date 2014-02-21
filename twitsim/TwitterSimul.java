/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import sim.engine.*;
import java.util.HashMap;
        
/**
 * Container class for Twitter social network simulation.
 * @author Cody Reibsome (creibsom@mail.umw.edu)
 * @date 02/21/2014
 */
public class TwitterSimul extends SimState {
    
    HashMap<Integer, User> users = new HashMap();
    final static int NUM_USERS = 300;
    
    public TwitterSimul(long seed) {
        super(seed);
        for(int i = 0; i < NUM_USERS; i++) {
            users.put(i, new User(i, findFreq(), findNumFollowees()));
        }
    }
    
    /**
     * Returns a 'frequency of tweeting' for a user, drawn from an empirical distribution.
     * @return A float representing the frequency at which a user tweets.
     */
    private float findFreq() {
        //Draw from empirical distribution
        return 0;
    }
    
    /**
     * Returns a number of followees for a user, drawn from an empirical distribution.
     * @return An int representing the number of followees the user will have.
     */
    private int findNumFollowees() {
        //Draw from empirical distribution
        return 0;
    }
    
    public static void main(String[] args) {
    }
}
