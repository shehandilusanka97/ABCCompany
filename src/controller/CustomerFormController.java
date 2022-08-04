package controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    public Label lblCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtContactNo;
    public JFXButton btnAddNewCustomer;
    public JFXButton btnSubmit;

    Connection connection = DBConnection.getInstance().getConnection();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableField(true);
        lblCustomerId.setVisible(false);
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        disableField(false);
        lblCustomerId.setVisible(true);
        autoGenerateId();
        txtName.requestFocus();
    }

    public void btnSubmitOnAction(ActionEvent actionEvent) {
        if (txtName.getText().matches("^[A-z]{2,}$")) {
            if (txtAddress.getText().matches("^^[^@*$!#.][A-z|0-9|,/| ]{1,}$")) {
                if (txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    if (txtContactNo.getText().matches("^[\\d]{10}$")) {
                        addToDatabase();
                        clear();

                    } else {
                        shake(txtContactNo);
                    }
                } else {
                    shake(txtEmail);
                }
            } else {
                shake(txtAddress);
            }
        } else {
            shake(txtName);
        }
    }

    public void shake(JFXTextField textField) {
        textField.setUnFocusColor(Paint.valueOf("red"));
        new Shake(textField).setCycleCount(1).setDelay(Duration.valueOf("100ms")).play();
        textField.setText("");
        textField.requestFocus();
    }
    public void clear(){
        lblCustomerId.setVisible(false);
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtContactNo.setText("");
        disableField(true);
        txtName.setUnFocusColor(Paint.valueOf("blue"));
        txtAddress.setUnFocusColor(Paint.valueOf("blue"));
        txtEmail.setUnFocusColor(Paint.valueOf("blue"));
        txtContactNo.setUnFocusColor(Paint.valueOf("blue"));
    }

    public void disableField(boolean disable) {
        txtName.setDisable(disable);
        txtAddress.setDisable(disable);
        txtContactNo.setDisable(disable);
        txtEmail.setDisable(disable);
    }
    public void addToDatabase(){
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContactNo.getText();
        String email = txtEmail.getText();

        try {
            PreparedStatement pst = connection.prepareStatement("insert into customer values (?,?,?,?,?)");
            pst.setObject(1,id);
            pst.setObject(2,name);
            pst.setObject(3,address);
            pst.setObject(4,contact);
            pst.setObject(5,email);

            int i = pst.executeUpdate();
            new Alert(Alert.AlertType.NONE, "Customer Saved!", ButtonType.OK).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void autoGenerateId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select cId from customer order by cId desc limit 1;");
            if (resultSet.next()) {
                String lastId = resultSet.getString(1);
                lastId = lastId.substring(1, lastId.length());
                int intId = Integer.parseInt(lastId);
                intId++;
                if (intId < 10) {
                    lblCustomerId.setText("C00" + intId);
                } else if (intId < 100) {
                    lblCustomerId.setText("C0" + intId);
                } else {
                    lblCustomerId.setText("C" + intId);
                }
            } else {
                lblCustomerId.setText("C001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}


