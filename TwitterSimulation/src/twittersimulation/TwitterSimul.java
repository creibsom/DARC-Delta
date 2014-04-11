package twittersimulation;

import ec.util.MersenneTwisterFast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import sim.engine.*;
import java.util.HashMap;
import sim.util.distribution.Exponential;
        
/**
 * Container class for Twitter social network simulation.
 * @author Cody Reibsome (creibsom@mail.umw.edu)
 * @date 03/08/2014
 */
public class TwitterSimul extends SimState {
    
    private final static HashMap<Integer, User> users = new HashMap();
    public final static int NUM_USERS = 35000;
    private final MersenneTwisterFast twist = new MersenneTwisterFast();
    private final Exponential exp = new Exponential(0, twist);
    private final ArrayList<Double> frequencyDist = new ArrayList();
    private final ArrayList<Integer> tweetablesDist = new ArrayList();
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
        loadNumTweetablesFile();
        User u;
        System.out.print("Initializing users...");
        for(int i = 0; i < NUM_USERS; i++) {
            u = new User(i, findFreq(), findNumTweetables(), twist, exp);
            users.put(i, u);
            schedule.scheduleOnce(Math.abs(exp.nextDouble(u.getFreq())), u);
//            if(i % (NUM_USERS/100) == 0)
//                System.out.print(".");
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
            br = new BufferedReader(new FileReader("tweetsPerWeekDist.csv"));
            String line;
            String[] temp;
            br.readLine(); //Skip first line
            while((line = br.readLine()) != null) {
                temp = line.split(",");
                frequencyDist.add(((double) Double.parseDouble(temp[1]))); 
            }
        } catch(FileNotFoundException e) {
            System.err.println("File tweetsPerWeekDist.csv not found.");
        } catch(IOException e) {
            System.err.println("IOException - reading tweetsPerWeekDist.csv");
        } finally {
            if(br != null) {
                try {
                    br.close();
		} catch (IOException e) {
                    System.err.println("IOException - closing tweetsPerWeekDist.csv");
                }
            }
        }
    }
    
    /**
     * Loads empirical tweetable counts into an ArrayList (followeesDist)
     * from friendDist.csv
     */
    private void loadNumTweetablesFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("potentialTweeteeDist.csv"));
            String line;
            String[] temp;
            int val;
            br.readLine(); //Skip first line
            while((line = br.readLine()) != null) {
                temp = line.split(",");
                val = Integer.parseInt(temp[1]);
                if(val <= NUM_USERS)
                    tweetablesDist.add(val); 
            }
        } catch(FileNotFoundException e) {
            System.err.println("File potentialTweeteeDist.csv not found.");
        } catch(IOException e) {
            System.err.println("IOException - reading potentialTweeteeDist.csv");
        } finally {
            if(br != null) {
                try {
                    br.close();
		} catch (IOException e) {
                    System.err.println("IOException - closing potentialTweeteeDist.csv");
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
     * Returns a user's tweetable count, drawn from an empirical distribution.
     * @return An int representing the number of tweetabless the user will have.
     */
    private int findNumTweetables() {
        int index = Math.abs(twist.nextInt() % tweetablesDist.size());
        while(tweetablesDist.get(index) > NUM_USERS) {
            index = Math.abs(twist.nextInt() % tweetablesDist.size());
        }
        return tweetablesDist.get(index);
    }
    
    /**
     * Accessor method to add a tweet to ArrayList tweets.
     * @param tw
     */
    public void catchTweet(Tweet tw) {
        tweets.add(tw);
    }
    
    public static void main(String[] args) {
        String timestamp = new SimpleDateFormat("MM-dd-yyyy_hh-mm a ").format(new Date());
        String[] flags = {"-until", "168"};
        doLoop(TwitterSimul.class, flags);

//        System.out.print("Outputting to " + timestamp + "relations.csv");
        //Print results to files.
        try {
//            File file = new File(timestamp + "relations.csv");
//            //Ensure the file is empty before appending to it.
//            if (file.exists())
//                file.delete();
//            file.createNewFile();
//	    BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
//            //Print all relationships in csv style
//	    for(int a = 0; a < TwitterSimul.NUM_USERS; a++) {
//                    bw.write(users.get(a).toString());
//                    if(a % 100 == 0)
//                        System.out.print(".");
//            }
            
            System.out.print("\nOutputting to " + timestamp + "tweets.csv");
            File file = new File(timestamp + "tweets.csv");
            //Ensure the file is empty before appending to it.
            if (file.exists())
                file.delete();
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
            //Print all tweets in csv style
            for(int a = 0; a < tweets.size(); a++) {
                bw.write(tweets.get(a).toString());
                if(a % (tweets.size()/100) == 0) 
                    System.out.print(".");
            }
	    bw.close();
 	} catch (IOException e) {
        	System.err.println("IOException - writing to file");
	}
        System.out.println("\n" + timestamp + "tweets.csv produced.");
        System.exit(0);
    }
}
