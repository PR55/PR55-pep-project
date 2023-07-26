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

    public Message GetMessageByID(int messageID)
    {
        return socialMediaMessagesDAO.GetMessageByID(messageID);
    }

    public Message DeleteMessageByID(int messageID)
    {
        return socialMediaMessagesDAO.DeleteMessageByID(messageID);
    }

    public Message UpdateMessage(int messageID, Message message)
    {
        return socialMediaMessagesDAO.UpdateMessage(messageID, message);
    }
}
