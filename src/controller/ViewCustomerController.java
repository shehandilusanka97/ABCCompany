package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tm.CustomerTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCustomerController implements Initializable {
    public TableView<CustomerTM> tblCustomer;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtId;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public TableColumn clmId;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public TableColumn clmEmail;
    public TableColumn clmContact;


    Connection connection = DBConnection.getInstance().getConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableItems(true);
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contact"));
        loadAllCustomers();
        tblCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                    return;
                }
                disableItems(false);
                txtId.setText(selectedItem.getId());
                txtName.setText(selectedItem.getName());
                txtAddress.setText(selectedItem.getAddress());
                txtContact.setText(selectedItem.getContact());
                txtEmail.setText(selectedItem.getEmail());
            }
        });
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer set cName =?, address=?, email=?,contact=? where cId=?");
              preparedStatement.setObject(1,name);
              preparedStatement.setObject(2,address);
              preparedStatement.setObject(3,email);
              preparedStatement.setObject(4,contact);
              preparedStatement.setObject(5,id);
              preparedStatement.executeUpdate();
              loadAllCustomers();
               new Alert(Alert.AlertType.NONE, "Customer updated.!!", ButtonType.OK).show();
              disableItems(true);
              clearField();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void clearField(){
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtContact.clear();
    }

    public void disableItems(boolean isDisable) {
        txtId.setDisable(isDisable);
        txtName.setDisable(isDisable);
        txtAddress.setDisable(isDisable);
        txtEmail.setDisable(isDisable);
        txtContact.setDisable(isDisable);
        btnDelete.setDisable(isDisable);
        btnUpdate.setDisable(isDisable);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.NONE,"Do you want to delete this Customer?", ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES )){
            String id = tblCustomer.getSelectionModel().getSelectedItem().getId();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from customer where cId=?");
                preparedStatement.setObject(1,id);
                preparedStatement.executeUpdate();
                loadAllCustomers();
                clearField();
                disableItems(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadAllCustomers() {

        try {
            PreparedStatement pst = connection.prepareStatement("select * from customer");
            ResultSet rst = pst.executeQuery();
            ObservableList<CustomerTM> objects = tblCustomer.getItems();
            objects.clear();
            while (rst.next()) {
               objects.add(new CustomerTM(rst.getString(1),rst.getString(2),
                       rst.getString(3),rst.getString(4),rst.getString(5)));
            }
            tblCustomer.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
