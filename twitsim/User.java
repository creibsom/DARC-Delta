/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import ec.util.MersenneTwisterFast;

/**
 *
 * @author Cody
 */
public class User {
    
    private int id;
    private float freq;
    private ArrayList<Integer> followees = new ArrayList();
    
    public User(int id, float freq, int numFollowees) {
        //Store params into class vars
        this.id = id;
        this.freq = freq;
        
        //Generate a list of followees and add them to the ArrayList
        MersenneTwisterFast rn = new MersenneTwisterFast();
        int temp;
        for(int i = 0; i < numFollowees; i++) {
           temp = rn.nextInt() % TwitterSimul.NUM_USERS;
           while(followees.contains(temp)) {
               temp = rn.nextInt() % TwitterSimul.NUM_USERS;
           }
           followees.add(temp);
        }
    }
}
