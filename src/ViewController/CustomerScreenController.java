package ViewController;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.DBConnection;
import DAO.DivisionDAOImpl;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for the customers screen.
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class CustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;
    
    private ObservableList<String> menuCountries = FXCollections.observableArrayList();
    private ObservableList<Integer> menuCountriesIds = FXCollections.observableArrayList();
    private ObservableList<String> menuDivisions = FXCollections.observableArrayList();
    private ObservableList<Integer> menuDivisionsIds = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, Integer> customerPostalCodeCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerDivisionCol;

    @FXML
    private Button addCustomer;

    @FXML
    private Button updateCustomer;

    @FXML
    private Button deleteCustomer;

    @FXML
    private TextField customerIdText;

    @FXML
    private TextField customerNameText;

    @FXML
    private TextField CustomerAddressText;

    @FXML
    private TextField CustomerPostalCodeText;

    @FXML
    private TextField CustomerPhoneNumberText;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button cancel;

    @FXML
    private ComboBox<String> selectCountryMenu;

    @FXML
    private ComboBox<String> selectDivisionMenu;

    /**
     * Initializes the controller class, populating the table and country
     * menu.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerTable.setItems(CustomerDAOImpl.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<> ("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<> ("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<> ("phone"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<> ("country"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<> ("division"));
        
        try {
            String sqlCountryMenu = "SELECT country, Country_ID FROM countries";

            PreparedStatement pstCountryMenu = DBConnection.startConnection().prepareStatement(sqlCountryMenu);

            ResultSet countryMenuResult = pstCountryMenu.executeQuery(sqlCountryMenu);

            while (countryMenuResult.next()) {
                String countryName = countryMenuResult.getString("country");
                int countryID = countryMenuResult.getInt("Country_ID");
                menuCountries.add(countryName);
                menuCountriesIds.add(countryID);
            }
            selectCountryMenu.setItems(menuCountries);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }    
    }    
    
    /**
     * Adds a customer to the database.
     * 
     * @param event
     */
    @FXML
    void addCustomer(ActionEvent event) throws IOException, SQLException {
        String selectedCountry = "";
        
        if(selectCountryMenu.getSelectionModel().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("No Country Selected");
        alert.setContentText("A country must be selected in order to add a customer.");
        alert.showAndWait();   
        }
        else {
        selectedCountry = selectCountryMenu.getSelectionModel().getSelectedItem();
        System.out.println(selectedCountry); 
        }   
        int countryMenuId = menuCountriesIds.get(menuCountries.indexOf(selectedCountry));
        String selectedDivision = selectDivisionMenu.getSelectionModel().getSelectedItem();
        menuDivisions = DivisionDAOImpl.menuDivisionStrings(countryMenuId);
        menuDivisionsIds = DivisionDAOImpl.menuDivisionIds(countryMenuId);
        String name = new String(customerNameText.getText());
        String address = new String(CustomerAddressText.getText());
        String postalCode = new String(CustomerPostalCodeText.getText());
        String phone = new String(CustomerPhoneNumberText.getText());
        int divisionId = 0;
        
        if (selectedDivision.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No First-Level Division Selected");
                alert.setContentText("A first-level division must be selected in order to add a customer.");
                alert.showAndWait();
        }
        else if(name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("One or More Fields is Empty");
                alert.setContentText("Verify all fields are filled out properly, then add the customer again.");
                alert.showAndWait();      
        }
        else{
            int divisionMenuIdIndex = menuDivisions.indexOf(selectedDivision);
            int divisionMenuId = menuDivisionsIds.get(divisionMenuIdIndex);
            divisionId = divisionMenuId;
            
            CustomerDAOImpl.createCustomer(0, name,address, postalCode, phone, "", "test", "", "test", divisionId);

            customerTable.setItems(CustomerDAOImpl.getAllCustomers());
            customerIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<> ("address"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<> ("postalCode"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<> ("phone"));
            customerCountryCol.setCellValueFactory(new PropertyValueFactory<> ("country"));
            customerDivisionCol.setCellValueFactory(new PropertyValueFactory<> ("division"));
        }
    }

    /**
     * Returns user to main menu.
     * 
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes a customer from the database.
     * 
     * @param event
     */
    @FXML
    void deleteCustomer(ActionEvent event) {
        int customerId = Integer.parseInt(customerIdText.getText());
      
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Customer Deletion");
            alert.setHeaderText("If you delete this customer, all associated appointments will also be deleted.  This action cannot be undone.  Are you sure you want to proceed?");
            alert.setContentText("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                CustomerDAOImpl.deleteCustomer(customerId);
                customerTable.setItems(CustomerDAOImpl.getAllCustomers());
                customerIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
                customerNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
                customerAddressCol.setCellValueFactory(new PropertyValueFactory<> ("address"));
                customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<> ("postalCode"));
                customerPhoneCol.setCellValueFactory(new PropertyValueFactory<> ("phone"));
                customerCountryCol.setCellValueFactory(new PropertyValueFactory<> ("country"));
                customerDivisionCol.setCellValueFactory(new PropertyValueFactory<> ("division"));
            }  
            else {
                customerTable.setItems(CustomerDAOImpl.getAllCustomers());
                customerIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
                customerNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
                customerAddressCol.setCellValueFactory(new PropertyValueFactory<> ("address"));
                customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<> ("postalCode"));
                customerPhoneCol.setCellValueFactory(new PropertyValueFactory<> ("phone"));
                customerCountryCol.setCellValueFactory(new PropertyValueFactory<> ("country"));
                customerDivisionCol.setCellValueFactory(new PropertyValueFactory<> ("division"));
            }
    }
    
    /**
     * Populates the fields and menus with customer information upon selection
     * of a customer.
     * 
     * @param event
     */
    @FXML
    void selectCustomer(MouseEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
        customerIdText.setText(Integer.toString(selectedCustomer.getId()));
        customerNameText.setText(selectedCustomer.getName());
        CustomerAddressText.setText(selectedCustomer.getAddress());
        CustomerPostalCodeText.setText(selectedCustomer.getPostalCode());
        CustomerPhoneNumberText.setText(selectedCustomer.getPhone());
        selectCountryMenu.setValue(selectedCustomer.getCountry());
        selectDivisionMenu.setValue(selectedCustomer.getDivision());
        }
        else {
        customerIdText.clear();
        customerNameText.clear();
        CustomerAddressText.clear();
        CustomerPostalCodeText.clear();
        CustomerPhoneNumberText.clear();
        selectCountryMenu.setValue(null);
        selectDivisionMenu.setValue(null);
        }
    }

    /**
     * Fills the first-level division menu.
     * 
     * @param event
     */
    @FXML
    void fillDivisions(MouseEvent event) throws IOException {
        try {
            String selectedCountry = "";
            if(selectCountryMenu.getSelectionModel().isEmpty()) {
            selectedCountry = "";
            }
            else {
            selectedCountry = selectCountryMenu.getSelectionModel().getSelectedItem();
            System.out.println(selectedCountry); 
            }
            
            
            if (selectedCountry.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Country not Selected");
                    alert.setContentText("A country must be selected prior to selecting a first-level division.");
                    alert.showAndWait();  
            }
            else {
                menuDivisions.clear();
                int countryMenuId = menuCountriesIds.get(menuCountries.indexOf(selectedCountry));
                menuDivisions = DivisionDAOImpl.menuDivisionStrings(countryMenuId);

                selectDivisionMenu.setItems(menuDivisions);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    void selectCountry(ActionEvent event) {
        //Not implemented
    }

    @FXML
    void selectDivision(ActionEvent event) {
        //Not implemented
    }

    /**
     * Updates a customer in the database.
     *
     * @param event
     */
    @FXML
    void updateCustomer(ActionEvent event) throws IOException, SQLException {
        
        String selectedCountry = "";
                
        if(selectCountryMenu.getSelectionModel().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("No Country Selected");
        alert.setContentText("A country must be selected in order to add a customer.");
        alert.showAndWait();          
        }
        else {
        selectedCountry = selectCountryMenu.getSelectionModel().getSelectedItem();
        System.out.println(selectedCountry); 
        }
                
        int countryMenuId = menuCountriesIds.get(menuCountries.indexOf(selectedCountry));
        String selectedDivision = selectDivisionMenu.getSelectionModel().getSelectedItem();
        menuDivisions = DivisionDAOImpl.menuDivisionStrings(countryMenuId);
        menuDivisionsIds = DivisionDAOImpl.menuDivisionIds(countryMenuId);
        int id = Integer.parseInt(customerIdText.getText());
        String name = new String(customerNameText.getText());
        String address = new String(CustomerAddressText.getText());
        String postalCode = new String(CustomerPostalCodeText.getText());
        String phone = new String(CustomerPhoneNumberText.getText());
        int divisionId = 0;
        
        if (selectedDivision.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No First-Level Division Selected");
                alert.setContentText("A first-level division must be selected in order to add a customer.");
                alert.showAndWait();
        }
        else if(name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("One or More Fields is Empty");
                alert.setContentText("Verify all fields are filled out properly, then add the customer again.");
                alert.showAndWait();     
        }
        else{
            int divisionMenuIdIndex = menuDivisions.indexOf(selectedDivision);
            int divisionMenuId = menuDivisionsIds.get(divisionMenuIdIndex);
            divisionId = divisionMenuId;
            CustomerDAOImpl.updateCustomer(id, name,address, postalCode, phone, "", "test", divisionId);

            customerTable.setItems(CustomerDAOImpl.getAllCustomers());
            customerIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<> ("address"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<> ("postalCode"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<> ("phone"));
            customerCountryCol.setCellValueFactory(new PropertyValueFactory<> ("country"));
            customerDivisionCol.setCellValueFactory(new PropertyValueFactory<> ("division"));
        }
    }
    
}
