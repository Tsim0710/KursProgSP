package main.controllers;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import main.entity.User;

import java.io.IOException;

import static main.Main.input;
import static main.Main.sendData;

public class AuthorizationController {

    @FXML
    private TextField loginField;

    @FXML
    private Button button;

    @FXML
    private PasswordField passField;

    @FXML
    void login() throws IOException, ClassNotFoundException {
        User user = new User();
        if(loginField.getText().length()>0 && passField.getText().length()>0) {
            user.setLogin(loginField.getText());
            user.setPassword(passField.getText());
            sendData("Авторизация");
            sendData(new Gson().toJson(user));

            String serverResponse = (String) input.readObject();
            if (serverResponse.equals("null")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неверный логин или пароль");
                alert.showAndWait();
            } else {
                user = new Gson().fromJson(serverResponse, User.class);
                switch (user.getRole()) {
                    case "Админ":
                        button.getScene().getWindow().hide();
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/main/windows/fxml/admin/main.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Салон");
                        stage.show();
                        break;
                    case "Работник":
                        button.getScene().getWindow().hide();
                        stage = new Stage();
                        loader = new FXMLLoader(Main.class.getResource("/main/windows/fxml/worker/main.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Салон");
                        stage.show();
                        break;
                    case "Клиент":
                        button.getScene().getWindow().hide();
                        stage = new Stage();
                        loader = new FXMLLoader(Main.class.getResource("/main/windows/fxml/client/main.fxml"));
                        root = loader.load();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Салон");
                        stage.show();
                        break;
                }
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Введите все данные");
            alert.showAndWait();
        }
    }

    @FXML
    void registration() throws IOException {
        button.getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/main/windows/fxml/registration.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Салон");
        stage.show();
    }

}