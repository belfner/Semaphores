import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Website
{
    private int numberOfMirrors;
    private Semaphore sem;
    private ArrayList<Mirror> availableMirrors;
    private ArrayList<Mirror> mirrorsInUse;

    public Website(int numberOfMirrors, String data)
    {
        this.numberOfMirrors = numberOfMirrors;
        sem = new Semaphore(numberOfMirrors);
        availableMirrors = new ArrayList<>();
        mirrorsInUse = new ArrayList<>();


        //Create all mirror objects and add them to the list of available mirrors
        for (int i = 0; i < numberOfMirrors; i++)
        {
            availableMirrors.add(new Mirror(Integer.toString(i), data));
        }
    }

    //pops the first mirror from available mirrors adds it to the mirrors in ues list and returns it to the user
    public Mirror getMirror()
    {
        mirrorsInUse.add(availableMirrors.get(0));
        return availableMirrors.remove(0);
    }

    //users call this function to return mirrors when done
    //removes the mirror from the mirrors in use list and adds it to available mirrors
    public void returnMirror(Mirror m)
    {
        mirrorsInUse.remove(m);
        availableMirrors.add(m);
    }
}
