package model.api;

import model.Box;

import java.util.List;

public class APIResponse {

    String userName;
    List<Box> boxList;
    Boolean win;
    Boolean lose;
    String status;

    public List<Box> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<Box> boxList) {
        this.boxList = boxList;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public Boolean getLose() {
        return lose;
    }

    public void setLose(Boolean lose) {
        this.lose = lose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
