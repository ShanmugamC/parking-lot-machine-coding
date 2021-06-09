package com.shan.parking.service;

import com.shan.parking.model.*;

import java.util.List;
import java.util.Map;

public interface ParkingService {
    public void registerAParkingLot(String name, Map<SlotType, List<Slot>> slots, Map<SlotType, Map<Integer, Integer>> rateCard);

    public boolean park(Vehicle vehicle, String parkingLotId);

    public Parking exit(Vehicle vehicle, String parkinglotId);

    public List<Parking> getHistory(String vehicleNumber);
}
