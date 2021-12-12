package main.entity;

public class Sale {
    private int id;
    private int car;
    private String area;
    private float price;
    private String status;
    private int worker;
    private String requestDate;
    private int diller;
    private String comment;
    private int client;

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWorker() {
        return worker;
    }

    public void setWorker(int worker) {
        this.worker = worker;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public int getDiller() {
        return diller;
    }

    public void setDiller(int diller) {
        this.diller = diller;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Backing{" +
                "id=" + id +
                ", car=" + car +
                ", area='" + area + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", worker=" + worker +
                ", requestDate='" + requestDate + '\'' +
                ", diller=" + diller +
                ", comment='" + comment + '\'' +
                ", client=" + client +
                '}';
    }
}
