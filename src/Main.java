import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main
{
    //setup time and format for getTime function
    public static long startTime;
    public static DecimalFormat df = new DecimalFormat("#.###");


    public static void main(String args[]) throws InterruptedException
    {
        df.setRoundingMode(RoundingMode.HALF_UP); //for getTime

        int numberOfMirrors = 3; //number of download servers available
        int numberOfUsers = 10; //number of users who will download a file

        //create Website object and set the data to be downloaded to "Hello World"
        Website w = new Website(numberOfMirrors, "Hello World");

        //Create the semaphore that will control access to the download mirrors. Fair is set to true so first calls are answered first
        Semaphore sem = new Semaphore(numberOfMirrors, true);

        //Create all Users
        User[] users = new User[numberOfUsers];
        for (int x = 0; x < numberOfUsers; x++)
        {
            users[x] = new User(sem, w, randomString(4));
        }
        startTime = System.currentTimeMillis();
        //Start all users
        for (int x = 0; x < numberOfUsers; x++)
        {
            users[x].start();
        }

        //Wait for all users to finish
        for (int x = 0; x < numberOfUsers; x++)
        {
            users[x].join();
        }

    }

    //generate random string for the users name
    //derived from https://www.baeldung.com/java-random-string example 1
    public static String randomString(int len)
    {
        Random r = new Random();
        byte[] array = new byte[len];
        for (int x = 0; x < len; x++)
        {
            array[x] = (byte) (97 + r.nextInt(26)); //UTF-8 chars 97-122 are the lowercase alphabet
        }
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString.substring(0, 1).toUpperCase() + generatedString.substring(1);
    }

    //returns time elapsed since program start in a formatted string
    public static String getTime()
    {
        return "Time: " + df.format((double) (System.currentTimeMillis() - startTime) / 1000.0d)+" s.";
    }

}