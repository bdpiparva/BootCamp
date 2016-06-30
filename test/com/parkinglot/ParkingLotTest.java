package com.parkinglot;

import com.parkinglot.exception.InvalidParkingTokenException;
import com.parkinglot.exception.ParkingLotFullException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class ParkingLotTest {


    public static final String CAR_REGISTRATION_NUMBER = "MH12FV1234";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ParkingLotObserver parkingLotObserver;
    private Car car;

    @Before
    public void setup() {
        parkingLotObserver = mock(ParkingLotObserver.class);
        car = mock(Car.class);
        when(car.getRegistrationNumber()).thenReturn(CAR_REGISTRATION_NUMBER);
    }


    @Test
    public void shouldParkMyCarWhenSlotsAvailable() throws ParkingLotFullException {

        ParkingLot parkingLot = new ParkingLot(2);
        Object parkingToken1 = parkingLot.park(car);
        Assert.assertNotNull(parkingToken1);
        Object parkingToken2 = parkingLot.park(car);
        assertNotNull(parkingToken2);
    }


    @Test
    public void shouldNotParkMyCarIfSpaceUnavailable() throws ParkingLotFullException {


        ParkingLot parkingLot = new ParkingLot(1);
        Object parkingTokenA = parkingLot.park(car);
        assertNotNull(parkingTokenA);

        expectedException.expect(ParkingLotFullException.class);
        expectedException.expectMessage(ParkingLot.SLOT_UNAVAILABLE_EXCEPTION_MSG);

        parkingLot.park(car);

    }

    @Test
    public void shouldUnparkMyCarWithValidToken() throws ParkingLotFullException, InvalidParkingTokenException {

        ParkingLot parkingLot = new ParkingLot(1);
        ParkingToken parkingToken = parkingLot.park(car);

        Car unparkedCar = parkingLot.unPark(parkingToken);

        Assert.assertEquals(car, unparkedCar);
        Assert.assertEquals(CAR_REGISTRATION_NUMBER, parkingToken.getVehicleNumber());

    }

    @Test
    public void shouldNotUnparkMyCarWithWrongToken() throws ParkingLotFullException, InvalidParkingTokenException {

        expectedException.expect(InvalidParkingTokenException.class);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(car);

        parkingLot.unPark(new Object());
    }

    @Test
    public void shouldNotUnparkMyCarIfTokenAlreadyUsed() throws ParkingLotFullException, InvalidParkingTokenException {

        expectedException.expect(InvalidParkingTokenException.class);

        ParkingLot parkingLot = new ParkingLot(1);
        Object parkingToken = parkingLot.park(car);

        parkingLot.unPark(parkingToken);

        parkingLot.unPark(parkingToken);

    }

    @Test
    public void shouldNotifyProductOwnerWhenTheParkingLotIsFull() throws ParkingLotFullException {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(parkingLotObserver);
        Object parkingToken = parkingLot.park(car);
        verify(parkingLotObserver, times(1)).onParkingSpaceFull();
    }


    @Test
    public void shouldNotNotifyProductOwnerWhenTheParkingLotIsNotFull() throws ParkingLotFullException {

        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(parkingLotObserver);

        Object parkingToken = parkingLot.park(car);

        verify(parkingLotObserver, times(0)).onParkingSpaceFull();
    }

    @Test
    public void shouldNotifySecurityPersonnelWhenTheParkingLotIsFull() throws ParkingLotFullException {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(parkingLotObserver);

        Object parkingToken = parkingLot.park(car);

        verify(parkingLotObserver, times(1)).onParkingSpaceFull();
    }

    @Test
    public void shouldNotNotifySecurityPersonnelWhenTheParkingLotIsNotFull() throws ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(parkingLotObserver);
        Object parkingToken = parkingLot.park(car);
        verify(parkingLotObserver, times(0)).onParkingSpaceFull();
    }


    @Test
    public void shouldNotifyObserverWhenUnparkAndParkingSlotWasFull() throws ParkingLotFullException, InvalidParkingTokenException {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(parkingLotObserver);
        Object parkingToken = parkingLot.park(car);

        parkingLot.unPark(parkingToken);

        verify(parkingLotObserver, times(1)).onParkingSpaceAvailable();
    }


    @Test
    public void shouldNotNotifyObserverWhenUnparkAndParkingSlotWasNotFull() throws ParkingLotFullException, InvalidParkingTokenException {

        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(parkingLotObserver);
        Object parkingToken = parkingLot.park(car);

        parkingLot.unPark(parkingToken);

        verify(parkingLotObserver, times(0)).onParkingSpaceAvailable();
    }

    @Test
    public void shouldAbleToGetParkingLotNumber() {
        ParkingLot parkingLot = new ParkingLot(2);
        Assert.assertNotNull(parkingLot.getParkingLotNumber());
    }

    @Test
    public void shouldAbleToCheckEmptyParkingSlotCount() throws ParkingLotFullException {

        ParkingLot parkingLot = new ParkingLot(3);
        parkingLot.park(car);
        Assert.assertEquals(2,parkingLot.getEmptyParkingSlotCount());

    }


}
