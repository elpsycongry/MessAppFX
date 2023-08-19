package com.example.demo;
import java.sql.*;
import java.util.ArrayList;

public class MessageDAO {
    private static final  String USER = "mysql";
    private static final  String PASSWORD = "Vuthanhtungtd2@";
    private static final String URL = "jdbc:mysql://localhost:3306/messageStoreT" ;

    public static Connection getConnection(){
        Connection databaseLink = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink  = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            System.out.println("Kết nối không thành công");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return databaseLink;
    }
    public static int getIDwithName(String userName){
        Connection connection = getConnection();
        PreparedStatement ps;
        int userID = 0;
        try {
            ps = connection.prepareStatement("SELECT userID FROM users WHERE userName = ?");
            ps.setString(1,userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                userID = rs.getInt("userID");
            }
        } catch (SQLException e) {
            System.out.println("Khong the lay ID vi ten nguoi dung ko ton tai ?");
            throw new RuntimeException(e);
        }
        return userID;
    }
    public static void addMessToDatabase(Data data){

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("insert INTO message(messageContent,userID) VALUES (?,?)");
            ps.setString(1,data.getContent());
            ps.setInt(2,data.getUserID());
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Them that bai");
            e.printStackTrace();
        }

    }

    public static ArrayList<Data> getAllMessage(){
        ArrayList<Data> resultList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "select ms.messageContent ,u.userName from message ms JOIN users u on ms.userID = u.userID;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Data data = new Data(rs.getString("userName"),rs.getString("messageContent"));
                resultList.add(data);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
    public static ArrayList<Data> get20LastMessage(){
        ArrayList<Data> resultList = new ArrayList<>();
        try {

            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT messageContent, userName FROM (\n" +
                            "select ms.messageID, ms.messageContent ,u.userName from message ms JOIN users u on ms.userID = u.userID \n" +
                            "ORDER BY ms.messageID DESC limit 20\n" +
                            ") AS T \n" +
                            "order by messageID \n" +
                            "; ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Data data = new Data(rs.getString("userName"),rs.getString("messageContent"));
                resultList.add(data);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

}
