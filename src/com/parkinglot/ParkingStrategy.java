package com.parkinglot;

import com.parkinglot.exception.ParkingLotFullException;

import java.util.Collection;

/**
 * Created by Bhupendrakumar Piprava on 6/30/16.
 */
public interface ParkingStrategy {

    public ParkingLot whereToPark(final Collection<ParkingLot> parkingLotList) throws ParkingLotFullException;
}
