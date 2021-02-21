package com.kaptsiug.project.controller;

import com.kaptsiug.project.dao.RequestDao;
import com.kaptsiug.project.dao.UserDao;
import com.kaptsiug.project.factory.DaoFactory;
import com.kaptsiug.project.model.Request;
import com.kaptsiug.project.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SummitSystem {
    private static final int ADMIN_ID = 1;
    private static final String MSG_INPUT_LOGIN = "Input login: ";
    private static final String MSG_INPUT_PASSWORD = "Input password: ";
    private static final String MSG_INCORRECT_INPUT = "Incorrect input!";
    private static final String MSG_LOGGED_IN = "You are logged in!";
    private static final String MSG_ADD_USER = "1 - Add user";
    private static final String MSG_VIEW_ALL_USERS = "2 - View all users";
    private static final String MSG_EXIT = "0 - Exit";
    private static final String MSG_CHOICE = "Make a choice: ";
    private static final String MSG_INPUT_NAME = "Input name: ";
    private static final String MSG_VIEW_ALL_DATES = "1 - View all dates";
    private static final String MSG_VIEW_YOURSELF_DATES = "2 - View yourself dates";
    private static final String MSG_ADD_NEW_DATE = "3 - Add new date";
    private static final String MSG_EMPTY_DATES_LIST = "List of dates is empty";
    private static final String MSG_INPUT_COUNTRY = "Input country: ";
    private static final String MSG_INPUT_MONTH = "Input month: ";
    private static final String MSG_INPUT_DAY = "Input day: ";
    private static final String MSG_SUMMIT_SCHEDULED = "Summit is scheduled for ";
    private final UserDao userDao = DaoFactory.getUserDao();
    private final RequestDao requestDao = DaoFactory.getRequestDao();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private User currentUser = null;

    public static void main(String[] args) {
        SummitSystem summitSystem = new SummitSystem();
        while (summitSystem.currentUser == null) {
            System.out.print(MSG_INPUT_LOGIN);
            String login = readStringFromIn(summitSystem.reader);

            System.out.print(MSG_INPUT_PASSWORD);
            String password = readStringFromIn(summitSystem.reader);

            summitSystem.currentUser = summitSystem.userDao.getUser(login, password);
            if (summitSystem.currentUser == null) {
                System.out.println(MSG_INCORRECT_INPUT);
            } else {
                System.out.println(MSG_LOGGED_IN);
            }
        }

        if (summitSystem.currentUser.getId() == SummitSystem.ADMIN_ID) {
            while (true) {
                System.out.println(MSG_ADD_USER);
                System.out.println(MSG_VIEW_ALL_USERS);
                System.out.println(MSG_EXIT);
                System.out.print(MSG_CHOICE);

                int key = Integer.parseInt(readStringFromIn(summitSystem.reader));
                switch (key) {
                    case 1: {
                        System.out.print(MSG_INPUT_NAME);
                        String name = readStringFromIn(summitSystem.reader);

                        System.out.print(MSG_INPUT_LOGIN);
                        String login = readStringFromIn(summitSystem.reader);

                        System.out.print(MSG_INPUT_PASSWORD);
                        String password = readStringFromIn(summitSystem.reader);

                        User user = new User(name, login, password);
                        summitSystem.userDao.addUser(user);
                        break;
                    }
                    case 2: {
                        List<User> list = summitSystem.userDao.getAllUsers();
                        for (User aList : list) {
                            System.out.println(aList);
                        }
                        break;
                    }
                    case 0: {
                        return;
                    }
                }
            }
        } else {
            Request req = summitSystem.processRequests();
            while (req == null) {
                System.out.println(MSG_VIEW_ALL_DATES);
                System.out.println(MSG_VIEW_YOURSELF_DATES);
                System.out.println(MSG_ADD_NEW_DATE);
                System.out.println(MSG_EXIT);
                System.out.print(MSG_CHOICE);

                int key = Integer.parseInt(readStringFromIn(summitSystem.reader));
                switch (key) {
                    case 1: {
                        List<Request> allRequests = summitSystem.requestDao.getAllRequests();
                        if (allRequests != null) {
                            for (Request request : allRequests) {
                                System.out.println(request);
                            }
                        } else {
                            System.out.println(MSG_EMPTY_DATES_LIST);
                        }
                        break;
                    }
                    case 2: {
                        List<Request> requests = summitSystem.requestDao
                                .getRequests(summitSystem.currentUser.getId());
                        if (requests != null) {
                            for (Request request : requests) {
                                System.out.println(request);
                            }
                        } else {
                            System.out.println(MSG_EMPTY_DATES_LIST);
                        }
                        break;
                    }
                    case 3: {
                        System.out.print(MSG_INPUT_COUNTRY);
                        String country = readStringFromIn(summitSystem.reader);

                        System.out.print(MSG_INPUT_MONTH);
                        int month = Integer.parseInt(readStringFromIn(summitSystem.reader));

                        System.out.print(MSG_INPUT_DAY);
                        int day = Integer.parseInt(readStringFromIn(summitSystem.reader));

                        Request request = new Request(5, country, month, day);
                        summitSystem.requestDao.addRequest(summitSystem.currentUser.getId(), request);
                        req = summitSystem.processRequests();
                        break;
                    }
                    case 0: {
                        return;
                    }
                }
            }
            System.out.println(MSG_SUMMIT_SCHEDULED + req.getDay() + "." + req.getMonth() + " в "
                    + req.getCountry());
        }
    }

    private Request processRequests() {
        List<User> users = userDao.getAllUsers();
        List<Request> userRequests = null;

        if (users.size() > 1) {
            userRequests = requestDao.getRequests(users.get(1).getId());
        } else {
            return null;
        }

        for (Request request : userRequests) {
            if (hasUsersSameRequest(users, request)) {
                return request;
            }
        }
        return null;
    }

    private boolean hasUsersSameRequest(List<User> users, Request request) {
        for (int i = 2; i < users.size(); i++) {
            List<Request> userRequestsForEquals = requestDao.getRequests(users.get(i).getId());
            if (!hasUserSameRequest(request, userRequestsForEquals)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasUserSameRequest(Request request, List<Request> userRequestsForEquals) {
        for (Request requestEquals : userRequestsForEquals) {
            if (request.equals(requestEquals)) {
                return true;
            }
        }
        return false;
    }

    private static String readStringFromIn(BufferedReader reader) {
        String str = "";
        try {
            str = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
