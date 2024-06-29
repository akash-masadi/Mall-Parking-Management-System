import java.time.LocalTime;
public class temp {
    static int time=0;
    public static void main(String[] args) {
        Character s='A'+1;
        Thread t = new Thread(()->{
            try{
                while(true)
                {
                    Thread.sleep(100);
                    time+=1;
                }
            }
            catch(Exception e){e.printStackTrace();}
        });
        t.start();
        int i=0;
        while(i++<100)
        {
            try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
            System.out.println(time);
        }
        System.out.println(s.toString());
    }
}
