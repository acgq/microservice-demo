package com.github.zuulsvr.model;

public class UserInfo {
    private String organizationId;
    private String userId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "organizationId='" + organizationId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
