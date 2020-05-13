package hu.bme.mit.spaceship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class GT4500Test {

    private GT4500 ship;
    private TorpedoStore primaryMock;
    private TorpedoStore secondaryMock;

    @BeforeEach
    public void init() {
        primaryMock = mock(TorpedoStore.class);
        secondaryMock = mock(TorpedoStore.class);
        this.ship = new GT4500(primaryMock, secondaryMock);
    }

    @Test
    public void fireTorpedo_Single_Success() {
        // Arrange
        when(primaryMock.fire(1)).thenReturn(true);
        when(secondaryMock.fire(1)).thenReturn(true);

        // Act
        boolean check = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert

        assertTrue(check);
        verify(primaryMock, times(1)).fire(1);
        verify(secondaryMock, times(0)).fire(1);
    }

    @Test
    public void fireTorpedo_All_Success() {
        // Arrange
        when(primaryMock.fire(1)).thenReturn(true);
        when(secondaryMock.fire(1)).thenReturn(true);
        // Act
        boolean check = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertTrue(check);
        verify(primaryMock, times(1)).fire(1);
        verify(secondaryMock, times(1)).fire(1);
    }

    @Test
    public void fireTorpedo_Both_Empty() {
        // Arrange
        when(primaryMock.isEmpty()).thenReturn(true);
        when(secondaryMock.isEmpty()).thenReturn(true);
        //Act

        boolean check = ship.fireTorpedo(FiringMode.ALL);

        //Assert
        assertFalse(check);
        verify(primaryMock, times(0)).fire(1);
        verify(secondaryMock, times(0)).fire(1);
    }

    @Test
    public void fireTorpedo_All_Fail() {
        // Arrange
        when(primaryMock.fire(1)).thenReturn(false);
        when(secondaryMock.fire(1)).thenReturn(false);

        // Act
        boolean check = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertFalse(check);
        verify(primaryMock, times(1)).fire(1);
        verify(secondaryMock, times(1)).fire(1);
    }

    @Test
    public void fireTorpedo_Primary_Empty() {
        // Arrange
        when(primaryMock.isEmpty()).thenReturn(true);
        when(secondaryMock.fire(1)).thenReturn(true);

        //Act
        boolean check = ship.fireTorpedo(FiringMode.SINGLE);
        check = ship.fireTorpedo(FiringMode.SINGLE);

        //Assert
        assertTrue(check);
        verify(primaryMock, times(0)).fire(1);
        verify(secondaryMock, times(2)).fire(1);

    }

    @Test
    public void fireTorpedo_Secondary_Empty() {
        // Arrange
        when(primaryMock.fire(1)).thenReturn(true);
        when(secondaryMock.isEmpty()).thenReturn(true);
        when(primaryMock.isEmpty()).thenReturn(false);

        //Act
        boolean check = ship.fireTorpedo(FiringMode.SINGLE);
        check = ship.fireTorpedo(FiringMode.SINGLE);
        check = ship.fireTorpedo(FiringMode.SINGLE);

        //Assert
        assertTrue(check);
        verify(primaryMock, times(3)).fire(1);
        verify(secondaryMock, times(0)).fire(1);
        verify(primaryMock, times(3)).isEmpty();

    }

    @Test
    public void fireTorpedo_Single_Primary_Fail() {
        // Arrange
        when(primaryMock.fire(1)).thenReturn(false);

        //Act
        boolean check = ship.fireTorpedo(FiringMode.SINGLE);

        //Assert
        assertFalse(check);
        verify(primaryMock, times(1)).fire(1);
        verify(secondaryMock, times(0)).fire(1);

    }


    @Test
    public void fireTorpedo_Single_Secondary_Fail() {
        // Arrange
        when(primaryMock.isEmpty()).thenReturn(true);
        when(secondaryMock.fire(1)).thenReturn(false);

        //Act
        boolean check = ship.fireTorpedo(FiringMode.SINGLE);

        //Assert
        assertFalse(check);
        verify(primaryMock, times(0)).fire(1);
        verify(secondaryMock, times(1)).fire(1);

    }

    @Test
    public void fireTorpedo_Single_Both_Empty(){
        // Arrange
        when(primaryMock.isEmpty()).thenReturn(true);
        when(secondaryMock.isEmpty()).thenReturn(true);

        // Act
        boolean check = ship.fireTorpedo(FiringMode.SINGLE);
        check = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertFalse(check);
        verify(primaryMock, times(2)).isEmpty();
        verify(secondaryMock, times(2)).isEmpty();
    }

    @Test
    public void fireTorpedo_ALL_Both_Empty(){
        // Arrange
        when(primaryMock.isEmpty()).thenReturn(true);
        when(secondaryMock.isEmpty()).thenReturn(true);

        // Act
        boolean check = ship.fireTorpedo(FiringMode.ALL);
        check = ship.fireTorpedo(FiringMode.ALL);


        // Assert
        assertFalse(check);
        verify(primaryMock, times(2)).isEmpty();
        verify(secondaryMock, times(2)).isEmpty();
    }

    @Test
    public void fireTorpedo_Single_Both(){
        // Arrange
        when(primaryMock.fire(1)).thenReturn(true);
        when(secondaryMock.fire(1)).thenReturn(true);

        // Act
        boolean check = ship.fireTorpedo(FiringMode.SINGLE);
        check = ship.fireTorpedo(FiringMode.SINGLE);
        check = ship.fireTorpedo(FiringMode.SINGLE);
        check = ship.fireTorpedo(FiringMode.SINGLE);
        ship.fireLaser(FiringMode.SINGLE);

        // Assert
        assertTrue(check);
        verify(primaryMock, times(2)).fire(1);
        verify(secondaryMock, times(2)).fire(1);
    }

     @Test
     public void ShipCreation_and_Firing(){

        GT4500 testShip= new GT4500();

        boolean singleFireCheck = testShip.fireTorpedo(FiringMode.SINGLE);
        singleFireCheck = testShip.fireTorpedo(FiringMode.SINGLE);
        boolean allFireCheck = testShip.fireTorpedo(FiringMode.ALL);

        assertTrue(singleFireCheck);
        assertTrue(allFireCheck);
     }

    @Test
    public void fireTorpedo_Single_Primary_Only_One_Torpedo() { // Coverage for Single fire if(!primaryTorpedoStore.isEmpty())
        //Arrange
        when(primaryMock.fire(1)).thenReturn(true);

        // act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        //arrange
        when(primaryMock.isEmpty()).thenReturn(true);
        when(secondaryMock.isEmpty()).thenReturn(true);

        //Act
        result = ship.fireTorpedo(FiringMode.SINGLE);

        //Assert
        assertFalse(result);
        verify(primaryMock, times(1)).fire(1);
    }
}
