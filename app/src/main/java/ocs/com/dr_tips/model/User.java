package ocs.com.dr_tips.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Randa on 3/18/2018.
 */

public class User {
    private boolean isVerified;

    @SerializedName("name")
    private String name;
    @SerializedName("profile_pic")
    private String profilePictureInBase64;

    public String getProfilePictureInBase64() {
        return profilePictureInBase64;
    }

    public void setProfilePictureInBase64(String profilePictureInBase64) {
        this.profilePictureInBase64 = profilePictureInBase64;
    }

    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("mobile_no")
    private String mobileNumber;
    @SerializedName("speciality")
    private String speciality;
    @SerializedName("work_place")
    private String workPlace;
    @SerializedName("title")
    private String title;
    @SerializedName("is_paid")
    private boolean isPaid;
    @SerializedName("package_id")
    private int packageId;


    public boolean getIsVerified(){
        return isVerified;
    }
    public void setVerified(){
        this.isVerified=isVerified;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryId() {
        return countryCode;
    }

    public void setCountryId(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
}
