import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
public class HouseViewTest {
    /**
     * Description: Testing if the house layout accpets the current number of rooms
     * Context: user are loaded from database with default users, smarthomedashboard is initialized with HouseLayout.txt
     * Expected: the dynamic layout should compute that the house needs 5 rooms
     */
    @Test
    public void TestLoadedRooms(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        DynamicLayout d = shd.getDynamicLayout();
        assertTrue(d.getRoomCount() == 5);

    }

}
