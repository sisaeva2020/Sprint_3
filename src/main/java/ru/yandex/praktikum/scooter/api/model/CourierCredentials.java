package ru.yandex.praktikum.scooter.api.model;

public class CourierCredentials {
    public String login;
    public String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "CourierCredentials {login:"+login+",password:"+password+"}";
    }
}