package ocs.com.dr_tips.model;

/**
 * Created by Randa on 3/18/2018.
 */

public class User {
    private String name;
    private boolean isVerified;

    public String getName() {
        return name;
    }
    public void setName(){
        this.name=name;
    }
    public boolean getIsVerified(){
        return isVerified;
    }
    public void setVerified(){
        this.isVerified=isVerified;
    }
}
