package com.example.demo;
import java.sql.*;
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


    public static void main(String[] args) {

        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select ms.messageContent ,u.userName from message ms JOIN users u on ms.userID = u.userID;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Data data = new Data(rs.getString("userName"),rs.getString("messageContent"));
                System.out.println(data);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public static Data get20LastMessage(){

//    }
}
