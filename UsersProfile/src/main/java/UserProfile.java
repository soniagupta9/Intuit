package main.java;

/**
 * Created by sonia on 10/31/16.
 */
public class UserProfile {

    private String name;
    private String address;
    private int postalCode;

    public UserProfile() {
    }

    public UserProfile(String name, String address, int postalCode) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }
}
