import Service.SocialMediaMessages;
import Model.Message;
import java.util.*;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args){
        SocialMediaMessages socialMediaMessages = new SocialMediaMessages();

        List<Message> messages = socialMediaMessages.GetAllMessages();

        System.out.println(messages);

    }
}
