package main.entity;

public class Worker {
    private int id;
    private String fullName;
    private String specialty;
    private String area;
    private float experience;
    private String telephone;
    private int user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", specialty='" + specialty + '\'' +
                ", area='" + area + '\'' +
                ", experience=" + experience +
                ", telephone='" + telephone + '\'' +
                ", user=" + user +
                '}';
    }
}
