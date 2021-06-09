package com.shan.parking.service;

import com.shan.parking.exception.ParkingLotAlreadyExistsException;
import com.shan.parking.exception.ParkingLotFullException;
import com.shan.parking.exception.ParkingLotNotRegisteredException;
import com.shan.parking.exception.VehicleNotFoundException;
import com.shan.parking.helper.ParkingHelper;
import com.shan.parking.model.*;

import java.util.*;

public class ParkingServiceImpl implements ParkingService {

    private List<ParkingLot> parkingLots;
    private List<Parking> parkings;

    public ParkingServiceImpl() {
        this.parkingLots = new ArrayList<>();
        this.parkings = new ArrayList<>();
    }

    public ParkingServiceImpl(List<ParkingLot> parkingLots, List<Parking> parkings) {
        this.parkingLots = parkingLots;
        this.parkings = parkings;
    }

    public void registerAParkingLot(String name, Map<SlotType, List<Slot>> slots, Map<SlotType, Map<Integer, Integer>> rateCard) {
        if (parkingLots == null) {
            parkingLots = new ArrayList<>();
        }
        ParkingLot parkingLot = new ParkingLot(name, slots, rateCard);
        if (parkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyExistsException();
        }
        parkingLots.add(parkingLot);
    }

    public boolean park(Vehicle vehicle, String parkingLotId) {
        ParkingLot parkingLotToCheck = new ParkingLot(parkingLotId);
        if (!parkingLots.contains(parkingLotToCheck)) {
            throw new ParkingLotNotRegisteredException();
        }
        ParkingLot parkingLotToPark = getParkingLot(parkingLotId);
        SlotType slotType = getslotType(vehicle);

        if (isParkingLotFull(parkingLotToPark, slotType)) {
            throw new ParkingLotFullException();
        }

        List<Slot> slots = parkingLotToPark.getSlots().get(slotType);
        for (Slot slot : slots) {
            if (slot.isAvailable()) {
                slot.setParkedVehicle(vehicle);
                slot.setAvailable(false);
                slot.setStartingTime(new Date(System.currentTimeMillis()));
                //Exit from the loop as a slot found
                return true;
            }
        }
        parkingLotToPark.getIsFullForSlot().put(slotType, true);
        return false;
    }

    public Parking exit(Vehicle vehicle, String parkingLotId) {
        Date exitTime = new Date(System.currentTimeMillis());
        ParkingLot parkingLotToCheck = new ParkingLot(parkingLotId);
        if (!parkingLots.contains(parkingLotToCheck)) {
            throw new ParkingLotNotRegisteredException();
        }
        ParkingLot parkingLotToExit = getParkingLot(parkingLotId);
        SlotType slotType = getslotType(vehicle);

        List<Slot> slots = parkingLotToExit.getSlots().get(slotType);
        Parking parking = null;
        for (Slot slot : slots) {
            if (slot.isAvailable() != true && slot.getParkedVehicle().getNumber().equals(vehicle.getNumber())) {
                slot.setAvailable(true);
                slot.setParkedVehicle(null);
                Date parkingStartTime = slot.getStartingTime();
                slot.setStartingTime(null);
                parkingLotToExit.getIsFullForSlot().put(slotType, false);

                parking.setId(UUID.randomUUID().toString());
                parking.setVehicle(vehicle);
                parking.setStartTime(parkingStartTime);
                parking.setEndTime(exitTime);
                parking.setAmountPaid(ParkingHelper.amountToBePaid(parkingLotToExit, parkingStartTime, exitTime, slotType));
                parkings.add(parking);
                break;
            }
        }
        if (parking == null) {
            throw new VehicleNotFoundException();
        }
        return parking;
    }

    public List<Parking> getHistory(String vehicleNumber) {
        if (parkings == null || parkings.isEmpty()) {
            return new ArrayList<>();
        }
        List<Parking> parkingsPerVehicle = new ArrayList<>();
        for (Parking parking : parkingsPerVehicle) {
            if (parking.getVehicle().getNumber().equals(vehicleNumber)) {
                parkingsPerVehicle.add(parking);
            }
        }
        return parkingsPerVehicle;
    }

    private void addParking(Parking parking) {
        if (parkings == null) {
            parkings = new ArrayList<>();
        }
        parkings.add(parking);
    }


    private ParkingLot getParkingLot(String parkingLotId) {
        ParkingLot parkingLot = null;
        for (ParkingLot lot : parkingLots) {
            if (lot.getId().equalsIgnoreCase(parkingLotId)) {
                parkingLot = lot;
            }
        }
        return parkingLot;
    }

    private SlotType getslotType(Vehicle vehicle) {
        SlotType slotType = SlotType.BIKE;
        if (vehicle instanceof Car) {
            slotType = slotType.CAR;
        }
        return slotType;
    }

    private boolean isParkingLotFull(ParkingLot parkingLotToPark, SlotType slotType) {
        return parkingLotToPark.getIsFullForSlot() != null && parkingLotToPark.getIsFullForSlot().get(slotType) != null &&
                parkingLotToPark.getIsFullForSlot().get(slotType) == true;
    }
}
