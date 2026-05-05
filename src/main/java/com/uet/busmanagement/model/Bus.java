package com.uet.busmanagement.model;


public class Bus {

    private int busid;
    private String busnumber;
    private String busname;

    private int totalseats;
    private int bookedseats;

    private String routeid;
    private String driverid;

    private String currentlocation;
    private boolean active;

    public Bus(int busid, String busnumber, String busname, //Constructor
               int totalseats, String routeid, String driverid)
    {

        this.busid = busid;
        this.busnumber = busnumber;
        this.busname = busname;
        this.totalseats = totalseats;
        this.routeid = routeid;
        this.driverid = driverid;
        this.bookedseats = 0;
        this.currentlocation = "Not Assigned";
        this.active = true;
    }

    public void bookSeat()
    {
        if (bookedseats < totalseats)
        {
            bookedseats++;
        }
        else
        {
            IO.println("Sorry! No Available Seats");
        }
    }

    public int getAvailableSeats()
    {
        return totalseats - bookedseats;
    }


    public void updateLocation(String location)
    {
        this.currentlocation = location;
    }

    public int getBusid()
    {
        return busid;
    }

    public String getBusName()
    {
        return busname;
    }

    public String getBusNumber()
    {
        return busnumber;
    }
}
