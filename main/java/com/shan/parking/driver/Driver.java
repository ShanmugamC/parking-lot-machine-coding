package com.shan.parking.driver;

import com.shan.parking.model.ParkingLot;
import com.shan.parking.model.Slot;
import com.shan.parking.model.SlotType;
import com.shan.parking.service.ParkingService;
import com.shan.parking.service.ParkingServiceImpl;

import java.util.*;

public class Driver {
    public static void main(String[] args) {
        ParkingService parkingService = new ParkingServiceImpl();
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId("A");
        SlotType slotType = SlotType.CAR;
        SlotType slotTypeBike = SlotType.BIKE;

        Map<Integer, Integer> carRateCard = new HashMap<>();
        carRateCard.put(2, 30);
        carRateCard.put(4, 70);
        carRateCard.put(24, 100);

        Map<Integer, Integer> bikeRateCard = new HashMap<>();
        bikeRateCard.put(2, 20);
        bikeRateCard.put(4, 50);
        bikeRateCard.put(24, 80);

        List<Slot> carParkingSlots = getSlots(50);
        List<Slot> bikeParkingSlots = getSlots(100);

       // TODO yet to finish test cases

    }

    private static List<Slot> getSlots(int numberOfSlots) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 1; i <= numberOfSlots; i++) {
            Slot slot = new Slot();
            slot.setId(UUID.randomUUID().toString());
            slot.setAvailable(true);
            slots.add(slot);
        }
        return slots;
    }
}
