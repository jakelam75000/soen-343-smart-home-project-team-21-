import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
public class HouseViewTest {
    /**
     * Tsting if the house layout accpets the current number of rooms
     */
    @Test
    public void TestLoadedRooms(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        DynamicLayout d = shd.getDynamicLayout();
        assertTrue(d.getRoomCount() == 5);

    }

}
