package ocs.com.dr_tips.util;

import ocs.com.dr_tips.model.User;

public class AppDataHolder {
    private static AppDataHolder instance;

    private User loggedInUser;

    private AppDataHolder(){
        //Remain Empty
    }

    public static AppDataHolder getInstance(){
        if(instance == null) {
            instance = new AppDataHolder();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
