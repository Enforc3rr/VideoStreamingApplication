package com.videostream.app.entities;
//To Work with the User Object received from the Database
public class UserEntity {
    private String _id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String dateOfUserJoining;
    private String countryOfUser;
    private String role;

    public UserEntity(String _id, String username, String password, String email, String firstName, String lastName, String dateOfUseJoining, String countryOfUser, String role) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfUserJoining = dateOfUseJoining;
        this.countryOfUser = countryOfUser;
        this.role = role;
    }

    public UserEntity() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfUserJoining() {
        return dateOfUserJoining;
    }

    public void setDateOfUserJoining(String dateOfUserJoining) {
        this.dateOfUserJoining = dateOfUserJoining;
    }

    public String getCountryOfUser() {
        return countryOfUser;
    }

    public void setCountryOfUser(String countryOfUser) {
        this.countryOfUser = countryOfUser;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "_id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfUseJoining='" + dateOfUserJoining + '\'' +
                ", countryOfUser='" + countryOfUser + '\'' +
                '}';
    }
}
