package pipl.ynov.com.schoolpierre.Models;

public class School {
    private int id = 0;
    private String name;
    private String address;
    private String zip_code;
    private String city;
    private String openings;
    private String phone;
    private String email;

    private double latitude;
    private double longitude;
    private int nb_student;
    private String status;

    public School(String name, double latitude, double longitude, String status, String address, String zip_code, String city, String openings, String phone, String email, int nb_student) {
        this.name = name;
        this.address = address;
        this.zip_code = zip_code;
        this.city = city;
        this.openings = openings;
        this.phone = phone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nb_student = nb_student;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenings() {
        return openings;
    }

    public void setOpenings(String openings) {
        this.openings = openings;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNb_student() {
        return nb_student;
    }

    public void setNb_student(int nb_student) {
        this.nb_student = nb_student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
