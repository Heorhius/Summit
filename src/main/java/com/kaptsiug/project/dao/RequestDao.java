package com.kaptsiug.project.dao;

import com.kaptsiug.project.model.Request;

import java.util.List;

public interface RequestDao {
    List<Request> getRequests(int userId);

    List<Request> getAllRequests();

    void addRequest(int userId, Request request);
}

