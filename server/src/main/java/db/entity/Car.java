package db.entity;

public class Car {
    private int id;
    private String brand;
    private String stateNumber;
    private String issueYear;
    private String mileage;
    private int clientId;

    public Car(int id, String brand, String stateNumber, String issueYear, String mileage, int clientId) {
        this.id = id;
        this.brand = brand;
        this.stateNumber = stateNumber;
        this.issueYear = issueYear;
        this.mileage = mileage;
        this.clientId = clientId;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public String getIssueYear() {
        return issueYear;
    }

    public void setIssueYear(String issueYear) {
        this.issueYear = issueYear;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", stateNumber='" + stateNumber + '\'' +
                ", issueYear='" + issueYear + '\'' +
                ", mileage='" + mileage + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
