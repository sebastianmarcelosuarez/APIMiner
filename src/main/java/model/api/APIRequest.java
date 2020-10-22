package model.api;

public class APIRequest {

    String userName;
    int posi;
    int posj;
    String action;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPosi() {
        return posi;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }

    public int getPosj() {
        return posj;
    }

    public void setPosj(int posj) {
        this.posj = posj;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
