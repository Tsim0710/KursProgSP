package main.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import main.controllers.util.PatternDir;
import main.entity.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static main.Main.input;
import static main.Main.sendData;

public class MainController {

    @FXML
    private TitledPane workersPane;

    @FXML
    private TableView<Worker> workersTable;

    @FXML
    private TableColumn<Worker, Integer> workerId;

    @FXML
    private TableColumn<Worker, String> workerFullName;

    @FXML
    private TableColumn<Worker, String> workerSpecialty;

    @FXML
    private TableColumn<Worker, Float> workerExperience;

    @FXML
    private TableColumn<Worker, String> workerTelephone;

    @FXML
    private ComboBox<String> workersAreaComboBox;

    @FXML
    private TextField addWorkerFullName;

    @FXML
    private TextField addWorkerSpecialty;

    @FXML
    private ComboBox<String> addWorkerArea;

    @FXML
    private TextField addWorkerExperience;

    @FXML
    private TextField addWorkerTelephone;

    @FXML
    private TextField addWorkerLogin;

    @FXML
    private PasswordField addWorkerPassword;

    @FXML
    private TextField editWorkerFullName;

    @FXML
    private TextField editWorkerSpecialty;

    @FXML
    private ComboBox<String> editWorkerArea;

    @FXML
    private TextField editWorkerExperience;

    @FXML
    private TextField editWorkerTelephone;

    @FXML
    private TableView<Client> clientsTable;

    @FXML
    private TableColumn<Client, Integer> clientId;

    @FXML
    private TableColumn<Client, String> clientFullName;

    @FXML
    private TableColumn<Client, String> clientEmail;

    @FXML
    private TableColumn<Client, String> clientTelephone;

    @FXML
    private TextField addClientFullName;

    @FXML
    private TextField addClientEmail;

    @FXML
    private TextField addClientTelephone;

    @FXML
    private TextField addClientLogin;

    @FXML
    private TextField addClientPassword;

    @FXML
    private TextField editClientFullName;

    @FXML
    private TextField editClientEmail;

    @FXML
    private TextField editClientTelephone;

    @FXML
    private ListView<String> areasListView;

    @FXML
    private TextField newAreaName;

    @FXML
    private TextField addDetailName;

    @FXML
    private TextField addDetailCountry;

    @FXML
    private ComboBox<String> addDetailArea;

    @FXML
    private TextField addDetailPrice;

    @FXML
    private TextField editDetailName;

    @FXML
    private TextField editDetailCountry;

    @FXML
    private ComboBox<String> editDetailArea;

    @FXML
    private TextField editDetailPrice;

    @FXML
    private TableView<Diller> detailsTable;

    @FXML
    private TableColumn<Diller, String> detailName;

    @FXML
    private TableColumn<Diller, String> detailCountry;

    @FXML
    private TableColumn<Diller, Float> detailPrice;

    @FXML
    private Button exitButton;

    @FXML
    private Text workerAllBackings;

    @FXML
    private Text workerProccesingBackings;

    @FXML
    private Text workerReadyBackings;

    @FXML
    private Text clientAllBackings;

    @FXML
    private Text clientProccesingBackings;

    @FXML
    private Text clientReadyBackings;

    @FXML
    private Text clientWaitBackings;

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
    void clientGraph() throws IOException, ClassNotFoundException {
        if (clientsTable.getSelectionModel().getSelectedItem() != null) {

            sendData("Машины клиента");
            sendData(String.valueOf(clientsTable.getSelectionModel().getSelectedItem().getId()));
            String carsInfo = (String) Main.input.readObject();
            System.out.println(carsInfo);
            Type type = new TypeToken<ArrayList<Car>>(){}.getType();
            ArrayList<Car> carsArrayList = new Gson().fromJson(carsInfo, type);
            ArrayList<String> carsBrand = new ArrayList<>();
            Set<String> uniqueBrands = new HashSet<String>();

            for (Car car : carsArrayList) {
                String brand = car.getBrand();
                carsBrand.add(brand);
                uniqueBrands.add(brand);
            }

            int[] brandCount = new int[uniqueBrands.size()];
            for(int i = 0; i < uniqueBrands.size(); i++){
                brandCount[i] = 0;
                for (String s : carsBrand) {
                    if (s.equals(uniqueBrands.toArray()[i])) {
                        brandCount[i]++;
                    }
                }
            }

            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Марка автомобиля");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Количество");

            BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
            dataSeries1.setName("количетсво автомобилей марки");

            for(int i = 0; i < uniqueBrands.size(); i++){
                dataSeries1.getData().add(new XYChart.Data<String, Number>(uniqueBrands.toArray()[i].toString(),  brandCount[i]));
            }

            barChart.getData().add(dataSeries1);

            barChart.setTitle("Автомобили клиента");

            VBox vbox = new VBox(barChart);

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Салон");
            Scene scene = new Scene(vbox, 600, 300);

            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setHeight(400);
            primaryStage.setWidth(600);
            primaryStage.setTitle("Салон");
            primaryStage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите клиента");
            alert.showAndWait();
        }
    }

    @FXML
    void workerGraph() throws IOException, ClassNotFoundException {
        if (workersTable.getSelectionModel().getSelectedItem() != null) {

            sendData("Заказы работника");
            sendData(workersTable.getSelectionModel().getSelectedItem().getId());
            String backingsInfo = (String) Main.input.readObject();
            Type type = new TypeToken<ArrayList<Sale>>(){}.getType();
            ArrayList<Sale> saleArrayList = new Gson().fromJson(backingsInfo, type);

            ArrayList<String> dates = new ArrayList<>();
            Set<String> uniqueDate = new HashSet<String>();

            for (Sale sale : saleArrayList) {
                String date = sale.getRequestDate();
                dates.add(date);
                uniqueDate.add(date);
            }

            int[] count = new int[uniqueDate.size()];
            for(int i = 0; i < uniqueDate.size(); i++){
                count[i] = 0;
                for (String s : dates) {
                    if (s.equals(uniqueDate.toArray()[i])) {
                        count[i]++;
                    }
                }
            }

            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Дата");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Количество заказов");

            LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
            dataSeries1.setName("количество заказов");

            for(int i = 0; i < uniqueDate.size(); i++){
                dataSeries1.getData().add(new XYChart.Data<String, Number>(uniqueDate.toArray()[i].toString(),  count[i]));
            }

            lineChart.getData().add(dataSeries1);

            lineChart.setTitle("Заказы работника");

            VBox vbox = new VBox(lineChart);

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Салон");
            Scene scene = new Scene(vbox, 600, 300);

            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setHeight(400);
            primaryStage.setWidth(600);
            primaryStage.setTitle("Салон");

            primaryStage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите работника");
            alert.showAndWait();
        }
    }

    @FXML
    void carsGraph() throws IOException, ClassNotFoundException {

        sendData("Все машины");
        String carsInfo = (String) Main.input.readObject();
        Type type = new TypeToken<ArrayList<Car>>(){}.getType();
        ArrayList<Car> carsArrayList = new Gson().fromJson(carsInfo, type);
        ArrayList<String> carsBrand = new ArrayList<>();
        Set<String> uniqueBrands = new HashSet<String>();

        for (Car car : carsArrayList) {
            String brand = car.getBrand();
            carsBrand.add(brand);
            uniqueBrands.add(brand);
        }

        int[] brandCount = new int[uniqueBrands.size()];
        for(int i = 0; i < uniqueBrands.size(); i++){
            brandCount[i] = 0;
            for (String s : carsBrand) {
                if (s.equals(uniqueBrands.toArray()[i])) {
                    brandCount[i]++;
                }
            }
        }

        Stage stage = new Stage();
        PieChart pieChart = new PieChart();

        for(int i = 0; i < uniqueBrands.size(); i++){
            PieChart.Data slice = new PieChart.Data(uniqueBrands.toArray()[i].toString(), brandCount[i]);
            pieChart.getData().add(slice);
        }

        pieChart.setLegendSide(Side.LEFT);

        stage.setTitle("Все автомобили");
        StackPane root = new StackPane(pieChart);

        Scene scene = new Scene(root, 400, 400);

        stage.setScene(scene);

        stage.setTitle("Салон");
        stage.show();
    }

    @FXML
    void addWorker() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();
        User user = new User();
        Worker worker = new Worker();
        if (addWorkerLogin.getText().length() > 4 && addWorkerPassword.getText().length() > 5) {
            user.setLogin(addWorkerLogin.getText());
            user.setPassword(addWorkerPassword.getText());
            user.setRole("Работник");
            sendData("Проверка логина");
            sendData(gson.toJson(user));
            String result = (String) input.readObject();
            if (result.equals("null")) {
                if (PatternDir.check(PatternDir.NAME_PATTERN, addWorkerFullName.getText()) &&
                        addWorkerSpecialty.getText().length() > 0 && addWorkerArea.getSelectionModel().getSelectedItem() != null &&
                        PatternDir.check(PatternDir.PHONE, addWorkerTelephone.getText())) {
                    try {
                        worker.setFullName(addWorkerFullName.getText());
                        worker.setSpecialty(addWorkerSpecialty.getText());
                        worker.setArea(addWorkerArea.getSelectionModel().getSelectedItem());
                        worker.setExperience(Float.parseFloat(addWorkerExperience.getText()));
                        worker.setTelephone(addWorkerTelephone.getText());
                        sendData("Добавление работника");
                        sendData(gson.toJson(user));
                        sendData(gson.toJson(worker));
                        clearAddWorkerPane();
                        initialize();
                    } catch (NumberFormatException ignored) {
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(result);
                alert.showAndWait();
            }
        }
    }

    @FXML
    void selectWorker() throws IOException, ClassNotFoundException {
        if (workersTable.getSelectionModel().getSelectedItem() != null) {
            Worker worker = workersTable.getSelectionModel().getSelectedItem();
            editWorkerFullName.setText(worker.getFullName());
            editWorkerSpecialty.setText(worker.getSpecialty());
            editWorkerArea.setValue(worker.getArea());
            editWorkerExperience.setText(String.valueOf(worker.getExperience()));
            editWorkerTelephone.setText(worker.getTelephone());

            int a = 0, b = 0;

            sendData("Заказы работника");
            sendData(workersTable.getSelectionModel().getSelectedItem().getId());
            String backingsInfo = (String) Main.input.readObject();
            Type type = new TypeToken<ArrayList<Sale>>(){}.getType();
            ArrayList<Sale> saleArrayList = new Gson().fromJson(backingsInfo, type);
            for(Sale sale : saleArrayList){
                if(sale.getStatus().equals("Готов"))
                    b++;
                else
                    a++;
            }

            workerAllBackings.setText("Всего " + saleArrayList.size() + " заказов");
            workerProccesingBackings.setText("В работе " + a + " заказов");
            workerReadyBackings.setText("Готово " + b + " заказов");
        }
    }

    @FXML
    void updateWorker() throws IOException, ClassNotFoundException {
        if (workersTable.getSelectionModel().getSelectedItem() != null) {
            Worker worker = workersTable.getSelectionModel().getSelectedItem();
            if (PatternDir.check(PatternDir.NAME_PATTERN, editWorkerFullName.getText()) &&
                    editWorkerSpecialty.getText().length() > 0 && editWorkerArea.getSelectionModel().getSelectedItem() != null &&
                    PatternDir.check(PatternDir.PHONE, editWorkerTelephone.getText())) {
                try {
                    worker.setFullName(editWorkerFullName.getText());
                    worker.setSpecialty(editWorkerSpecialty.getText());
                    worker.setArea(editWorkerArea.getSelectionModel().getSelectedItem());
                    worker.setExperience(Float.parseFloat(editWorkerExperience.getText()));
                    worker.setTelephone(editWorkerTelephone.getText());
                    sendData("Изменение работника");
                    sendData(new Gson().toJson(worker));
                    clearEditWorkerPane();
                    initialize();
                } catch (NumberFormatException ignored) {
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите работника");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteWorker() throws IOException, ClassNotFoundException {
        if (workersTable.getSelectionModel().getSelectedItem() != null) {
            sendData("Удаление аккаунта");
            sendData(workersTable.getSelectionModel().getSelectedItem().getUser());
            sendData(workersTable.getSelectionModel().getSelectedItem().getId());
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите работника");
            alert.showAndWait();
        }
    }

    @FXML
    void clearAddWorkerPane() {
        addWorkerFullName.clear();
        addWorkerSpecialty.clear();
        addWorkerExperience.clear();
        addWorkerTelephone.clear();
        addWorkerLogin.clear();
        addWorkerPassword.clear();
    }

    @FXML
    void clearEditWorkerPane() {
        editWorkerFullName.clear();
        editWorkerSpecialty.clear();
        editWorkerExperience.clear();
        editWorkerTelephone.clear();
    }

    @FXML
    void addClient() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();
        User user = new User();
        Client client = new Client();
        if (addClientLogin.getText().length() > 4 && addClientPassword.getText().length() > 5) {
            user.setLogin(addClientLogin.getText());
            user.setPassword(addClientPassword.getText());
            user.setRole("Клиент");
            sendData("Проверка логина");
            sendData(gson.toJson(user));
            String result = (String) input.readObject();
            if (result.equals("null")) {
                if (PatternDir.check(PatternDir.NAME_PATTERN, addClientFullName.getText()) &&
                        PatternDir.check(PatternDir.EMAIL_PATTERN, addClientEmail.getText()) &&
                        PatternDir.check(PatternDir.PHONE, addClientTelephone.getText())) {
                    try {
                        client.setFullName(addClientFullName.getText());
                        client.setEmail(addClientEmail.getText());
                        client.setTelephone(addClientTelephone.getText());
                        sendData("Добавление клиента");
                        sendData(gson.toJson(user));
                        sendData(gson.toJson(client));
                        clearAddClientPane();
                        initialize();
                    } catch (NumberFormatException ignored) {
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(result);
                alert.showAndWait();
            }
        }
    }

    @FXML
    void selectClient() throws IOException, ClassNotFoundException {
        if (clientsTable.getSelectionModel().getSelectedItem() != null) {
            Client client = clientsTable.getSelectionModel().getSelectedItem();
            editClientFullName.setText(client.getFullName());
            editClientTelephone.setText(client.getTelephone());
            editClientEmail.setText(client.getEmail());

            int a = 0, b = 0, c = 0;

            sendData("Заявки клиента");
            sendData(String.valueOf(clientsTable.getSelectionModel().getSelectedItem().getId()));
            String backingsInfo = (String) Main.input.readObject();
            Type type = new TypeToken<ArrayList<Sale>>(){}.getType();
            ArrayList<Sale> saleArrayList = new Gson().fromJson(backingsInfo, type);
            for(Sale sale : saleArrayList){
                if(sale.getStatus().equals("Готов"))
                    b++;
                else
                if(sale.getStatus().equals("Ожидает"))
                    a++;
                else
                    c++;
            }

            clientAllBackings.setText("Всего " + saleArrayList.size() + " заказов");
            clientWaitBackings.setText("Ожидают " + a + " заказов");
            clientProccesingBackings.setText("В работе " + c + " заказов");
            clientReadyBackings.setText("Готово " + b + " заказов");
        }
    }

    @FXML
    void updateClient() {
        if (clientsTable.getSelectionModel().getSelectedItem() != null) {
            Client client = clientsTable.getSelectionModel().getSelectedItem();
            if (PatternDir.check(PatternDir.NAME_PATTERN, editClientFullName.getText()) &&
                    PatternDir.check(PatternDir.EMAIL_PATTERN, editClientEmail.getText()) &&
                    PatternDir.check(PatternDir.PHONE, editClientTelephone.getText())) {
                try {
                    client.setFullName(editClientFullName.getText());
                    client.setEmail(editClientEmail.getText());
                    client.setTelephone(editClientTelephone.getText());
                    sendData("Изменение клиента");
                    sendData(new Gson().toJson(client));
                    clearEditClientPane();
                    initialize();
                } catch (NumberFormatException | IOException | ClassNotFoundException ignored) {
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите клиента");
            alert.showAndWait();
        }
    }

    @FXML
    void clearAddClientPane() {
        addClientLogin.clear();
        addClientPassword.clear();
        addClientFullName.clear();
        addClientTelephone.clear();
        addClientEmail.clear();
    }

    @FXML
    void clearEditClientPane() {
        editClientFullName.clear();
        editClientEmail.clear();
        editClientTelephone.clear();
    }

    @FXML
    void deleteClient() throws IOException, ClassNotFoundException {
        if (clientsTable.getSelectionModel().getSelectedItem() != null) {
            sendData("Удаление клиента");
            sendData(clientsTable.getSelectionModel().getSelectedItem().getUser());
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите клиента");
            alert.showAndWait();
        }
    }

    @FXML
    void addArea() throws IOException, ClassNotFoundException {
        boolean flag = true;
        if (PatternDir.check(PatternDir.AREA, newAreaName.getText())) {
            for (int i = 0; i < areasListView.getItems().size(); i++) {
                if (newAreaName.getText().equals(areasListView.getItems().get(i))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Такая область уже добавлена");
                    alert.showAndWait();
                    flag = false;
                    break;
                }
            }
            if (flag) {
                Area area = new Area(newAreaName.getText());
                sendData("Добавление области");
                sendData(new Gson().toJson(area));
                newAreaName.clear();
                initialize();
            }
        }
    }

    @FXML
    void deleteArea() throws IOException, ClassNotFoundException {
        if (areasListView.getSelectionModel().getSelectedItem() != null) {
            sendData("Удаление области");
            sendData(new Gson().toJson(new Area(areasListView.getSelectionModel().getSelectedItem())));
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите область");
            alert.showAndWait();
        }
    }

    @FXML
    void addDetail() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();
        Diller diller = new Diller();
        if (addDetailName.getText().length() > 2 && addDetailArea.getSelectionModel().getSelectedItem() != null && PatternDir.check(PatternDir.COUNTRY, addDetailCountry.getText())) {
            try {
                diller.setName(addDetailName.getText());
                diller.setArea(addDetailArea.getSelectionModel().getSelectedItem());
                diller.setCountry(addDetailCountry.getText());
                diller.setPrice(Float.parseFloat(addDetailPrice.getText()));
                sendData("Добавление диллера");
                sendData(gson.toJson(diller));
                clearAddDetailPane();
                initialize();
            } catch (NumberFormatException ignored) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неверный формат данных");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неверный формат данных");
            alert.showAndWait();
        }
    }

    @FXML
    void selectDetail() {
        if(detailsTable.getSelectionModel().getSelectedItem()!=null){
            Diller diller = detailsTable.getSelectionModel().getSelectedItem();
            editDetailName.setText(diller.getName());
            editDetailCountry.setText(diller.getCountry());
            editDetailPrice.setText(String.valueOf(diller.getPrice()));
            editDetailArea.setValue(diller.getArea());
        }
    }

    @FXML
    void updateDetail() {
        if(detailsTable.getSelectionModel().getSelectedItem()!=null) {
            Diller diller = detailsTable.getSelectionModel().getSelectedItem();
            if(editDetailName.getText().length()>2 &&
                    editDetailArea.getSelectionModel().getSelectedItem()!=null &&
                    PatternDir.check(PatternDir.COUNTRY, editDetailCountry.getText())) {
                try {
                    diller.setName(editDetailName.getText());
                    diller.setArea(editDetailArea.getSelectionModel().getSelectedItem());
                    diller.setCountry(editDetailCountry.getText());
                    diller.setPrice(Float.parseFloat(editDetailPrice.getText()));
                    sendData("Изменение диллера");
                    sendData(new Gson().toJson(diller));
                    clearEditDetailPane();
                    initialize();
                }catch (NumberFormatException | IOException | ClassNotFoundException ignored){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Неверный формат данных");
                    alert.showAndWait();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите диллера");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteDetail() throws IOException, ClassNotFoundException {
        if(detailsTable.getSelectionModel().getSelectedItem()!=null) {
            sendData("Удаление диллера");
            sendData(detailsTable.getSelectionModel().getSelectedItem().getId());
            initialize();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите диллера");
            alert.showAndWait();
        }
    }

    @FXML
    void clearAddDetailPane() {
        addDetailName.clear();
        addDetailCountry.clear();
        addDetailPrice.clear();
    }

    @FXML
    void clearEditDetailPane() {
        editDetailName.clear();
        editDetailCountry.clear();
        editDetailPrice.clear();
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        sendData("Области работ");
        String areasInfo = (String) Main.input.readObject();
        Type type = new TypeToken<ArrayList<Area>>(){}.getType();
        ArrayList<Area> areasArrayList = new Gson().fromJson(areasInfo, type);

        workersAreaComboBox.getItems().clear();
        addWorkerArea.getItems().clear();
        editWorkerArea.getItems().clear();
        addDetailArea.getItems().clear();
        editDetailArea.getItems().clear();
        areasListView.getItems().clear();

        workersAreaComboBox.getItems().add("Все");
        for (Area area : areasArrayList) {
            workersAreaComboBox.getItems().add(area.getArea());
            addWorkerArea.getItems().add(area.getArea());
            editWorkerArea.getItems().add(area.getArea());
            addDetailArea.getItems().add(area.getArea());
            editDetailArea.getItems().add(area.getArea());
            areasListView.getItems().add(area.getArea());
        }

        workersAreaComboBox.setValue("Все");
        sendData("Все работники");
        String workersInfo = (String) Main.input.readObject();
        type = new TypeToken<ArrayList<Worker>>(){}.getType();
        ArrayList<Worker> workerArrayList = new Gson().fromJson(workersInfo, type);
        ObservableList<Worker> workerObservableList = FXCollections.observableArrayList(workerArrayList);
        workerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        workerFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        workerSpecialty.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        workerExperience.setCellValueFactory(new PropertyValueFactory<>("experience"));
        workerTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        workersTable.setItems(workerObservableList);

        workersAreaComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (workersAreaComboBox.getSelectionModel().getSelectedItem().equals("Все")) {
                    sendData("Все работники");
                    String workersInfo1 = null;
                    try {
                        workersInfo1 = (String) Main.input.readObject();
                    } catch (IOException | ClassNotFoundException ignored) {
                    }
                    Type type1 = new TypeToken<ArrayList<Worker>>() {
                    }.getType();
                    ArrayList<Worker> workerArrayList1 = new Gson().fromJson(workersInfo1, type1);
                    ObservableList<Worker> workerObservableList1 = FXCollections.observableArrayList(workerArrayList1);
                    workerId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    workerFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                    workerSpecialty.setCellValueFactory(new PropertyValueFactory<>("specialty"));
                    workerExperience.setCellValueFactory(new PropertyValueFactory<>("experience"));
                    workerTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
                    workersTable.setItems(workerObservableList1);
                } else {
                    sendData("Работники области");
                    sendData(workersAreaComboBox.getSelectionModel().getSelectedItem());
                    String workersInfo1 = null;
                    try {
                        workersInfo1 = (String) Main.input.readObject();
                    } catch (IOException | ClassNotFoundException ignored) {
                    }
                    Type type1 = new TypeToken<ArrayList<Worker>>() {
                    }.getType();
                    ArrayList<Worker> workerArrayList1 = new Gson().fromJson(workersInfo1, type1);
                    ObservableList<Worker> workerObservableList1 = FXCollections.observableArrayList(workerArrayList1);
                    workerId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    workerFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                    workerSpecialty.setCellValueFactory(new PropertyValueFactory<>("specialty"));
                    workerExperience.setCellValueFactory(new PropertyValueFactory<>("experience"));
                    workerTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
                    workersTable.setItems(workerObservableList1);
                }
            }catch (Exception ignored){}

        });

        sendData("Все клиенты");
        String clientsInfo = (String) Main.input.readObject();
        type = new TypeToken<ArrayList<Client>>(){}.getType();
        ArrayList<Client> clientArrayList = new Gson().fromJson(clientsInfo, type);
        ObservableList<Client> clientObservableList = FXCollections.observableArrayList(clientArrayList);
        clientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        clientTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        clientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clientsTable.setItems(clientObservableList);

        sendData("Все диллеры");
        String detailsInfo = (String) Main.input.readObject();
        type = new TypeToken<ArrayList<Diller>>(){}.getType();
        ArrayList<Diller> dillerArrayList = new Gson().fromJson(detailsInfo, type);
        ObservableList<Diller> dillerObservableList = FXCollections.observableArrayList(dillerArrayList);
        detailName.setCellValueFactory(new PropertyValueFactory<>("name"));
        detailCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        detailPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        detailsTable.setItems(dillerObservableList);
    }
}