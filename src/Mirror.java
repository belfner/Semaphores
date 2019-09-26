import java.util.Random;

public class Mirror
{
    private String name;
    private String data;
    private Random r = new Random();

    //Mirror object just contains its name and the data to be downloaded
    public Mirror(String name, String data)
    {
        this.name = name;
        this.data = data;
    }

    //returns the data
    public String download()
    {
        //sleep simulates download time
        try
        {
            Thread.sleep(r.nextInt(7000) + 500);
        } catch (InterruptedException e)
        {
            System.out.println(e);
        }
        return data;
    }

    public String getName()
    {
        return name;
    }
}
