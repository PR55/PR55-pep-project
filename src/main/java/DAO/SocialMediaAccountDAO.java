package DAO;

import Model.Account;

import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocialMediaAccountDAO {
    
    public List<Account> GetAllAccounts()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        try {
            //Write SQL logic here
            String sql = "select * from account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), 
                rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return accounts;
    }

    public boolean AccountExists(Account acc)
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        if(acc.username == "")
        {
            return true;
        }
        else
        {
            Account foundAccount = null;
            try {
                //Write SQL logic here
                String sql = "select * from account where username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                
                preparedStatement.setString(1, acc.username);
                
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Account account = new Account(rs.getInt("account_id"), rs.getString("username"), 
                    rs.getString("password"));
                    foundAccount = account;
                    
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            if(foundAccount == null)
                return false;
        }
        return true;
        
        
    }

    public Account AddAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();

        if(account.password.length() >= 4)
        {
            try {
                //Write SQL logic here
                String sql = "insert into accunt (username, password) values (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account.username);
                preparedStatement.setString(1, account.password);
    
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    int generated_account_id = (int) resultSet.getLong(1);
                    return new Account(generated_account_id, account.username, account.password);
                }
    
            }catch(SQLException e){
                System.out.println(e.getMessage());
                
            }
        }
        return null;
        
    }

}
