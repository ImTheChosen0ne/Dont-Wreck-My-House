package learn.mastery.models;

import java.math.BigDecimal;

public class Host {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private int zip;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZip() {
        return zip;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }
}
