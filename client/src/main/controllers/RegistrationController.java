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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.Main;
import main.controllers.util.PatternDir;
import main.entity.Client;
import main.entity.User;

import java.io.IOException;

import static main.Main.input;
import static main.Main.sendData;

public class RegistrationController {

    @FXML
    private TextField loginField;

    @FXML
    private Button registrationButton;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField fullName;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    void authorization() throws IOException {
        registrationButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/main/windows/fxml/authorization.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Салон");
        stage.show();
    }

    @FXML
    void registration() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();
        User user = new User();
        Client client = new Client();
        if (loginField.getText().length() > 4 && passField.getText().length() > 5) {
            user.setLogin(loginField.getText());
            user.setPassword(passField.getText());
            user.setRole("Клиент");
            sendData("Проверка логина");
            sendData(gson.toJson(user));
            String result = (String) input.readObject();
            if (result.equals("null")) {
                if (PatternDir.check(PatternDir.NAME_PATTERN, fullName.getText()) &&
                        PatternDir.check(PatternDir.EMAIL_PATTERN, email.getText()) &&
                        PatternDir.check(PatternDir.PHONE, phoneNumber.getText())) {
                    try {
                        client.setFullName(fullName.getText());
                        client.setEmail(email.getText());
                        client.setTelephone(phoneNumber.getText());
                        sendData("Добавление клиента");
                        sendData(gson.toJson(user));
                        sendData(gson.toJson(client));
                        authorization();
                    } catch (NumberFormatException | IOException ignored) {
                        showAlert("Неправильный формат данных!!");
                    }
                }
                else showAlert("Проверьте правильность ввода данных!");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(result);
                alert.showAndWait();
            }
        }
        else showAlert("Логин и пароль должен быть длиной больше 5!");
    }

    private void showAlert(String str){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(str);
        alert.showAndWait();
    }

}
