package Service;
import DAO.SocialMediaAccountDAO;
import java.util.*;
import Model.Account;

public class SocialMediaAccount {
    
    private SocialMediaAccountDAO socialMediaAccountDAO;

    public SocialMediaAccount()
    {
        socialMediaAccountDAO = new SocialMediaAccountDAO();
    }

    public List<Account> GetAllAccounts()
    {
        return socialMediaAccountDAO.GetAllAccounts();
    }
}
