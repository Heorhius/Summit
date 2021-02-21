package com.kaptsiug.project.dao.implementation;

import com.kaptsiug.project.dao.RequestDao;
import com.kaptsiug.project.model.Request;
import com.kaptsiug.project.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {
    private static final String QUERY_INSERT_SUMMIT_VALUES = "INSERT INTO Request (country, month, day, user_id) VALUES (?,?,?,?);";
    private static final String MSG_SUMMIT_DATE_ADDED = "Date for summit was added";
    private static final String QUERY_VIEW_USER_DATES = "SELECT * FROM Request WHERE user_id=?;";
    private static final String QUERY_VIEW_ALL_REQUESTS = "SELECT * FROM Request";
    Connection connection = null;

    public RequestDaoImpl() {
        connection = DbUtil.getConnection();
    }

    @Override
    public List<Request> getRequests(int userId) {
        ArrayList<Request> requests = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(QUERY_VIEW_USER_DATES);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Request request = new Request(rs.getInt("id"), rs.getString("country"),
                        rs.getInt("month"), rs.getInt("day"));
                requests.add(request);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return requests;
    }

    @Override
    public List<Request> getAllRequests() {
        ArrayList<Request> allRequests = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY_VIEW_ALL_REQUESTS);
            while (rs.next()) {
                Request request = new Request(rs.getString("country"), rs.getInt("month"),
                        rs.getInt("day"));
                allRequests.add(request);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return allRequests;
    }

    @Override
    public void addRequest(int userId, Request request) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT_SUMMIT_VALUES);
            preparedStatement.setString(1, request.getCountry());
            preparedStatement.setInt(2, request.getMonth());
            preparedStatement.setInt(3, request.getDay());
            preparedStatement.setInt(4, userId);
            preparedStatement.executeUpdate();
            System.out.println(MSG_SUMMIT_DATE_ADDED);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
