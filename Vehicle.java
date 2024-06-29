public class Vehicle {
    public String type; // Self - vallet;
    public String parktype; // Regular -- Premium
    public char floor;
    public int slot;
    public long entryTime;
    public long exitTime;
    public String vname;
    public String service; // check for premium feature
    Vehicle(String vname,String parktype,String type,char floor, int slot, long entryTime)
    {
        this.vname=vname;
        this.type=type;
        this.parktype=parktype;
        this.floor=floor;
        this.slot=slot;
        this.entryTime=entryTime;
        this.exitTime=0;
    }
}
