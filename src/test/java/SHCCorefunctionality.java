import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test case class holding tests pertaining to SHC
 */
public class SHCCorefunctionality {
    /**
     * Testing the use case of SHC functionality
     */
    @Test
    public void SHCfunctionalitytest(){
        UserDatabaseManager.loadUsers(false);
        SmartHomeDashboard shd = new SmartHomeDashboard("test", UserTypes.PARENT.toString(), "Parent", "Houselayout.txt");
        JCheckBox[] j = shd.getOpenClosePanel();
        JList<SmartObjectType> d = shd.getListItems();
        d.setSelectedIndex(0);
        Timer t = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assertTrue(!shd.getHouse().getObjectState(j[0].getText()));
                j[0].setSelected(true);
                assertTrue(shd.getHouse().getObjectState(j[0].getText()));
                d.setSelectedIndex(2);
                Timer t1 = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        assertTrue(!shd.getHouse().getObjectState(j[0].getText()));
                        j[0].setSelected(true);
                        assertTrue(shd.getHouse().getObjectState(j[0].getText()));
                        d.setSelectedIndex(1);
                        Timer t2 = new Timer(5000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                assertTrue(!shd.getHouse().getObjectState(j[0].getText()));
                                j[0].setSelected(true);
                                assertTrue(shd.getHouse().getObjectState(j[0].getText()));
                                //PUT CODE HERE
                            }
                        });
                    }
                });
            }
        });


    }
}
