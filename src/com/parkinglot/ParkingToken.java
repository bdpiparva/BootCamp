package com.parkinglot;

/**
 * Created by rajats on 6/29/16.
 */
public class ParkingToken {

    private String tokenNumber;
    private String vehicleNumber;
    private String parkingLotNumber;


    public ParkingToken(String vehicleNumber,String parkingLotNumber) {
        this.tokenNumber = "#"+System.nanoTime();
        this.vehicleNumber = vehicleNumber;
        this.parkingLotNumber = parkingLotNumber;
    }

    public String getParkingLotNumber() {
        return parkingLotNumber;
    }


    public String getVehicleNumber() {
        return vehicleNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingToken that = (ParkingToken) o;

        if (tokenNumber != null ? !tokenNumber.equals(that.tokenNumber) : that.tokenNumber != null) return false;
        return vehicleNumber != null ? vehicleNumber.equals(that.vehicleNumber) : that.vehicleNumber == null;

    }

    @Override
    public int hashCode() {
        int result = tokenNumber != null ? tokenNumber.hashCode() : 0;
        result = 31 * result + (vehicleNumber != null ? vehicleNumber.hashCode() : 0);
        return result;
    }
}
