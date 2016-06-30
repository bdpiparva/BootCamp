package com.parkinglot;

import com.parkinglot.exception.ParkingLotFullException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Bhupendrakumar Piprava on 6/30/16.
 */
public class EvenParkingStrategyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ParkingLot basement;
    private ParkingLot backyard;
    private List<ParkingLot> parkingLotList;

    @Before
    public void setup() {
        basement = mock(ParkingLot.class);
        backyard = mock(ParkingLot.class);

        parkingLotList = new ArrayList<>();
        parkingLotList.add(basement);
        parkingLotList.add(backyard);
    }

    @Test
    public void shouldAbleToKnowWhereToPark() throws ParkingLotFullException {

        when(basement.getEmptyParkingSlotCount()).thenReturn(2);
        when(backyard.getEmptyParkingSlotCount()).thenReturn(3);
        when(basement.isFull()).thenReturn(false);
        when(backyard.isFull()).thenReturn(false);

        ParkingStrategy parkingStrategy = new EvenParkingStrategy();

        assertEquals(backyard, parkingStrategy.whereToPark(parkingLotList));

    }

    @Test
    public void shouldNotReturnParkingLotIfFull() throws ParkingLotFullException {

        expectedException.expect(ParkingLotFullException.class);
        expectedException.expectMessage(EvenParkingStrategy.ALL_PARKING_LOTS_ARE_FULL);

        when(basement.getEmptyParkingSlotCount()).thenReturn(0);
        when(backyard.getEmptyParkingSlotCount()).thenReturn(0);
        when(basement.isFull()).thenReturn(true);
        when(backyard.isFull()).thenReturn(true);

        ParkingStrategy parkingStrategy = new EvenParkingStrategy();

        parkingStrategy.whereToPark(parkingLotList);

        fail("Did not throw exception");

    }

}