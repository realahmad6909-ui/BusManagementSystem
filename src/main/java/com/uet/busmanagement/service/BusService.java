package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.repository.BusRepository;
import java.util.List;

public class BusService
{

    private BusRepository BusRepository;

    public BusService()
    {
        this.BusRepository = new BusRepository();
    }

    // 1. Nayi Bus register karne ki logic
    public String addBus(Bus bus)
    {
        boolean isSaved = BusRepository.save(bus);
        if (isSaved)
        {
            return "Bus added successfully!";
        }
        else
        {
            return "Failed to add bus.";
        }
    }

    // 2. Bus ka Route update karne ki logic
    public String assignRouteToBus(String busNumber, String routeId)
    {
        boolean isUpdated = BusRepository.updateBusRoute(busNumber, routeId);
        if (isUpdated)
        {
            return "Route assigned to bus successfully!";
        }
        else
        {
            return "Failed to assign route. Please check bus number or route ID.";
        }
    }

    // 3. Saari active buses dekhne ke liye
    public List<Bus> getAllBuses()
    {
        return BusRepository.findAll();
    }

    // 4. Check karne ke liye ke bus mien jagah khali hai ya nahi
    public boolean hasAvailableSeats(Bus bus, int currentBookingsCount)
    {
        int totalCapacity = bus.getSeatingCapacity() + bus.getStandingCapacity();
        return currentBookingsCount < totalCapacity;
    }
}
