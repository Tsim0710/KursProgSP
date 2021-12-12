package main.controllers.worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.Main;
import main.entity.Diller;
import main.entity.Sale;
import main.entity.Car;
import main.entity.Worker;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static main.Main.input;
import static main.Main.sendData;

public class MainController {

    Worker thisWorker;
    ArrayList<Diller> dillerArrayList;

    @FXML
    private TitledPane freeOrdersPane;

    @FXML
    private TableView<Sale> freeOrders;

    @FXML
    private TableColumn<Sale, Integer> freeOrderCarId;

    @FXML
    private TableColumn<Sale, Float> freeOrderPrice;

    @FXML
    private TableColumn<Sale, String> freeOrderStatus;

    @FXML
    private TableColumn<Sale, String> freeOrderDate;

    @FXML
    private TableColumn<Sale, String> freeOrderComment;

    @FXML
    private TextField viewFreeCarBrand;

    @FXML
    private TextField viewFreeCarNumber;

    @FXML
    private TextField viewFreeCarIssueYear;

    @FXML
    private TextField viewFreeCarMileage;

    @FXML
    private ListView<String> freeDetails;

    @FXML
    private ListView<String> selectedDetails;

    @FXML
    private TitledPane requestsPane;

    @FXML
    private TableView<Sale> backingTable;

    @FXML
    private TableColumn<Sale, Integer> backingCarId;

    @FXML
    private TableColumn<Sale, Float> backingPrice;

    @FXML
    private TableColumn<Sale, String> backingStatus;

    @FXML
    private TableColumn<Sale, String> backingRequestDate;

    @FXML
    private TableColumn<Sale, String> backingComment;

    @FXML
    private TextField viewCarBrand;

    @FXML
    private TextField viewCarNumber;

    @FXML
    private TextField viewCarIssueYear;

    @FXML
    private ComboBox<String> orderStatus;

    @FXML
    private TextField viewCarMileage;

    @FXML
    private Spinner<Double> priceSpinner;

    @FXML
    private Label workerFullName;

    @FXML
    private Button exitButton;

    @FXML
    void exit() throws IOException {
        exitButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/main/windows/fxml/authorization.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Салон");
        stage.show();
    }

    @FXML
    void getBackingInfo() throws IOException, ClassNotFoundException {
        if(freeOrders.getSelectionModel().getSelectedItem()!=null){
            sendData("Информация о машине");
            sendData(String.valueOf(freeOrders.getSelectionModel().getSelectedItem().getCar()));
            String carInfo = (String) input.readObject();
            Car car = new Gson().fromJson(carInfo, Car.class);
            viewFreeCarBrand.setText(car.getBrand());
            viewFreeCarIssueYear.setText(car.getIssueYear());
            viewFreeCarMileage.setText(car.getMileage());
            viewFreeCarNumber.setText(car.getStateNumber());
        }
    }

    @FXML
    void getMyBackingInfo() throws IOException, ClassNotFoundException {
        if(backingTable.getSelectionModel().getSelectedItem()!=null){
            sendData("Информация о машине");
            sendData(String.valueOf(backingTable.getSelectionModel().getSelectedItem().getCar()));
            String carInfo = (String) input.readObject();
            Car car = new Gson().fromJson(carInfo, Car.class);
            viewCarBrand.setText(car.getBrand());
            viewCarIssueYear.setText(car.getIssueYear());
            viewCarMileage.setText(car.getMileage());
            viewCarNumber.setText(car.getStateNumber());

            priceSpinner.getValueFactory().setValue(
                    (double) backingTable.getSelectionModel().getSelectedItem().getPrice());
            orderStatus.setValue(backingTable.getSelectionModel().getSelectedItem().getStatus());
        }
    }

    @FXML
    void method() throws IOException {
        if (freeOrders.getSelectionModel().getSelectedItem()!=null) {
            if (selectedDetails.getItems().size() == 3) {
                Diller firstDiller = null, secondDiller = null, thirdDiller = null;
                for (Diller diller : dillerArrayList) {
                    if (selectedDetails.getItems().get(0).split(",")[0].equals(diller.getName()) &&
                            selectedDetails.getItems().get(0).split(",")[1].trim().equals(diller.getCountry())) {
                        firstDiller = diller;
                    }
                }
                for (Diller diller : dillerArrayList) {
                    if (selectedDetails.getItems().get(1).split(",")[0].equals(diller.getName()) &&
                            selectedDetails.getItems().get(1).split(",")[1].trim().equals(diller.getCountry())) {
                        secondDiller = diller;
                    }
                }
                for (Diller diller : dillerArrayList) {
                    if (selectedDetails.getItems().get(2).split(",")[0].equals(diller.getName()) &&
                            selectedDetails.getItems().get(2).split(",")[1].trim().equals(diller.getCountry())) {
                        thirdDiller = diller;
                    }
                }

                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/main/windows/fxml/worker/method.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Салон");
                MethodController methodController = loader.getController();
                methodController.initialize(firstDiller, secondDiller, thirdDiller, freeOrders.getSelectionModel().getSelectedItem(),
                        thisWorker, this);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Выберите 3 диллера");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите заказ");
            alert.showAndWait();
        }
    }

    @FXML
    void refuse() throws IOException, ClassNotFoundException {
        if(backingTable.getSelectionModel().getSelectedItem()!=null){
            if(!backingTable.getSelectionModel().getSelectedItem().getStatus().equals("Готов")) {
                Sale sale = backingTable.getSelectionModel().getSelectedItem();
                sale.setStatus("Ожидает");
                sale.setPrice(0);
                sale.setWorker(1);
                sale.setDiller(1);
                sendData("Изменение заказа");
                sendData(new Gson().toJson(sale));
                initialize();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Вы не можете отказаться от заказа, который уже готов");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите заказ");
            alert.showAndWait();
        }
    }

    @FXML
    void saveChanges() throws IOException, ClassNotFoundException {
        if(backingTable.getSelectionModel().getSelectedItem()!=null){
            if(!backingTable.getSelectionModel().getSelectedItem().getStatus().equals("Готов")) {
                Sale sale = backingTable.getSelectionModel().getSelectedItem();
                sale.setStatus(orderStatus.getValue());
                sale.setPrice(Float.parseFloat(String.valueOf(priceSpinner.getValue())));
                sendData("Изменение заказа");
                sendData(new Gson().toJson(sale));
                initialize();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Вы не можете изменить заказ, который уже готов");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите заказ");
            alert.showAndWait();
        }
    }

    @FXML
    void selectDetail() {
        if(freeDetails.getSelectionModel().getSelectedItem()!=null) {
            if (selectedDetails.getItems().size() < 3) {
                selectedDetails.getItems().add(freeDetails.getSelectionModel().getSelectedItem());
                freeDetails.getItems().remove(freeDetails.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    void unselectDetail() {
        if(selectedDetails.getSelectionModel().getSelectedItem()!=null) {
            freeDetails.getItems().add(selectedDetails.getSelectionModel().getSelectedItem());
            selectedDetails.getItems().remove(selectedDetails.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        freeDetails.getItems().clear();
        selectedDetails.getItems().clear();
        if(thisWorker == null){
            String workerInfo = (String) Main.input.readObject();
            thisWorker = new Gson().fromJson(workerInfo, Worker.class);
        }

        workerFullName.setText(thisWorker.getFullName() + ", специалист в области '" + thisWorker.getArea() + "'");

        sendData("Свободные заказы");
        sendData(thisWorker.getArea());
        String backingsInfo = (String) Main.input.readObject();
        Type type = new TypeToken<ArrayList<Sale>>(){}.getType();
        ArrayList<Sale> saleArrayList = new Gson().fromJson(backingsInfo, type);
        ObservableList<Sale> saleObservableList = FXCollections.observableArrayList(saleArrayList);
        freeOrderCarId.setCellValueFactory(new PropertyValueFactory<>("car"));
        freeOrderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        freeOrderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        freeOrderDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        freeOrderComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        freeOrders.setItems(saleObservableList);

        sendData("Заказы работника");
        sendData(thisWorker.getId());
        backingsInfo = (String) Main.input.readObject();
        type = new TypeToken<ArrayList<Sale>>(){}.getType();
        saleArrayList = new Gson().fromJson(backingsInfo, type);
        saleObservableList = FXCollections.observableArrayList(saleArrayList);
        backingCarId.setCellValueFactory(new PropertyValueFactory<>("car"));
        backingPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        backingStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        backingRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        backingComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        backingTable.setItems(saleObservableList);

        sendData("Диллеры области");
        sendData(thisWorker.getArea());
        String detailsInfo = (String) Main.input.readObject();
        type = new TypeToken<ArrayList<Diller>>(){}.getType();
        dillerArrayList = new Gson().fromJson(detailsInfo, type);

        for(Diller diller : dillerArrayList){
            freeDetails.getItems().add(diller.getName() + ", " + diller.getCountry());
        }




        ObservableList<Double> prices = FXCollections.observableArrayList(100.0, 200.0, 300.0, 400.0, 500.0,
                600.0, 700.0, 800.0, 900.0, 1000.0);
        priceSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(prices));

        ArrayList<String> statuses = new ArrayList<>();
        statuses.add("Обрабатывается");
        statuses.add("Работа приостановлена");
        statuses.add("Готов");
        ObservableList<String> statusesObservableList = FXCollections.observableList(statuses);
        orderStatus.setItems(statusesObservableList);
    }
}