import Controller.SocialMediaController;
import io.javalin.Javalin;
import DAO.SocialMediaAccountDAO;
import Service.SocialMediaAccount;
import java.util.*;
import Model.Account;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
        SocialMediaAccount socialMediaAccount = new SocialMediaAccount();
        List<Account> account = socialMediaAccount.GetAllAccounts();
        for(Account a: account)
        {
            System.out.println(a.account_id + " " + a.username + " " + a.password);
        }
    }
}
