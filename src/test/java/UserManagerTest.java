
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class UserManagerTest {

    // Making sure all users are cleared
    public void isEmpty() {
        assertAll(
                "Make sure all hashMaps are empty",
                () -> assertEquals(0, UserManager.sizeAuthenticate()),
                () -> assertEquals(0, UserManager.sizeUserParent()),
                () -> assertEquals(0, UserManager.sizeUserChild()),
                () -> assertEquals(0, UserManager.sizeUserGuest())
        );
    }

    @Test
    public void testAddUser() {
        isEmpty();

        UserManager.initialize();
        // Should not be added because username already exists
        UserManager.addUser("Parent1", "password", UserTypes.PARENT.toString());

        assertAll(
                "Make sure user have been added correctly",
                () -> assertEquals(5, UserManager.sizeAuthenticate()),
                () -> assertEquals(2, UserManager.sizeUserParent()),
                () -> assertEquals(2, UserManager.sizeUserChild()),
                () -> assertEquals(1, UserManager.sizeUserGuest())
        );
    }

    @Test
    public void testDeleteUser() {
        isEmpty();

        // This should not remove anything since there are no users
        UserManager.removeUser("dina");
        isEmpty();

        UserManager.initialize();

        UserManager.removeUser("Parent2");
        UserManager.removeUser("Guest");
        UserManager.removeUser("Child1");
        //Should not remove since username does not exist
        UserManager.removeUser("Friend");

        assertAll(
                "Make sure user have been deleted correctly",
                () -> assertEquals(2, UserManager.sizeAuthenticate()),
                () -> assertEquals(1, UserManager.sizeUserParent()),
                () -> assertEquals(1, UserManager.sizeUserChild()),
                () -> assertEquals(0, UserManager.sizeUserGuest())
        );
    }

    @Test
    public void testEditUser() {
        // TO-DO
    }

    @AfterEach
    public void cleanUp() {
        UserManager.clearUsers();

    }
}
