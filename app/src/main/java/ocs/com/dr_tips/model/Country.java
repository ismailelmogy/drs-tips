package ocs.com.dr_tips.model;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("name")
    private String name;
    @SerializedName("dial_code")
    private String mobileCode;
    @SerializedName("code")
    private String countryCode;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
