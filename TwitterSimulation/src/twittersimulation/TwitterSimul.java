package twittersimulation;

import ec.util.MersenneTwisterFast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import sim.engine.*;
import java.util.HashMap;
        
/**
 * Container class for Twitter social network simulation.
 * @author Cody Reibsome (creibsom@mail.umw.edu)
 * @date 03/08/2014
 */
public class TwitterSimul extends SimState {
    
    private final static HashMap<Integer, User> users = new HashMap();
    public final static int NUM_USERS = 260000;
    private final MersenneTwisterFast twist = new MersenneTwisterFast();
    private final ArrayList<Double> frequencyDist = new ArrayList();
    private final ArrayList<Integer> followeesDist = new ArrayList();
    private final static ArrayList<Tweet> tweets = new ArrayList();
    
    /**
     * 
     * @param seed 
     */
    public TwitterSimul(long seed) {
        super(seed);
    }
    
    @Override
    public void start() {
        super.start();
        loadFreqFile();
        loadNumFolloweesFile();
        User u;
        System.out.println("Initializing users...");
        for(int i = 0; i < NUM_USERS; i++) {
            u = new User(i, findFreq(), findNumFollowees(), twist);
            users.put(i, u);
            schedule.scheduleRepeating(u);
            System.out.println("User " + i + " scheduled.");
        }
    }
    
    /**
     * Loads empirical frequencies into  an ArrayList (frequencyDist)
     * from degreeDist.csv
     */
    private void loadFreqFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("degreeDist.csv"));
            String line;
            String[] temp;
            br.readLine(); //Skip first line
            while((line = br.readLine()) != null) {
                temp = line.split(",");
                // 168 is the number of hours in a week
                frequencyDist.add(((double) Double.parseDouble(temp[2])) / 168); 
            }
        } catch(FileNotFoundException e) {
            System.err.println("File degreeDist.csv not found.");
        } catch(IOException e) {
            System.err.println("IOException - reading degreeDist.csv");
        } finally {
            if(br != null) {
                try {
                    br.close();
		} catch (IOException e) {
                    System.err.println("IOException - closing degreeDist.csv");
                }
            }
        }
    }
    
    /**
     * Loads empirical followee counts into an ArrayList (followeesDist)
     * from friendDist.csv
     */
    private void loadNumFolloweesFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("friendDist.csv"));
            String line;
            String[] temp;
            br.readLine(); //Skip first line
            while((line = br.readLine()) != null) {
                temp = line.split(",");
                followeesDist.add(Integer.parseInt((temp[3].substring(1, temp[3].length() - 1)))); 
            }
        } catch(FileNotFoundException e) {
            System.err.println("File friendDist.csv not found.");
        } catch(IOException e) {
            System.err.println("IOException - reading friendDist.csv");
        } finally {
            if(br != null) {
                try {
                    br.close();
		} catch (IOException e) {
                    System.err.println("IOException - closing friendDist.csv");
                }
            }
        }
    } 
    
    /**
     * Returns a user's tweet frequency, drawn from an empirical distribution.
     * @return A double representing the frequency at which a user tweets.
     */
    private double findFreq() {
        int index = Math.abs(twist.nextInt() % frequencyDist.size());
        return frequencyDist.get(index);
    }
    
    /**
     * Returns a user's followee count, drawn from an empirical distribution.
     * @return An int representing the number of followees the user will have.
     */
    private int findNumFollowees() {
        int index = Math.abs(twist.nextInt() % followeesDist.size());
        return followeesDist.get(index);
    }
    
    /**
     * Accessor method to add a tweet to ArrayList tweets.
     * @param tw
     */
    public void catchTweet(Tweet tw) {
        tweets.add(tw);
    }
    
    public static void main(String[] args) {
        doLoop(TwitterSimul.class, args);
        String relationshipContent = "";
        for(int a = 0; a < TwitterSimul.NUM_USERS; a++) {
            relationshipContent += users.get(a).toString();
        }
        String tweetContent = "";
        for(int a = 0; a < tweets.size(); a++) {
            tweetContent += tweets.get(a).toString();
        }
        try {
            File file = new File("relations.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
	    BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
	    bw.write(relationshipContent);
            
            file = new File("tweets.csv");
            bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            bw.write(tweetContent);
	    bw.close();
 	} catch (IOException e) {
        	System.err.println("IOException - writing to relations.csv");
	}
        System.out.println("relations.csv and tweets.csv produced.");
        System.exit(0);
    }
}
