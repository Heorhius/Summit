package com.kaptsiug.project.dao.implementation;

import com.kaptsiug.project.dao.UserDao;
import com.kaptsiug.project.model.User;
import com.kaptsiug.project.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String QUERY_GET_USER = "SELECT * FROM User AS u WHERE u.login=? AND u.password=?";
    private static final String QUERY_ADD_USER = "INSERT INTO User (name, login, password) VALUES (?,?,?);";
    private static final String MSG_USER_ADDED = "User has been added";
    private static final String QUERY_GET_ALL_USERS = "SELECT * FROM User";
    Connection connection = null;

    public UserDaoImpl() {
        connection = DbUtil.getConnection();
    }

    @Override
    public User getUser(String login, String password) {
        User user = null;

        try {
            PreparedStatement pstmt = connection.prepareStatement(QUERY_GET_USER);

            pstmt.setString(1, login);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("login"),
                        rs.getString("password"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return user;
    }

    @Override
    public void addUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ADD_USER);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            System.out.println(MSG_USER_ADDED);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY_GET_ALL_USERS);
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("login"),
                        rs.getString("password"));
                users.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
