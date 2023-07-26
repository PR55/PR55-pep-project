package Service;

import DAO.SocialMediaMessagesDAO;
import java.util.*;
import Model.Account;
import Model.Message;

public class SocialMediaMessages {
    
    private SocialMediaMessagesDAO socialMediaMessagesDAO;

    public SocialMediaMessages()
    {
        this.socialMediaMessagesDAO = new SocialMediaMessagesDAO();
    }
    
    public List<Message> GetAllMessages()
    {
        return socialMediaMessagesDAO.GetAllMessages();
    }

    public List<Message> GetUserMessages(int userID)
    {
        return socialMediaMessagesDAO.GetUserMessages(userID);
    }
}
