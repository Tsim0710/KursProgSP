package main.controllers.worker;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import main.entity.Diller;
import main.entity.Sale;
import main.entity.Worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static main.Main.sendData;

public class MethodController {

    Diller firstDiller, secondDiller, thirdDiller;
    Sale sale;
    Worker worker;
    MainController mainController;

    @FXML
    private GridPane secondGrid;

    @FXML
    private Spinner<Integer> eb11;

    @FXML
    private Spinner<Integer> eb12;

    @FXML
    private Spinner<Integer> eb13;

    @FXML
    private Spinner<Integer> eb23;

    @FXML
    private Spinner<Integer> eb22;

    @FXML
    private Spinner<Integer> eb21;

    @FXML
    private Spinner<Integer> eb32;

    @FXML
    private Spinner<Integer> eb31;

    @FXML
    private Spinner<Integer> eb33;

    @FXML
    private Label firstDetailInfo;

    @FXML
    private Label secondDetailInfo;

    @FXML
    private Label thirdDetailInfo;

    @FXML
    private GridPane thirdGrid;

    @FXML
    private TextField result;

    @FXML
    private Spinner<Double> price;

    @FXML
    private Button saveButton;

    @FXML
    void acceptOrder() throws IOException, ClassNotFoundException {
        switch (result.getText()){
            case "Диллер 1":
                sale.setDiller(firstDiller.getId());
                break;
            case "Диллер 2":
                sale.setDiller(secondDiller.getId());
                break;
            case "Диллер 3":
                sale.setDiller(thirdDiller.getId());
                break;
        }

        sale.setPrice(price.getValue().floatValue());
        sale.setWorker(worker.getId());
        sale.setStatus("Обрабатывается");

        sendData("Изменение заказа");
        sendData(new Gson().toJson(sale));
        save();
    }

    @FXML
    void calculate() {
        secondGrid.setDisable(true);
        thirdGrid.setDisable(false);
        int[][] array = new int[3][3];
        int[] min = new int[3];
        array[0][0] = eb11.getValue();
        array[0][1] = eb12.getValue();
        array[0][2] = eb13.getValue();
        array[1][0] = eb21.getValue();
        array[1][1] = eb22.getValue();
        array[1][2] = eb23.getValue();
        array[2][0] = eb31.getValue();
        array[2][1] = eb32.getValue();
        array[2][2] = eb33.getValue();

        for (int i = 0; i < 3; i++){
            min[i] = array[i][0];
            for (int j = 1; j < 3; j++){
                if(array[i][j] < min[i]) {
                    min [i] = array[i][j];
                }
            }
        }


        int max = min[0], indMax = 0;
        for (int j = 1; j < 3; j++){
            if(max < min[j]) {
                indMax = j;
                max = min[j];
            }
        }

        indMax++;

        switch (indMax){
            case 1:
                result.setText("Диллер 1");
                break;
            case 2:
                result.setText("Диллер 2");
                break;
            case 3:
                result.setText("Диллер 3");
                break;
        }
    }

    @FXML
    void save() throws IOException, ClassNotFoundException {
        String date = new Date().toString();
        String fileName = "Отчёт по анализу.txt";
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file, false);
        String text = "";
        text += "Матрица последствий:\n" +
                "диллер 1: благоприятно - " + eb11.getValue() + " нейтрально - " + eb12.getValue() + " ужасно - " + eb13.getValue() + "\n" +
                "диллер 2: благоприятно - " + eb21.getValue() + " нейтрально - " + eb22.getValue() + " ужасно - " + eb23.getValue() + "\n" +
                "диллер 3: благоприятно - " + eb33.getValue() + " нейтрально - " + eb32.getValue() + " ужасно - " + eb33.getValue() + "\n\n";
        text+= "Работник - " + worker.getFullName() + "\n";
        text+= "Выбран " + result.getText() + "\n\n";
        text+= "Анализ сделан " + date;
        writer.write(text);
        writer.flush();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText("Отчёт сохранен");
        alert.showAndWait();
        saveButton.getScene().getWindow().hide();
        mainController.initialize();
    }


    @FXML
    void initialize(Diller firstDiller, Diller secondDiller, Diller thirdDiller, Sale sale, Worker worker, MainController mainController) {
        thirdGrid.setDisable(true);

        this.firstDiller = firstDiller;
        this.secondDiller = secondDiller;
        this.thirdDiller = thirdDiller;
        this.sale = sale;
        this.worker = worker;
        this.mainController = mainController;

        firstDetailInfo.setText("Диллер 1: " + firstDiller.getName() + ", " + firstDiller.getCountry() + ", " + firstDiller.getPrice());
        secondDetailInfo.setText("Диллер 2: " + secondDiller.getName() + ", " + secondDiller.getCountry() + ", " + secondDiller.getPrice());
        thirdDetailInfo.setText("Диллер 3: " + thirdDiller.getName() + ", " + thirdDiller.getCountry() + ", " + thirdDiller.getPrice());

        ObservableList<Integer> data = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        eb11.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));
        eb12.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));
        eb13.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));
        eb21.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));
        eb22.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));
        eb23.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));
        eb31.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));
        eb32.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));
        eb33.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(data));

        ObservableList<Double> prices = FXCollections.observableArrayList(100.0, 200.0, 300.0, 400.0, 500.0,
                600.0, 700.0, 800.0, 900.0, 1000.0);
        price.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(prices));
    }
}