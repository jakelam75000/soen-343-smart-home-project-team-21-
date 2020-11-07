import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TimerspeedTest, tests if the speed modifier is working
 * the timer in the test is because the timer needs time to update the delay
 */
public class TimespeedTest {
    @Test
    public void testPplInHouse() {
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        JSpinner speed = shd.getSpeedspinner();
        speed.setValue(3);
        shd.run(true);
        Timer t = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = shd.getTimerDelay();
                assertEquals(i, 1000 / 3);
            }
        });
    }
}
