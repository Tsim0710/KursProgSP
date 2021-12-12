package app;

import db.service.*;

import java.io.IOException;
import java.sql.SQLException;

public class RequestHandler{

    public void accept(String command) throws SQLException, IOException, ClassNotFoundException {
        UserService userService = new UserService();
        CarService carService = new CarService();
        SaleService saleService = new SaleService();
        AreaService areaService = new AreaService();
        WorkerService workerService = new WorkerService();
        DillerService dillerService = new DillerService();
        ClientService clientService = new ClientService();

        switch (command){
            case "Авторизация":
                String user = (String) ClientSocket.in.readObject();
                userService.getUser(user);
                break;

            case "Машины клиента":
                String clientId = (String) ClientSocket.in.readObject();
                carService.selectClientCars(clientId);
                break;

            case "Все машины":
                carService.selectAllCars();
                break;

            case "Добавление машины":
                String carInfo = (String) ClientSocket.in.readObject();
                carService.addCar(carInfo);
                break;
            case "Информация о машине":
                String carId = (String) ClientSocket.in.readObject();
                carService.getCar(carId);
                break;
            case "Информация о работнике":
                Integer workerId = (Integer) ClientSocket.in.readObject();
                workerService.getWorker(workerId);
                break;
            case "Редактирование машины":
                carInfo = (String) ClientSocket.in.readObject();
                carService.updateCar(carInfo);
                break;
            case "Удаление машины":
                carInfo = (String) ClientSocket.in.readObject();
                carService.deleteCar(carInfo);
                break;

            case "Удаление области":
                String areaInfo = (String) ClientSocket.in.readObject();
                saleService.deleteArea(areaInfo);
                break;

            case "Добавление области":
                areaInfo = (String) ClientSocket.in.readObject();
                saleService.addArea(areaInfo);
                break;

            case "Все диллеры":
                dillerService.selectAllDillers();
                break;

            case "Новая заявка клиента":
                String SaleInfo = (String) ClientSocket.in.readObject();
                saleService.addSale(SaleInfo);
                break;

            case "Изменение заказа":
                SaleInfo = (String) ClientSocket.in.readObject();
                saleService.updateSale(SaleInfo);
                break;

            case "Изменение диллера":
                String dillerInfo = (String) ClientSocket.in.readObject();
                dillerService.updateDiller(dillerInfo);
                break;

            case "Свободные заказы":
                String area = (String) ClientSocket.in.readObject();
                saleService.selectFreeSale(area);
                break;

            case "Заказы работника":
                workerId = (Integer) ClientSocket.in.readObject();
                saleService.selectWorkerSales(workerId);
                break;

            case "Все работники":
                workerService.selectAllBrokers();
                break;

            case "Все клиенты":
                clientService.selectAllClients();
                break;

            case "Работники области":
                area = (String) ClientSocket.in.readObject();
                workerService.selectAreaBrokers(area);
                break;

            case "Диллеры области":
                area = (String) ClientSocket.in.readObject();
                dillerService.selectAreaDillers(area);
                break;

            case "Проверка логина":
                user = (String) ClientSocket.in.readObject();
                userService.checkLogin(user);
                break;

            case "Удаление аккаунта":
                Integer userId = (Integer) ClientSocket.in.readObject();
                workerId = (Integer) ClientSocket.in.readObject();
                userService.deleteWorker(userId, workerId);
                break;

            case "Удаление клиента":
                userId = (Integer) ClientSocket.in.readObject();
                userService.deleteClient(userId);
                break;

            case "Удаление диллера":
                Integer dillerId = (Integer) ClientSocket.in.readObject();
                dillerService.deleteDiller(dillerId);
                break;

            case "Добавление работника":
                user = (String) ClientSocket.in.readObject();
                String workerInfo = (String) ClientSocket.in.readObject();
                userService.addWorker(user, workerInfo);
                break;

            case "Добавление клиента":
                user = (String) ClientSocket.in.readObject();
                String clientInfo = (String) ClientSocket.in.readObject();
                userService.addClient(user, clientInfo);
                break;

            case "Добавление диллера":
                dillerInfo = (String) ClientSocket.in.readObject();
                dillerService.addDiller(dillerInfo);
                break;

            case "Изменение работника":
                workerInfo = (String) ClientSocket.in.readObject();
                workerService.updateWorker(workerInfo);
                break;

            case "Изменение клиента":
                clientInfo = (String) ClientSocket.in.readObject();
                clientService.updateClient(clientInfo);
                break;

            case "Заявки клиента":
                clientId = (String) ClientSocket.in.readObject();
                saleService.selectClientSales(clientId);
                break;

            case "Удаление заказа":
                SaleInfo = (String) ClientSocket.in.readObject();
                saleService.deleteSale(SaleInfo);
                break;

            case "Области работ":
                areaService.selectAreas();
                break;

            default:
                System.out.print("Неизвестная команда: ");
                System.err.println(command);
                break;
        }
    }
}