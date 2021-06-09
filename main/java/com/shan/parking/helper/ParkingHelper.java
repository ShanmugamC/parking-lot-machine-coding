package com.shan.parking.helper;

import com.shan.parking.model.ParkingLot;
import com.shan.parking.model.SlotType;
import com.shan.parking.model.Vehicle;

import java.util.Date;
import java.util.Map;

public class ParkingHelper {
    public static Double amountToBePaid(ParkingLot parkingLot, Date startTime, Date endTime, SlotType slotType) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        int hoursSpent = (int) (endTime.getTime() - startTime.getTime()) / MILLI_TO_HOUR;
        Map<Integer, Integer> rateCard = parkingLot.getRateCard().get(slotType);

        if (hoursSpent <= 2) {
            return Double.valueOf(rateCard.get(2));
        } else if (hoursSpent <= 4) {
            return Double.valueOf(rateCard.get(4));
        } else if (hoursSpent <= 24) {
            return Double.valueOf(rateCard.get(24));
        } else if (hoursSpent > 24) {
            Double perDayCost = Double.valueOf(rateCard.get(24));
            int totalDays = hoursSpent / 24;
            return totalDays * perDayCost;
        }
        return 0.0;
    }
}
