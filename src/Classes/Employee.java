package Classes;

public class Employee {
    private int employeeID;
    private String employeeName;
    private double salary;
    private String location;
    private String department;
    private String employeePosition;
    private String photoURL;

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Employee(int employeeID, String employeeName, double salary, String location, String department, String employeePosition, String photoURL) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.salary = salary;
        this.location = location;
        this.department = department;
        this.employeePosition = employeePosition;
        this.photoURL = photoURL;
    }
}
