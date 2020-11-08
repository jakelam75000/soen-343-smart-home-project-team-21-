import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class HouseViewTest {
    /**
     * Description: Testing if the house layout accpets the current number of rooms
     * Context: user are loaded from database with default users, smarthomedashboard is initialized with HouseLayout.txt
     * Expected: the dynamic layout should compute that the house needs 5 rooms
     */
    //Delivery 2: 3.4
    @Test
    public void TestLoadedRooms(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        DynamicLayout d = shd.getDynamicLayout();
        assertTrue(d.getRoomCount() == 5);

    }

}
