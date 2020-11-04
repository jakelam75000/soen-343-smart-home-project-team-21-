
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HouseReaderTest {

    /**
     * Description: test if house layout can be read and loaded from a .txt file
     * Context: HouseLayout.txt file
     * Expected: the first assert should give the following: 5
     *           first assertAll: assertEquals should give the following results in their respective order: LIVINGROOM, BEDROOM, KITCHEN, OFFICE, GARAGE
     *           second assertAll: assertEquals should give the following results in their respective order: 3, 2, 2, 2, 0
     *           third assertAll: assertEquals should give the following results in their respective order: 20.3, 21.3, 22.0, 20.5, 18.0
     */
    @Test
    public void testReadAndLoadHouse() {

        House testHouse = HouseReader.readAndLoadHouse("Houselayout.txt");
        Room room1 = testHouse.getRoomAtIndex(0);
        Room room2 = testHouse.getRoomAtIndex(1);
        Room room3 = testHouse.getRoomAtIndex(2);
        Room room4 = testHouse.getRoomAtIndex(3);
        Room room5 = testHouse.getRoomAtIndex(4);
        assertEquals(5, testHouse.getRoomCount());
        assertAll(
                "Make sure that house layout names are properly read.",
                () -> assertEquals(LocationType.LIVINGROOM.toString(), room1.getName()),
                () -> assertEquals(LocationType.BEDROOM.toString(), room2.getName()),
                () -> assertEquals(LocationType.KITCHEN.toString(), room3.getName()),
                () -> assertEquals(LocationType.OFFICE.toString(), room4.getName()),
                () -> assertEquals(LocationType.GARAGE.toString(), room5.getName())
        );

        assertAll(
                "Make sure the number of smart objects in each room is correct.",
                () -> assertEquals(5, room1.getSmartObjects().length),
                () -> assertEquals(3, room2.getSmartObjects().length),
                () -> assertEquals(4, room3.getSmartObjects().length),
                () -> assertEquals(3, room4.getSmartObjects().length),
                () -> assertEquals(2, room5.getSmartObjects().length)
        );

        assertAll(
                "Make sure the temperature in each room is correct",
                () -> assertEquals(20.3, room1.getTemperature()),
                () -> assertEquals(21.3, room2.getTemperature()),
                () -> assertEquals(22.0, room3.getTemperature()),
                () -> assertEquals(20.5, room4.getTemperature()),
                () -> assertEquals(18.0, room5.getTemperature())
        );
    }
}