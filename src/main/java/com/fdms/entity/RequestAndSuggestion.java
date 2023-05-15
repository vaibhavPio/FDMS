package com.fdms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RequestAndSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int requestId;
    private String request;
    private String userName;
    private String name;
    private String role;
    private boolean requestStatus;

    public void setRequestStatus(boolean requestStatus) {
        this.requestStatus = requestStatus;
    }

    public boolean isRequestStatus() {
        return requestStatus;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getUserName() {
        return userName;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getRequest() {
        return request;
    }
}
