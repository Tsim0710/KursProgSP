package main.controllers.client;

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
import main.entity.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static main.Main.input;
import static main.Main.sendData;
import static main.controllers.util.PatternDir.BRAND;
import static main.controllers.util.PatternDir.check;

public class MainController {

    Client thisClient;
    Car selectedCar;
    boolean editFlag = false;

    @FXML
    private TitledPane carPane;

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private Label clientFullName;

    @FXML
    private TableColumn<Car, String> carBrandColumn;

    @FXML
    private TableColumn<Car, String> stateNumberColumn;

    @FXML
    private TableColumn<Car, String> issueYearColumn;

    @FXML
    private TableColumn<Car, String> mileageColumn;

    @FXML
    private TextField addCarBrand;

    @FXML
    private TextField addStateNumber;

    @FXML
    private ComboBox<String> addIssueYear;

    @FXML
    private Spinner<String> addMileage;

    @FXML
    private TextField editCarBrand;

    @FXML
    private TextField editStateNumber;

    @FXML
    private ComboBox<String> editIssueYear;

    @FXML
    private Spinner<String> editMileage;

    @FXML
    private ComboBox<String> areaComboBox;

    @FXML
    private TextArea commentTextField;

    @FXML
    private TitledPane requestsPane;

    @FXML
    private TableView<Sale> backingTable;

    @FXML
    private TableColumn<Sale, Integer> backingCarId;

    @FXML
    private TableColumn<Sale, String> backingArea;

    @FXML
    private TableColumn<Sale, Float> backingPrice;

    @FXML
    private TableColumn<Sale, String> backingStatus;

    @FXML
    private TableColumn<Sale, Integer> backingWorkerId;

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
    private TextField viewCarMileage;

    @FXML
    private TextField viewWorkerFullName;

    @FXML
    private TextField viewWorkerSpecialty;

    @FXML
    private TextField viewWorkerArea;

    @FXML
    private TextField viewWorkerExperience;

    @FXML
    private TextField viewWorkerTelephone;

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
        stage.setTitle("??????????");
        stage.show();
    }

    /**
     * ?????????????? ?????????? ?????? ????????????????????
     */
    @FXML
    void clearAddPane() {
        addCarBrand.clear();
        addStateNumber.clear();
    }

    @FXML
    void clearEditPane() {
        editCarBrand.clear();
        editStateNumber.clear();
    }

    @FXML
    void clearBackingPane() {
        commentTextField.setText("");
    }

    @FXML
    void addBacking() throws IOException, ClassNotFoundException {
        if(carsTable.getSelectionModel().getSelectedItem()!=null){
            if(areaComboBox.getValue()!=null && commentTextField.getText().length()>0){
                Sale sale = new Sale();
                sale.setCar(carsTable.getSelectionModel().getSelectedItem().getId());
                sale.setArea(areaComboBox.getValue());
                sale.setPrice(0);
                sale.setWorker(1);
                sale.setDiller(1);
                sale.setStatus("??????????????");

                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

                sale.setRequestDate(formatForDateNow.format(dateNow));
                sale.setComment(commentTextField.getText());
                sale.setClient(thisClient.getId());
                sendData("?????????? ???????????? ??????????????");
                sendData(new Gson().toJson(sale));
                showAlert("??????????????????");
                clearBackingPane();
                initialize();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("????????????");
                alert.setHeaderText("?????????????????? ????????????");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("????????????");
            alert.setHeaderText("???????????????? ????????????");
            alert.showAndWait();
        }
    }

    @FXML
    void closeBacking() throws IOException, ClassNotFoundException {
        if(backingTable.getSelectionModel().getSelectedItem()!=null){
            if(!backingTable.getSelectionModel().getSelectedItem().getStatus().equals("??????????")) {
                sendData("???????????????? ????????????");
                sendData(new Gson().toJson(backingTable.getSelectionModel().getSelectedItem()));
                clearAddPane();
                initialize();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("????????????");
                alert.setHeaderText("???? ???? ???????????? ???????????????? ??????????, ?????????????? ?????? ??????????");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("????????????");
            alert.setHeaderText("???????????????? ??????????");
            alert.showAndWait();
        }
    }

    /**
     * ???????????????? ????????????
     */
    @FXML
    void deleteCar() throws IOException, ClassNotFoundException {
        if(carsTable.getSelectionModel().getSelectedItem()!=null){
            sendData("???????????????? ????????????");
            sendData(new Gson().toJson(carsTable.getSelectionModel().getSelectedItem()));
            clearAddPane();
            initialize();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("????????????");
            alert.setHeaderText("???????????????? ????????????");
            alert.showAndWait();
        }
    }

    /**
     * ???????????????????? ????????????
     */
    @FXML
    void saveCar() throws IOException, ClassNotFoundException {
        if(addCarBrand.getText().length()>0 && check(BRAND, addStateNumber.getText()) && addIssueYear.getSelectionModel().getSelectedItem()!=null){
            Car car = new Car();
            car.setBrand(addCarBrand.getText().toUpperCase());
            car.setStateNumber(addStateNumber.getText());
            car.setIssueYear(addIssueYear.getSelectionModel().getSelectedItem());
            car.setMileage(addMileage.getValue());
            car.setClientId(thisClient.getId());
            sendData("???????????????????? ????????????");
            sendData(new Gson().toJson(car));

            String response = (String) input.readObject();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("??????????");
            alert.setHeaderText(response);
            alert.showAndWait();
            clearAddPane();
            initialize();
        }
        else showAlert("???????????? ?????????? ???? ??????????:\n\tLada\n\t5555 AF-5\n\n ???????????????? ?????????????????????? ??????.????????!" +
                "\n?? ???????????????????? ?????? ???????????????????????????? ?????????? ????????????????????!!");
    }

    @FXML
    void selectCarForUpdate(){
        if(carsTable.getSelectionModel().getSelectedItem()!=null && !editFlag){
            editFlag = true;
            selectedCar = carsTable.getSelectionModel().getSelectedItem();
            editCarBrand.setText(selectedCar.getBrand());
            editStateNumber.setText(selectedCar.getStateNumber());
            editStateNumber.setEditable(false);
            editIssueYear.setValue(selectedCar.getIssueYear());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("????????????");
            alert.setHeaderText("???????????????? ????????????");
            alert.showAndWait();
        }
    }

    /**
     * ???????????????????????????? ????????????
     */
    @FXML
    void updateCar() throws IOException, ClassNotFoundException {
        if(editFlag){
            selectedCar.setBrand(editCarBrand.getText().toUpperCase());
            selectedCar.setStateNumber(editStateNumber.getText());
            selectedCar.setIssueYear(editIssueYear.getSelectionModel().getSelectedItem());
            selectedCar.setMileage(editMileage.getValue());
            System.out.println(selectedCar);
            sendData("???????????????????????????? ????????????");
            sendData(new Gson().toJson(selectedCar));
            editFlag = false;
            clearEditPane();
            initialize();
        }
    }

    @FXML
    void getBackingInfo() throws IOException, ClassNotFoundException {
        if(backingTable.getSelectionModel().getSelectedItem()!=null){
            sendData("???????????????????? ?? ????????????");
            sendData(String.valueOf(backingTable.getSelectionModel().getSelectedItem().getCar()));
            String carInfo = (String) input.readObject();
            Car car = new Gson().fromJson(carInfo, Car.class);
            viewCarBrand.setText(car.getBrand());
            viewCarIssueYear.setText(car.getIssueYear());
            viewCarMileage.setText(car.getMileage());
            viewCarNumber.setText(car.getStateNumber());

            sendData("???????????????????? ?? ??????????????????");
            sendData(backingTable.getSelectionModel().getSelectedItem().getWorker());
            String workerInfo = (String) input.readObject();
            Worker worker = new Gson().fromJson(workerInfo, Worker.class);
            viewWorkerFullName.setText(worker.getFullName());
            viewWorkerSpecialty.setText(worker.getSpecialty());
            viewWorkerExperience.setText(String.valueOf(worker.getExperience()));
            viewWorkerTelephone.setText(worker.getTelephone());
            viewWorkerArea.setText(worker.getArea());
        }
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        if(thisClient==null) {
            String clientInfo = (String) Main.input.readObject();
            thisClient = new Gson().fromJson(clientInfo, Client.class);
        }

        clientFullName.setText(thisClient.getFullName());
        for(int i = 0; i < 30; i++){
            addIssueYear.getItems().add(String.valueOf(2021 - i));
            editIssueYear.getItems().add(String.valueOf(2021 - i));
        }

        ObservableList<String> mileageData = FXCollections.observableArrayList(
                "0-50 ??????. ????", "51-100 ??????. ????", "101-150 ??????. ????", "151-200 ??????. ????",
                "201-250 ??????. ????", "251-300 ??????. ????", "301-350 ??????. ????", "351-400 ??????. ????",
                "401-450 ??????. ????", "451-500 ??????. ????");
        addMileage.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(mileageData));
        editMileage.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(mileageData));

        sendData("???????????? ??????????????");
        sendData(String.valueOf(thisClient.getId()));
        String carsInfo = (String) Main.input.readObject();
        System.out.println(carsInfo);
        Type type = new TypeToken<ArrayList<Car>>(){}.getType();
        ArrayList<Car> carsArrayList = new Gson().fromJson(carsInfo, type);
        ObservableList<Car> carsObservableList = FXCollections.observableArrayList(carsArrayList);
        carBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        stateNumberColumn.setCellValueFactory(new PropertyValueFactory<>("stateNumber"));
        issueYearColumn.setCellValueFactory(new PropertyValueFactory<>("issueYear"));
        mileageColumn.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        carsTable.setItems(carsObservableList);

        sendData("?????????????? ??????????");
        String areasInfo = (String) Main.input.readObject();
        type = new TypeToken<ArrayList<Area>>(){}.getType();
        ArrayList<Area> areasArrayList = new Gson().fromJson(areasInfo, type);
        areaComboBox.getItems().clear();
        for (Area area : areasArrayList) {
            areaComboBox.getItems().add(area.getArea());
        }

        sendData("???????????? ??????????????");
        sendData(String.valueOf(thisClient.getId()));
        String backingsInfo = (String) Main.input.readObject();
        System.out.println(backingsInfo);
        type = new TypeToken<ArrayList<Sale>>(){}.getType();
        ArrayList<Sale> saleArrayList = new Gson().fromJson(backingsInfo, type);
        ObservableList<Sale> saleObservableList = FXCollections.observableArrayList(saleArrayList);
        backingCarId.setCellValueFactory(new PropertyValueFactory<>("car"));
        backingArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        backingPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        backingStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        backingWorkerId.setCellValueFactory(new PropertyValueFactory<>("worker"));
        backingRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        backingComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        backingTable.setItems(saleObservableList);

    }

    private void showAlert(String str){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("????????????");
        alert.setHeaderText(str);
        alert.showAndWait();
    }
}
