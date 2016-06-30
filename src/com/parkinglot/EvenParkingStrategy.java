package com.parkinglot;

import com.parkinglot.exception.ParkingLotFullException;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Bhupendrakumar Piprava on 6/30/16.
 */
public class EvenParkingStrategy implements ParkingStrategy {

    public static final String ALL_PARKING_LOTS_ARE_FULL = "All parking lots are full";

    @Override
    public ParkingLot whereToPark(final Collection<ParkingLot> parkingLotList) throws ParkingLotFullException {

        Iterator<ParkingLot> parkingLotIterator = parkingLotList.iterator();
        ParkingLot parkingLot = parkingLotIterator.next();
        while (parkingLotIterator.hasNext()) {
            ParkingLot parkingLotInList = parkingLotIterator.next();
            if (!parkingLotInList.isFull() && parkingLotInList.getEmptyParkingSlotCount() > parkingLot.getEmptyParkingSlotCount())
                parkingLot = parkingLotInList;

        }

        if(parkingLot.isFull())
            throw  new ParkingLotFullException(ALL_PARKING_LOTS_ARE_FULL);
        return parkingLot;
    }

}
