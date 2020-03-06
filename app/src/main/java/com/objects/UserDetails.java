package com.objects;

import java.io.Serializable;

public class UserDetails implements Serializable {
    private String UserID, Email, MobileNumber, Fullname, Gender, BirthDate, CountryOfResidence, CountryCode, CreatedDate,
            UpdatedDate, IsActive, IsEmailConfirmed, AddressID, IsAuthenticated;
    
    public String getUserID() {
        return UserID;
    }
    
    public void setUserID(String userID) {
        UserID = userID;
    }
    
    public String getEmail() {
        return Email;
    }
    
    public void setEmail(String email) {
        Email = email;
    }
    
    public String getMobileNumber() {
        return MobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
    
    public String getFullname() {
        return Fullname;
    }
    
    public void setFullname(String fullname) {
        Fullname = fullname;
    }
    
    public String getGender() {
        return Gender;
    }
    
    public void setGender(String gender) {
        Gender = gender;
    }
    
    public String getBirthDate() {
        return BirthDate;
    }
    
    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }
    
    public String getCountryOfResidence() {
        return CountryOfResidence;
    }
    
    public void setCountryOfResidence(String countryOfResidence) {
        CountryOfResidence = countryOfResidence;
    }
    
    public String getCountryCode() {
        return CountryCode;
    }
    
    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }
    
    public String getCreatedDate() {
        return CreatedDate;
    }
    
    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }
    
    public String getUpdatedDate() {
        return UpdatedDate;
    }
    
    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }
    
    public String getIsActive() {
        return IsActive;
    }
    
    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
    
    public String getIsEmailConfirmed() {
        return IsEmailConfirmed;
    }
    
    public void setIsEmailConfirmed(String isEmailConfirmed) {
        IsEmailConfirmed = isEmailConfirmed;
    }
    
    public String getAddressID() {
        return AddressID;
    }
    
    public void setAddressID(String addressID) {
        AddressID = addressID;
    }
    
    public String getIsAuthenticated() {
        return IsAuthenticated;
    }
    
    public void setIsAuthenticated(String isAuthenticated) {
        IsAuthenticated = isAuthenticated;
    }
}
