package ocs.com.dr_tips.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Randa on 3/18/2018.
 */

public class User {
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

    @SerializedName("country_id")
    private int countryId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
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
