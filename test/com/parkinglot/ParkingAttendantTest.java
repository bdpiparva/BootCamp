package com.parkinglot;

import com.parkinglot.exception.InvalidParkingTokenException;
import com.parkinglot.exception.ParkingLotFullException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by Bhupendrakumar Piprava on 6/29/16.
 */
public class ParkingAttendantTest {

    public static final String CAR_REGISTRATION_NUMBER = "MH12FV1234";
    public static final String PARKING_LOT_NUMBER = "1";

    private Car car;
    private ParkingAttendant parkingAttendant;
    private ParkingLot parkingLot;
    private ParkingToken parkingToken;

    @Before
    public void setUp() {
        car = new Car(CAR_REGISTRATION_NUMBER);
        parkingAttendant = new ParkingAttendant(new EvenParkingStrategy());
        parkingLot = mock(ParkingLot.class);
        parkingToken = new ParkingToken(car.getRegistrationNumber(), PARKING_LOT_NUMBER);

    }

    @Test
    public void shouldParkingAttendantAcceptTheCar() throws ParkingLotFullException {

        when(parkingLot.isFull()).thenReturn(false);
        when(parkingLot.park(car)).thenReturn(new ParkingToken(car.getRegistrationNumber(), PARKING_LOT_NUMBER));
        parkingAttendant.assignParkingLot(parkingLot);

        ParkingToken parkingtoken = parkingAttendant.acceptCar(car);

        Assert.assertNotNull(parkingtoken);

    }

    @Test
    public void shouldParkingAttendantAcceptTheCarWithMuplipleParkingLots() throws ParkingLotFullException {

        when(parkingLot.isFull()).thenReturn(false);
        when(parkingLot.park(car)).thenReturn(new ParkingToken(car.getRegistrationNumber(), PARKING_LOT_NUMBER));
        parkingAttendant.assignParkingLot(parkingLot);

        ParkingLot parkingLot1 = mock(ParkingLot.class);
        when(parkingLot1.isFull()).thenReturn(false);
        String anotherParkingLotNumber = "12345xyz";
        when(parkingLot1.park(car)).thenReturn(new ParkingToken(car.getRegistrationNumber(), anotherParkingLotNumber));
        parkingAttendant.assignParkingLot(parkingLot1);

        ParkingToken parkingtoken = parkingAttendant.acceptCar(car);

        Assert.assertNotNull(parkingtoken);

        Assert.assertTrue(parkingtoken.getParkingLotNumber().equals(PARKING_LOT_NUMBER) || parkingtoken.getParkingLotNumber().equals(anotherParkingLotNumber));

    }


    @Test(expected = ParkingLotFullException.class)
    public void shouldParkingAttendantNotAcceptTheCarWhenAllParkingLotsAreFull() throws ParkingLotFullException {

        when(parkingLot.isFull()).thenReturn(true);
        when(parkingLot.park(car)).thenReturn(new ParkingToken(car.getRegistrationNumber(), PARKING_LOT_NUMBER));
        parkingAttendant.assignParkingLot(parkingLot);

        ParkingToken parkingtoken = parkingAttendant.acceptCar(car);

        Assert.assertNotNull(parkingtoken);

    }

    @Test
    public void shouldParkingAttendantUnparkTheCar() throws ParkingLotFullException, InvalidParkingTokenException {

        when(parkingLot.isFull()).thenReturn(false);
        when(parkingLot.getParkingLotNumber()).thenReturn(PARKING_LOT_NUMBER);
        when(parkingLot.unPark(parkingToken)).thenReturn(car);
        parkingAttendant.assignParkingLot(parkingLot);

        Car returnedCar = parkingAttendant.returnCar(parkingToken);

        Assert.assertNotNull(returnedCar);
        Assert.assertEquals(CAR_REGISTRATION_NUMBER, returnedCar.getRegistrationNumber());

    }


    @Test(expected = InvalidParkingTokenException.class)
    public void shouldNotParkingAttendantUnparkTheCar() throws ParkingLotFullException, InvalidParkingTokenException {

        when(parkingLot.isFull()).thenReturn(false);
        when(parkingLot.getParkingLotNumber()).thenReturn(PARKING_LOT_NUMBER);
        when(parkingLot.unPark(parkingToken)).thenReturn(car);

        parkingAttendant.assignParkingLot(parkingLot);

        Car returnedCar = parkingAttendant.returnCar(new ParkingToken("", ""));
    }

}
