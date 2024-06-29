import java.util.*;
import java.io.*;
public class parkDB 
{
    private int[][] avialable; /// 0-A 1-B 2-C ////---> last 1 / 10 indexes are allocated to premium
    public int time;
    HashMap<String,Vehicle> hm;
    // public Vehicle v;
    parkDB()  throws IOException
    {
        this.time=0; //
        this.hm=new HashMap<>(); /// read the previous input data
        this.avialable=new int[3][10];
        readPreviousRecord();
        startTimer();
    }
    private void startTimer()
    {
        Thread t = new Thread(()->{
            try{
                while(true)
                {
                    Thread.sleep(1001); // 1 Sec --> 1 hour
                    this.time+=1;
                }
            }
            catch(Exception e){e.printStackTrace();}
        });
        t.start();
    } 
    public long getTime()
    {
        return this.time;
    }
    // release the slot
    public void release(Vehicle v)
    {
        int floor=(int)(v.floor-'A');
        int slot=v.slot;
        avialable[floor][slot]=0;
        hm.remove(v);
    }
    // last 1 / 10 indexes are allocated to premium
    public int[] check(boolean isPremium)
    {
        if(isPremium)
        {
            for(int i=0;i<3;i++)
            {
                if(avialable[i][0]==0)
                {
                    return new int[]{i,0};
                }
            }
        }
        else // regualar
        {
            for(int i=0;i<3;i++)
            {
                for(int j=1;j<10;j++) // regular starts from 1 to 10
                {
                    if(avialable[i][j]==0)
                    {
                        return new int[]{i,j};
                    }
                }
            }
        }
        return new int[]{-1,-1};
    }
    public boolean allocate(String vname,String type,String parktype)  throws IOException // true -- alllocated || object creation
    {
        if(parktype=="Premium")
        {
            int currAvail[]=check(true);
            if(currAvail[0]!=-1 && currAvail[1]!=-1)
            {
                char floor=(char)('A'+currAvail[0]);
                Vehicle currVehicle = new Vehicle(vname,parktype ,type, floor, currAvail[1], this.getTime());
                hm.put(vname,currVehicle);
                avialable[currAvail[0]][currAvail[1]]=1;
                writeRecord(currVehicle);
                return true;
            }
            else
            {
                return false;
            }
        }
        else{
            
            int currAvail[]=check(false);
            if(currAvail[0]!=-1 && currAvail[1]!=-1)
            {
                char floor=(char)('A'+currAvail[0]);
                Vehicle currVehicle = new Vehicle(vname,parktype,type, floor, currAvail[1], this.getTime());
                hm.put(vname,currVehicle);
                avialable[currAvail[0]][currAvail[1]]=1;
                writeRecord(currVehicle);
                return true; // success
            }
            else
            {
                return false;
            }
        }
    }
    public Vehicle getDetails(String vname) // to check whether it is in db
     {
        if(hm.containsKey(vname))
        {
            return hm.get(vname);
        }
        return null;
    }
    public boolean billing(String vname)
    {
        Vehicle curr= getDetails(vname);
        if(curr!=null)
        {
            System.out.println("Your Vehicle details: ");
            System.out.println("Vehicle Registration Number: "+curr.vname);
            System.out.println("Vehicle Service: "+curr.type);
            System.out.println("Vehicle Parking Type: "+curr.parktype);
            System.out.println("Vehicle has been parked in floor: "+curr.floor+" - slot "+curr.slot);
            System.out.println("Vehicle has been parked at: "+curr.entryTime);
            long exitTime=this.getTime();
            System.out.println("Vehicle exited at: "+exitTime);
            long period=(exitTime-curr.entryTime);
            long amount =0;
            if(curr.parktype=="Premium")
            {
                amount = 150*(period)+500;
            } 
            else{
                amount=100*period;
            }
            System.out.println("The total period is :"+period);
            System.out.println("Amount to be paid :"+amount);
            try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
            System.out.println("Successfull! Travel Safe!\n");
            release(curr);
            return true;
        }
        return false;
    }


    /// function to read the previous data --> Which is called only once
    private void readPreviousRecord() throws IOException
    {
        try{
            FileInputStream fi=new FileInputStream("storage.txt");
            ObjectInputStream ois=new ObjectInputStream(fi);
            Vehicle curr;
            while((curr= (Vehicle)ois.readObject())!=null)
            {
                // puting them into hashmap and allocating the data and rechecking the avilability
                int floor=(int)(curr.floor-'A');
                int slot=curr.slot;
                String vname=curr.vname;
                hm.put(vname,curr);
                avialable[floor][slot]=1;
            }
            ois.close();
            fi.close();
            // System.out.println(curr.vname);
        }
        catch(Exception e)
        {
            // e.printStackTrace();
        }
    }
    /// function to append the current vehicle to the file
    public void writeRecord(Vehicle v) throws IOException
    {
        try{
            FileOutputStream fo=new FileOutputStream("storage.txt",true);
            ObjectOutputStream oos=new ObjectOutputStream(fo);
            oos.writeObject(v);
            oos.close();
            fo.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}