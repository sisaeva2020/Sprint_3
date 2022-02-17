package ru.yandex.praktikum.scooter.api.model;



public class Order {
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public String rentTime;
    public String deliveryDate;
    public String comment;
    public String[] color;

    public Order (String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

}
