
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HouseReaderTest {

    @Test
    public void testAddUser() {

        House testHouse = HouseReader.readAndLoadHouse("Houselayout.txt");
        Room room1 = testHouse.getRoomAtIndex(0);
        Room room2 = testHouse.getRoomAtIndex(1);
        Room room3 = testHouse.getRoomAtIndex(2);
        Room room4 = testHouse.getRoomAtIndex(3);
        Room room5 = testHouse.getRoomAtIndex(4);
        assertAll(
                "Make sure that house layout file is being properly read.",
                () -> assertEquals(5, testHouse.getRoomCount()),
                () -> assertEquals(LocationType.LIVINGROOM.toString(), room1.getName()),
                () -> assertEquals(LocationType.BEDROOM.toString(), room2.getName()),
                () -> assertEquals(LocationType.KITCHEN.toString(), room3.getName()),
                () -> assertEquals(LocationType.OFFICE.toString(), room4.getName()),
                () -> assertEquals(LocationType.GARAGE.toString(), room5.getName()),
                () -> assertEquals(3, room1.getSmartObjects().length),
                () -> assertEquals(2, room2.getSmartObjects().length),
                () -> assertEquals(2, room3.getSmartObjects().length),
                () -> assertEquals(2, room4.getSmartObjects().length),
                () -> assertEquals(0, room5.getSmartObjects().length),
                () -> assertEquals(20.3, room1.getTemperature()),
                () -> assertEquals(21.3, room2.getTemperature()),
                () -> assertEquals(22.0, room3.getTemperature()),
                () -> assertEquals(20.5, room4.getTemperature()),
                () -> assertEquals(18.0, room5.getTemperature())
        );
    }
}