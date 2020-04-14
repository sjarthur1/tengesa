package com.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class UserAddresses implements Serializable, Parcelable {
    private String AddressID, UserID, Fullname, CountryCode, Country_Code, Mobile_Number, Country, City, Area,
            StreetNameOrNo, Address1, Address2, NearestLandmark, LocationType, ShppingNote;
    
    public UserAddresses(){
    
    }
    
    public UserAddresses(Parcel in) {
        AddressID = in.readString();
        UserID = in.readString();
        Fullname = in.readString();
        CountryCode = in.readString();
        Country_Code = in.readString();
        Mobile_Number = in.readString();
        Country = in.readString();
        City = in.readString();
        Area = in.readString();
        StreetNameOrNo = in.readString();
        Address1 = in.readString();
        Address2 = in.readString();
        NearestLandmark = in.readString();
        LocationType = in.readString();
        ShppingNote = in.readString();
    }
    
    public static final Creator<UserAddresses> CREATOR = new Creator<UserAddresses>() {
        @Override
        public UserAddresses createFromParcel(Parcel in) {
            return new UserAddresses(in);
        }
        
        @Override
        public UserAddresses[] newArray(int size) {
            return new UserAddresses[size];
        }
    };
    
    public String getAddressID() {
        return AddressID;
    }
    
    public void setAddressID(String addressID) {
        AddressID = addressID;
    }
    
    public String getUserID() {
        return UserID;
    }
    
    public void setUserID(String userID) {
        UserID = userID;
    }
    
    public String getFullname() {
        return Fullname;
    }
    
    public void setFullname(String fullname) {
        Fullname = fullname;
    }
    
    public String getCountry_Code() {
        return Country_Code;
    }
    
    public void setCountry_Code(String country_Code) {
        Country_Code = country_Code;
    }
    
    public String getCountryCode() {
        return CountryCode;
    }
    
    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }
    
    public String getMobile_Number() {
        return Mobile_Number;
    }
    
    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }
    
    public String getCountry() {
        return Country;
    }
    
    public void setCountry(String country) {
        Country = country;
    }
    
    public String getCity() {
        return City;
    }
    
    public void setCity(String city) {
        City = city;
    }
    
    public String getArea() {
        return Area;
    }
    
    public void setArea(String area) {
        Area = area;
    }
    
    public String getStreetNameOrNo() {
        return StreetNameOrNo;
    }
    
    public void setStreetNameOrNo(String streetNameOrNo) {
        StreetNameOrNo = streetNameOrNo;
    }
    
    public String getAddress1() {
        return Address1;
    }
    
    public void setAddress1(String address1) {
        Address1 = address1;
    }
    
    public String getAddress2() {
        return Address2;
    }
    
    public void setAddress2(String address2) {
        Address2 = address2;
    }
    
    public String getNearestLandmark() {
        return NearestLandmark;
    }
    
    public void setNearestLandmark(String nearestLandmark) {
        NearestLandmark = nearestLandmark;
    }
    
    public String getLocationType() {
        return LocationType;
    }
    
    public void setLocationType(String locationType) {
        LocationType = locationType;
    }
    
    public String getShippingNote() {
        return ShppingNote;
    }
    
    public void setShippingNote(String shippingNote) {
        ShppingNote = shippingNote;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
    
        parcel.writeString(AddressID);
        parcel.writeString(UserID);
        parcel.writeString(Fullname);
        parcel.writeString(CountryCode);
        parcel.writeString(Country_Code);
        parcel.writeString(Mobile_Number);
        parcel.writeString(Country);
        parcel.writeString(City);
        parcel.writeString(Area);
        parcel.writeString(StreetNameOrNo);
        parcel.writeString(Address1);
        parcel.writeString(Address2);
        parcel.writeString(NearestLandmark);
        parcel.writeString(LocationType);
        parcel.writeString(ShppingNote);
    }
}
