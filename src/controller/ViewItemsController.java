package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tm.CustomerTM;
import tm.ItemTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewItemsController implements Initializable {
    public TableView<ItemTM> tblItem;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtId;
    public JFXTextField txtUnitPrice;
    Connection connection = DBConnection.getInstance().getConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        common(true);
        loadItems();

        tblItem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                    return;
                }
                common(false);
                txtId.setText(selectedItem.getId());
                txtDescription.setText(selectedItem.getDescription());
                txtQtyOnHand.setText(String.valueOf(selectedItem.getQty()));
                txtUnitPrice.setText(String.valueOf(selectedItem.getPrice()));
            }
        });
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String description = txtDescription.getText();
        String qty = txtQtyOnHand.getText();
        String price = txtUnitPrice.getText();

        try {
            PreparedStatement pst = connection.prepareStatement("UPDATE item set description =?, qtyOnHand=?, unitPrice=? where itemCode=?");
            pst.setObject(1,description);
            pst.setObject(2,qty);
            pst.setObject(3,price);
            pst.setObject(4,id);
            pst.executeUpdate();
            loadItems();
            common(true);
            new Alert(Alert.AlertType.NONE, "Item updated.!!", ButtonType.OK).show();
            clearField();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.NONE,"Do you want to delete this Item?", ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES )){
            String id = tblItem.getSelectionModel().getSelectedItem().getId();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from item where itemCode=?");
                preparedStatement.setObject(1,id);
                preparedStatement.executeUpdate();
                loadItems();
                clearField();
               common(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void common(boolean isDisable) {
        txtId.setDisable(isDisable);
        txtDescription.setDisable(isDisable);
        txtQtyOnHand.setDisable(isDisable);
        txtUnitPrice.setDisable(isDisable);
        btnUpdate.setDisable(isDisable);
        btnDelete.setDisable(isDisable);

    }
    public void clearField(){
        txtId.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();

    }

    public void loadItems() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from item");
            ResultSet rst = preparedStatement.executeQuery();
            ObservableList<ItemTM> item = tblItem.getItems();
            item.clear();
            while (rst.next()) {
                item.add(new ItemTM(rst.getString(1), rst.getString(2), rst.getInt(3)
                        , rst.getDouble(4)));
            }
            tblItem.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
