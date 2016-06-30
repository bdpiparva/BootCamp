package com.parkinglot;

import com.parkinglot.exception.InvalidParkingTokenException;
import com.parkinglot.exception.ParkingLotFullException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajats on 6/29/16.
 */
public class ParkingAttendant {

    private Map<String, ParkingLot> assignedParkingLots = new HashMap<>();
    private ParkingStrategy parkingStrategy;

    public ParkingAttendant(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }

    public ParkingToken acceptCar(Car car) throws ParkingLotFullException {

//        ParkingToken token = null;
//        boolean isSpaceAvailable = false;
//        for(ParkingLot parkingLot : assignedParkingLots.values()){
//           if(!parkingLot.isFull()){
//               isSpaceAvailable = true;
//               token = parkingLot.park(car);
//           }
//        }
//
//        if(!isSpaceAvailable){
//            throw new ParkingLotFullException("All Parking Lots are full");
//        }

        return parkingStrategy.whereToPark(assignedParkingLots.values()).park(car);
    }

    public void assignParkingLot(ParkingLot parkingLot) {
        assignedParkingLots.put(parkingLot.getParkingLotNumber(), parkingLot);
    }

    public Car returnCar(ParkingToken parkingToken) throws InvalidParkingTokenException {
        if (assignedParkingLots.containsKey(parkingToken.getParkingLotNumber())) {
            ParkingLot parkingLot = assignedParkingLots.get(parkingToken.getParkingLotNumber());
            return parkingLot.unPark(parkingToken);
        } else {
            throw new InvalidParkingTokenException("Token is not valid");
        }
    }
}
