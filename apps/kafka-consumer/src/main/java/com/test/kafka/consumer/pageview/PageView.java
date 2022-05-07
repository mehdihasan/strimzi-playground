package com.test.kafka.consumer.pageview;

import java.util.Date;

public class PageView {

    private String userName;
    private String page;
    private String browser;
    private Date viewDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    @Override
    public String toString() {
        return "PageView{" +
                "userName='" + userName + '\'' +
                ", page='" + page + '\'' +
                ", browser='" + browser + '\'' +
                ", viewDate=" + viewDate +
                '}';
    }
}
