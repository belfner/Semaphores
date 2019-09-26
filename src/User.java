import java.util.Random;
import java.util.concurrent.Semaphore;

public class User extends Thread
{
    private Semaphore sem;
    private Random r = new Random();
    private String threadName;
    private Website w;

    public User(Semaphore sem, Website w, String threadName)
    {
        super(threadName);
        this.sem = sem;
        this.w = w;
        this.threadName = threadName;
    }

    @Override
    public void run()
    {
        System.out.println("Starting user: " + threadName + " at " + Main.getTime());

        //sleep allows users calls to spread out
        try
        {
            sleep(r.nextInt(10000) + 500);
        } catch (InterruptedException e)
        {
            System.out.println(e);
        }

        try
        {
            System.out.println(threadName + " is waiting for a mirror." + " at " + Main.getTime());

            //Requests access to mirrors. If no mirrors available thread waits until another user releases their mirror
            sem.acquire();
            //once a mirror is available user grabs it
            Mirror m = w.getMirror();
            System.out.println(threadName + " gets mirror " + m.getName() + " at " + Main.getTime());

            //calls download on mirror to get data
            String download = m.download();
            System.out.println(threadName + " downloaded " + download + " at " + Main.getTime());

            //after data is downloaded, the user returns the mirror to the website
            w.returnMirror(m);
            System.out.println(threadName + " releases mirror " + m.getName() + " at " + Main.getTime());

        } catch (InterruptedException e)
        {
            System.out.println(e);
        }
        //notifies sem that user is done with mirror
        sem.release();
    }
}