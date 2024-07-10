package com.example.test3pizza;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private static final double XL_PRICE = 15.00;
    private static final double L_PRICE = 12.00;
    private static final double M_PRICE = 10.00;
    private static final double S_PRICE = 8.00;
    private static final double TOPPING_PRICE = 1.50;
    private static final double HST_RATE = 0.15;

    @FXML
    private TableView<pizza> tableview;
    @FXML
    private TableColumn<pizza, Integer> id;
    @FXML
    private TableColumn<pizza, String> Customername;
    @FXML
    private TableColumn<pizza, String> Phonenum;
    @FXML
    private TableColumn<pizza, String> Pizzasize;
    @FXML
    private TableColumn<pizza, Integer> nooftopping;
    @FXML
    private TableColumn<pizza, Double> totalbill;

    @FXML
    private TextField name;
    @FXML
    private TextField num;
    @FXML
    private TextField no;
    @FXML
    private CheckBox xl;
    @FXML
    private CheckBox l;
    @FXML
    private CheckBox m;
    @FXML
    private CheckBox s;

    private ObservableList<pizza> pizzaList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Customername.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Phonenum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        Pizzasize.setCellValueFactory(new PropertyValueFactory<>("pizzaSize"));
        nooftopping.setCellValueFactory(new PropertyValueFactory<>("numberOfToppings"));
        totalbill.setCellValueFactory(new PropertyValueFactory<>("totalBill"));

        tableview.setItems(pizzaList);

        populateTable();
    }

    @FXML
    public void fetchdata(ActionEvent actionEvent) {
        populateTable();
    }

    public void populateTable() {
        pizzaList.clear();
        String jdbcUrl = "jdbc:mysql://localhost:3306/PizzaOrderingSystem";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM pizza";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String customerName = resultSet.getString("customerName");
                String phoneNum = resultSet.getString("phoneNum");
                String pizzaSize = resultSet.getString("pizzaSize");
                int numberOfToppings = resultSet.getInt("numberOfToppings");
                double totalBill = resultSet.getDouble("totalBill");

                pizza pizzaObj = new pizza(customerName, phoneNum, pizzaSize, numberOfToppings, totalBill);
                pizzaObj.setId(id);
                pizzaList.add(pizzaObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void insertdata(ActionEvent actionEvent) {
        String customerName = name.getText();
        String phoneNum = num.getText();
        String pizzaSize = determinePizzaSize();
        int numberOfToppings = parseNumberOfToppings();
        double totalBill = calculateTotalBill(pizzaSize, numberOfToppings);

        String jdbcUrl = "jdbc:mysql://localhost:3306/PizzaOrderingSystem";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO pizza (customerName, phoneNum, pizzaSize, numberOfToppings, totalBill) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, customerName);
            statement.setString(2, phoneNum);
            statement.setString(3, pizzaSize);
            statement.setInt(4, numberOfToppings);
            statement.setDouble(5, totalBill);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new pizza was inserted successfully!");
                populateTable();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deletedata(ActionEvent actionEvent) {
        pizza selectedPizza = tableview.getSelectionModel().getSelectedItem();

        if (selectedPizza != null) {
            String jdbcUrl = "jdbc:mysql://localhost:3306/PizzaOrderingSystem";
            String dbUser = "root";
            String dbPassword = "";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String query = "DELETE FROM pizza WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, selectedPizza.getId());

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Pizza deleted successfully!");
                    populateTable();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Select a pizza to delete!");
        }
    }

    @FXML
    public void updatebutton(ActionEvent actionEvent) {
        pizza selectedPizza = tableview.getSelectionModel().getSelectedItem();

        if (selectedPizza != null) {
            String customerName = name.getText();
            String phoneNum = num.getText();
            String pizzaSize = determinePizzaSize();
            int numberOfToppings = parseNumberOfToppings();
            double totalBill = calculateTotalBill(pizzaSize, numberOfToppings);

            String jdbcUrl = "jdbc:mysql://localhost:3306/PizzaOrderingSystem";
            String dbUser = "root";
            String dbPassword = "";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String query = "UPDATE pizza SET customerName = ?, phoneNum = ?, pizzaSize = ?, numberOfToppings = ?, totalBill = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, customerName);
                statement.setString(2, phoneNum);
                statement.setString(3, pizzaSize);
                statement.setInt(4, numberOfToppings);
                statement.setDouble(5, totalBill);
                statement.setInt(6, selectedPizza.getId());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Pizza updated successfully!");
                    populateTable();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Select a pizza to update!");
        }
    }

    @FXML
    public void loadbutton(ActionEvent actionEvent) {
        populateTable();
    }

    @FXML
    public void totalbill(ActionEvent actionEvent) {
        String pizzaSize = determinePizzaSize();
        int numberOfToppings = parseNumberOfToppings();
        double totalBill = calculateTotalBill(pizzaSize, numberOfToppings);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total Bill");
        alert.setHeaderText(null);
        alert.setContentText("Total Bill: $" + totalBill);
        alert.showAndWait();
    }

    private String determinePizzaSize() {
        if (xl.isSelected()) {
            return "XL";
        } else if (l.isSelected()) {
            return "L";
        } else if (m.isSelected()) {
            return "M";
        } else if (s.isSelected()) {
            return "S";
        } else {
            return "";
        }
    }

    private int parseNumberOfToppings() {
        try {
            return Integer.parseInt(no.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    static double calculateTotalBill(String pizzaSize, int numberOfToppings) {
        double basePrice = 0.0;

        switch (pizzaSize) {
            case "XL":
                basePrice = XL_PRICE;
                break;
            case "L":
                basePrice = L_PRICE;
                break;
            case "M":
                basePrice = M_PRICE;
                break;
            case "S":
                basePrice = S_PRICE;
                break;
        }

        double toppingsPrice = numberOfToppings * TOPPING_PRICE;
        double subtotal = basePrice + toppingsPrice;
        double hst = subtotal * HST_RATE;
        double total = subtotal + hst;

        return total;
    }
}
