package com.objects.list_objects;

public class AddressObject {
    private String block, street, city, country;
    
    public AddressObject ( String block, String street, String city, String country ){
        this.block = block;
        this.street = street;
        this.city = city;
        this.country = country;
    }
    
    public String getBlock() {
        return block;
    }
    
    public void setBlock(String block) {
        this.block = block;
    }
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
}
