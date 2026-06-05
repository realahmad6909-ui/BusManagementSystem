package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.repository.BusRepository;
import com.uet.busmanagement.util.LogManager; // Import kiya
import java.util.List;

public class BusService
{

    private BusRepository busRepository;

    public BusService()
    {
        this.busRepository = new BusRepository();
    }

    public String addBus(Bus bus)
    {
        boolean isSaved = busRepository.save(bus);

        if (isSaved)
        {
            LogManager.log("BUS_MANAGEMENT: Bus " + bus.getBusNumber() + " added successfully with status: " + bus.getStatus());
            return "Bus " + bus.getBusNumber() + " added successfully with status: " + bus.getStatus() + "!";
        }
        else
        {
            LogManager.log("BUS_MANAGEMENT_ERROR: Failed to add bus: " + bus.getBusNumber());
            return "Failed to add bus.";
        }
    }

    public List<Bus> getActiveBuses()
    {
        return busRepository.findActiveBuses();
    }
    public String deleteBus(String busNumber) {
        boolean isDeleted = busRepository.delete(busNumber);

        if (isDeleted) {
            LogManager.log("BUS_MANAGEMENT: Bus " + busNumber + " deleted.");
            return "Bus " + busNumber + " deleted successfully.";
        }
        return "Failed to delete bus. It might be currently assigned.";
    }
}