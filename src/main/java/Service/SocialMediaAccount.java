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
    public boolean AccountExists(Account account)
    {
        return socialMediaAccountDAO.AccountExists(account);
    }
    public Account AddAccount(Account toAdd)
    {
        return socialMediaAccountDAO.AddAccount(toAdd);
    }
    public boolean loginTest(Account account)
    {
        return socialMediaAccountDAO.Login(account);
    }

    public Account getReference(Account account)
    {
        return socialMediaAccountDAO.getReference(account);
    }

    public Account AccountByID(int accountID)
    {
        return socialMediaAccountDAO.AccountByID(accountID);
    }
    
}
