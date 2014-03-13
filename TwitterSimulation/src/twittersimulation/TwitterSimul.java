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
    public final static int NUM_USERS = 35000;
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
        System.out.print("Initializing users...");
        for(int i = 0; i < NUM_USERS; i++) {
            u = new User(i, findFreq(), findNumFollowees(), twist);
            users.put(i, u);
            schedule.scheduleRepeating(u);
            if(i % 1000 == 0)
                System.out.print(".");
        }
        System.out.print("\nSimulating");
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
            int val;
            br.readLine(); //Skip first line
            while((line = br.readLine()) != null) {
                temp = line.split(",");
                val = Integer.parseInt(temp[3].substring(1, temp[3].length() - 1));
                if(val <= NUM_USERS)
                    followeesDist.add(val); 
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
        String[] flags = {"-until", "168"};
        doLoop(TwitterSimul.class, flags);

        System.out.print("Outputting to relations.csv");
        //Print results to files.
        try {
            File file = new File("relations.csv");
            //Ensure the file is empty before appending to it.
            if (file.exists())
                file.delete();
            file.createNewFile();
	    BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
            //Print all relationships in csv style
	    for(int a = 0; a < TwitterSimul.NUM_USERS; a++) {
                    bw.write(users.get(a).toString());
                    if(a % 100 == 0)
                        System.out.print(".");
            }
            
            System.out.print("\nOutputting to tweets.csv");
            file = new File("tweets.csv");
            //Ensure the file is empty before appending to it.
            if (file.exists())
                file.delete();
            file.createNewFile();
            bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
            //Print all tweets in csv style
            for(int a = 0; a < tweets.size(); a++) {
                bw.write(tweets.get(a).toString());
                if(a % 100 == 0) 
                    System.out.print(".");
            }
	    bw.close();
 	} catch (IOException e) {
        	System.err.println("IOException - writing to relations.csv");
	}
        System.out.println("\nrelations.csv and tweets.csv produced.");
        System.exit(0);
    }
}
