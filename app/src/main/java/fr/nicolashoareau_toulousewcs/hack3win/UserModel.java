package fr.nicolashoareau_toulousewcs.hack3win;

public class UserModel {

    String number;
    String password;
    String status;
    String name;
    String firstname;
    String employer;

    public UserModel() {
    }

    public UserModel(String number, String password, String status, String name, String firstname, String employer) {
        this.number = number;
        this.password = password;
        this.status = status;
        this.name = name;
        this.firstname = firstname;
        this.employer = employer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }
}
