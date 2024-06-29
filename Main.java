import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static parkDB db;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        db=new parkDB();
        while(true)
        {
            System.out.println("\n\n\n______ PMS_______\n");
            System.out.println("Enter the vehicle registration number : ");
            String vname=sc.next();
            System.out.println("Choose the Service:\n");
            System.out.println("\n1. Entry\n2. Exit");
            int choice=sc.nextInt();
            if(choice==1)
            {
                System.out.println("Choose the type of parking : \n1.Self Parking\n2.Vallet Parking\n");
                int type=sc.nextInt();
                /// if vallet -> we need to do the parking
                /// else he will choose the slot
                if(type==2)
                {
                    getVallet(vname);
                }
                else
                {
                    getSelf(vname);
                }
                
            }
            else{
                boolean done =db.billing(vname);
                if(!done)
                {
                    System.out.println("Your vehicle is not in the database!");
                }
            }
            try{Thread.sleep(2000);}catch(Exception e){e.printStackTrace();}
        }
    }
    public static void getVallet(String vname)
    {
        System.out.println("Choose the type of parking-service : \n1.Regular\n2.Premium\n");
        int parktype=sc.nextInt();
        boolean isParked=false;
        if(parktype==1)
        {
            isParked=db.allocate(vname,"Vallet","Regular"); //vname , 
        }
        else
        {
            isParked=db.allocate(vname,"Vallet","Premium");
        }
        if(isParked)
        {
            try{Thread.sleep(2000);}catch(Exception e){e.printStackTrace();}
            Vehicle curr=db.getDetails(vname);
            System.out.println("Your vehicle has been parked in floor "+curr.floor+" in "+curr.slot);
        }
        else
        {
            System.out.println("Oops! No Parking slots are empty");
        }
        
    }
    public static void getSelf(String vname)
    {
        boolean isParked=false;
        isParked=db.allocate(vname,"Self","Regular");
        if(isParked)
        {
            Vehicle curr=db.getDetails(vname); // what if it is NULL
            try{Thread.sleep(2000);}catch(Exception e){e.printStackTrace();}
            System.out.println("Please park your vehicle in floor "+curr.floor+" in "+curr.slot);
        }
        else
        {
            System.out.println("Oops! No Parking slots are empty");
        }
        
    }
    
}
/*
 * User
 * He  will see the menu  ->Entry | Exit
 * Entry -> Self | Walet -> (Premium | regular) for each entry
 * Exit -> Do billing in the backend
 * Returm to the main menu
 */