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

public class ItemFormController implements Initializable {
    public JFXButton btnAddNewItem;
    public Label lblItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtPrice;
    public JFXButton btnSubmit;
    Connection connection = DBConnection.getInstance().getConnection();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableField(true);
        lblItemCode.setVisible(false);
    }

    public void btnAddNewItemOnAction(ActionEvent actionEvent) throws SQLException {
        disableField(false);
        lblItemCode.setVisible(true);
        setId();
        txtDescription.requestFocus();
    }

    public void btnSubmitOnAction(ActionEvent actionEvent) {
     String id = lblItemCode.getText();
     String description = txtDescription.getText();
     int qty = Integer.parseInt(txtQtyOnHand.getText());
     double price = Double.parseDouble(txtPrice.getText());
        try {
        PreparedStatement pst = connection.prepareStatement("insert into item values (?,?,?,?) ");
        pst.setObject(1,id);
        pst.setObject(2,description);
        pst.setObject(3,qty);
        pst.setObject(4,price);

        pst.executeUpdate();
            new Alert(Alert.AlertType.NONE, "Item Saved!", ButtonType.OK).show();
        disableField(true);
        lblItemCode.setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void disableField(boolean disable){
        txtDescription.setDisable(disable);
        txtQtyOnHand.setDisable(disable);
        txtPrice.setDisable(disable);
        txtDescription.clear();
        txtPrice.clear();
        txtQtyOnHand.clear();
    }
    public void setId() throws SQLException {
        try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select itemCode from item order by itemCode desc limit 1 ");
        if (resultSet.next()){
            String lastId = resultSet.getString(1);
            lastId = lastId.substring(1, lastId.length());
            int intId = Integer.parseInt(lastId);
            intId++;
            if (intId < 10) {
                lblItemCode.setText("I00" + intId);
            } else if (intId < 100) {
                lblItemCode.setText("I0" + intId);
            } else {
                lblItemCode.setText("I" + intId);
            }
        } else {
            lblItemCode.setText("I001");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
