/**
 * Exception class for uploading a file with the wrong extension.
 */
public class WrongExtensionException extends Exception{

    public WrongExtensionException(String message){
        super(message);
    }
}
