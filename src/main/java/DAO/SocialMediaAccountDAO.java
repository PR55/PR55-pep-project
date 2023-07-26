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
            if(accounts.contains(acc))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        
    }

    public Account AddAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();

        if(account.password.length() > 4)
        {
            try 
            {
                String sql = "insert into account (username, password) values (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                preparedStatement.setString(1, account.username);
                preparedStatement.setString(2, account.password);
                            
                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_account_id, account.username, account.password);
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
        
    }

    public boolean Login(Account account)
    {
        
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        try {
            //Write SQL logic here
            String sql = "select * from account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account acc = new Account(rs.getInt("account_id"), rs.getString("username"), 
                rs.getString("password"));
                accounts.add(acc);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        boolean loginSuccess = false;

        for(int i = 0; i < accounts.size(); i++)
        {
            Account testAccount = new Account(accounts.get(i).getUsername(), accounts.get(i).getPassword());
            if(testAccount.equals(account))
            {
                loginSuccess = true;
                break;
            }
        }

        return loginSuccess;
    }

    public Account getReference(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){

                return new Account(rs.getInt("account_id"), rs.getString("username"), 
                rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account AccountByID(int accountID)
    {
        Connection connection = ConnectionUtil.getConnection();
        Account account = null;

        try {
            //Write SQL logic here
            String sql = "select * from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, accountID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account tempAccount = new Account(rs.getInt("account_id"), rs.getString("username"), 
                rs.getString("password"));
                account = tempAccount;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return account;
    }
}
