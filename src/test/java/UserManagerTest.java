import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class UserManagerTest {

    // Making sure all users are cleared
    public void isEmpty() {
        assertAll(
                "Make sure all hashMaps are empty",
                () -> assertEquals(0, UserManager.sizeAllUsers()),
                () -> assertEquals(0, UserManager.sizeUserParent()),
                () -> assertEquals(0, UserManager.sizeUserChild()),
                () -> assertEquals(0, UserManager.sizeUserGuest())
        );
    }

    // Initialize users
    public static void initialize() {
        UserManager.addUser("Parent1", "passwordabc", UserTypes.PARENT);
        UserManager.addUser("Parent2", "password123", UserTypes.PARENT);
        UserManager.addUser("Child1", "abc", UserTypes.CHILD);
        UserManager.addUser("Child2", "123", UserTypes.CHILD);
        UserManager.addUser("Guest", "password", UserTypes.GUEST);
    }

    @Test
    public void testAddUser() {
        isEmpty();

        initialize();
        // Should not be added because username already exists
        UserManager.addUser("Parent1", "password", UserTypes.PARENT);

        assertAll(
                "Make sure user have been added correctly",
                () -> assertEquals(5, UserManager.sizeAllUsers()),
                () -> assertEquals(2, UserManager.sizeUserParent()),
                () -> assertEquals(2, UserManager.sizeUserChild()),
                () -> assertEquals(1, UserManager.sizeUserGuest())
        );
    }

    @Test
    public void testDeleteUser() {
        isEmpty();

        // This should not remove anything since there are no users
        UserManager.removeUser("Parent2", "password123");
        isEmpty();

        initialize();

        UserManager.removeUser("Parent2", "password123");
        UserManager.removeUser("Guest", "password");
        UserManager.removeUser("Child1", "abc");
        //Should not remove since username does not exist
        UserManager.removeUser("Friend", "password");
        //Should not remove because password not valid
        UserManager.removeUser("Child2", "fail");

        assertAll(
                "Make sure user have been deleted correctly",
                () -> assertEquals(2, UserManager.sizeAllUsers()),
                () -> assertEquals(1, UserManager.sizeUserParent()),
                () -> assertEquals(1, UserManager.sizeUserChild()),
                () -> assertEquals(0, UserManager.sizeUserGuest())
        );
    }

    @Test
    public void testEditUser() {
        initialize();

        assertAll(
                "Make sure user have been edited correctly",
                () -> assertEquals(false, UserManager.editUser("", "", "", null)),
                () -> assertEquals(false, UserManager.editUser("Parent2", "wrong", "new", UserTypes.PARENT)),
                () -> assertEquals(true, UserManager.editUser("Parent2", "password123", "new", UserTypes.PARENT)),
                () -> assertEquals(true, UserManager.editUser("Guest", "password", "hello", UserTypes.CHILD))
        );
    }

    @AfterEach
    public void cleanUp() {
        UserManager.clearUsers();

    }
}
