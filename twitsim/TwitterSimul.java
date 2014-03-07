import ec.util.MersenneTwisterFast;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import sim.engine.*;
import java.util.HashMap;
        
/**
 * Container class for Twitter social network simulation.
 * @author Cody Reibsome (creibsom@mail.umw.edu)
 * @date 02/21/2014
 */
public class TwitterSimul extends SimState {
    
    private final HashMap<Integer, User> users = new HashMap();
    public final static int NUM_USERS = 300;
    private final MersenneTwisterFast twist = new MersenneTwisterFast();
    private final ArrayList<Double> frequencyDist = new ArrayList();
    private final ArrayList<Integer> followeesDist = new ArrayList();
    
    public TwitterSimul(long seed) {
        super(seed);
        loadFreqFile();
        loadNumFolloweesFile();
        for(int i = 0; i < NUM_USERS; i++) {
            users.put(i, new User(i, findFreq(), findNumFollowees(), twist));
        }
    }
    
    private void loadFreqFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("degreeDist.csv"));
            String line = "";
            String[] temp;
            while((line = br.readLine()) != null) {
                temp = line.split(",");
                // 168 is the number of hours in a week
                frequencyDist.add(((double) Integer.parseInt(temp[2])) / 168); 
            }
        } 
        catch(FileNotFoundException e) {} 
        catch(IOException e) {}
        finally {
            if(br != null) {
                try {
                    br.close();
		} 
                catch (IOException e) {}
            }
        }
    }
    
    private void loadNumFolloweesFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("friendDist.csv"));
            String line = "";
            String[] temp;
            while((line = br.readLine()) != null) {
                temp = line.split(",");
                followeesDist.add(Integer.parseInt(temp[3])); 
            }
        } 
        catch(FileNotFoundException e) {} 
        catch(IOException e) {}
        finally {
            if(br != null) {
                try {
                    br.close();
		} 
                catch (IOException e) {}
            }
        }
    } 
    
    /**
     * Returns a 'frequency of tweeting' for a user, drawn from an empirical distribution.
     * @return A double representing the frequency at which a user tweets.
     */
    private double findFreq() {
        int index = twist.nextInt() % frequencyDist.size();
        return frequencyDist.get(index);
    }
    
    /**
     * Returns a number of followees for a user, drawn from an empirical distribution.
     * @return An int representing the number of followees the user will have.
     */
    private int findNumFollowees() {
        int index = twist.nextInt() % followeesDist.size();
        return followeesDist.get(index);
    }
    
    public static void main(String[] args) {
    }
}
