package com.parkinglot;

/**
 * Created by rajats on 6/29/16.
 */
public interface ParkingLotObserver {

    public void onParkingSpaceFull();

    public void onParkingSpaceAvailable();
}
