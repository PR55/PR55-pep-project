package DAO;

import Model.Message;
import Model.Account;

import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocialMediaMessagesDAO {
    public List<Message> GetAllMessages()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            //Write SQL logic here
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message NewMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        Message newMessage = null;

        try{
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                preparedStatement.setInt(1, message.posted_by);
                preparedStatement.setString(2, message.message_text);
                preparedStatement.setLong(3, message.time_posted_epoch);
                            
                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_message_id = (int) pkeyResultSet.getLong(1);
                    newMessage = new Message(generated_message_id, message.posted_by, message.message_text,
                    message.time_posted_epoch);
                }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return newMessage;
    }
    
    public List<Message> GetUserMessages(int userID)
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            //Write SQL logic here
            String sql = "select * from message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message GetMessageByID(int messageID)
    {
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;

        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message tempMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                message = tempMessage;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return message;
    }

    public Message DeleteMessageByID(int messageID)
    {
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;

        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message tempMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                message = tempMessage;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        if(message != null)
        {
            try {
                //Write SQL logic here
                String sql = "delete from message where message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                preparedStatement.setInt(1, messageID);
    
                preparedStatement.executeUpdate();

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return message;
    }

    public Message UpdateMessage(int messageID,String message)
    {
        Connection connection = ConnectionUtil.getConnection();
        Message newMessage = null;

        if(message != "" && message.length() < 255)
        {
            try {
                //Write SQL logic here
                String sql = "update message set message_text = ? where message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                preparedStatement.setString(1, message);
                preparedStatement.setInt(2, messageID);
    
                preparedStatement.executeUpdate();
    
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }

            newMessage = this.GetMessageByID(messageID);

        }
        
        return newMessage;
    }

}
