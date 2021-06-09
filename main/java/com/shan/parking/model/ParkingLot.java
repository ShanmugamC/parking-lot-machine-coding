package com.shan.parking.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {
    private String id;
    private Map<SlotType, List<Slot>> slots;
    private Map<SlotType, Map<Integer, Integer>> rateCard;
    private Map<SlotType, Boolean> isFullForSlot;

    public ParkingLot() {
    }

    public ParkingLot(String id) {
        this.id = id;
        this.slots = new HashMap<>();
        this.rateCard = new HashMap<>();
    }

    public ParkingLot(String id, Map<SlotType, List<Slot>> slots, Map<SlotType, Map<Integer, Integer>> rateCard) {
        this.id = id;
        this.slots = slots;
        this.rateCard = rateCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<SlotType, List<Slot>> getSlots() {
        return slots;
    }

    public void setSlots(Map<SlotType, List<Slot>> slots) {
        this.slots = slots;
    }

    public Map<SlotType, Map<Integer, Integer>> getRateCard() {
        return rateCard;
    }

    public void setRateCard(Map<SlotType, Map<Integer, Integer>> rateCard) {
        this.rateCard = rateCard;
    }

    public Map<SlotType, Boolean> getIsFullForSlot() {
        return isFullForSlot;
    }

    public void setIsFullForSlot(Map<SlotType, Boolean> isFullForSlot) {
        this.isFullForSlot = isFullForSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
