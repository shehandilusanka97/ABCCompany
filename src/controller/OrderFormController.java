package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tm.*;

import java.net.URL;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    public JFXTextField txtOrderDate;
    public JFXComboBox<String> cmbCustomer;
    public JFXTextField txtCustomerName;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQty;
    public JFXButton btnAddToCart;
    public TableView tblOrder;
    public JFXButton btnPlaceOrder;
    public Label lblOrderId;
    public Label lblTotal;
    public TableColumn clmDescription;
    public TableColumn clmCode;
    public TableColumn clmQty;
    public TableColumn clmTotal;
    public TableColumn clmAction;
    public TableColumn clmUnityPrice;
    public JFXButton btnDelete;

    Connection connection = DBConnection.getInstance().getConnection();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnDelete.setDisable(true);
          clmCode.setCellValueFactory(new PropertyValueFactory<>("id"));
          clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
          clmUnityPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
          clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
          clmTotal.setCellValueFactory(new PropertyValueFactory<>("totalCost"));


        setCustomerIds();
        setItemId();
        loadDate();
        autoOrderId();

        cmbCustomer.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setCustomerDetails(newValue);
                }); cmbCustomer.getSelectionModel().selectedItemProperty();
//                ============================================================================
        cmbItemCode.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setItemDetails(newValue);
                }); cmbCustomer.getSelectionModel().selectedItemProperty();
    }

    private void setItemDetails(String selectedItemId) {
        try {
            ItemTM i = ItemCrudController.getItems(selectedItemId);
            if (i!= null){
                txtDescription.setText(i.getDescription());
                txtUnitPrice.setText(String.valueOf(i.getPrice()));
                txtQtyOnHand.setText(String.valueOf(i.getQty()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemId() {
        try {

            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    ItemCrudController.getItemId());
            cmbItemCode.setItems(cIdObList);


        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setCustomerDetails(String selectedCustomerId) {
        try {
            CustomerTM c = CustomerCrudController.getCustomer(selectedCustomerId);
            if (c!= null){
                txtCustomerName.setText(c.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerIds() {
        try {

            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerId()
            );
            cmbCustomer.setItems(cIdObList);


        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    ObservableList<CartTM> tmList = FXCollections.observableArrayList();


    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double totalCost = unitPrice * qty;
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

    }


    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList) {
            total += tm.getTotalCost();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String description = txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double totalCost = unitPrice * qty;
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());



        if (qtyOnHand < qty) {
            new Alert(Alert.AlertType.WARNING, "Out Of Stock...!").show();
            return;
        }

        CartTM isExists = isExists(cmbItemCode.getValue());
        if (isExists != null) {
            for (CartTM temp : tmList) {
                if (temp.equals(isExists)) {
                    temp.setQty((temp.getQty() + qty));
                    temp.setTotalCost(temp.getTotalCost() + totalCost);
                }
            }
        } else {


            CartTM tm = new CartTM(
                    cmbItemCode.getValue(),
                    description,
                    unitPrice,
                    qty,
                    totalCost
            );
            tmList.add(tm);
            tblOrder.setItems(tmList);
        }
        tblOrder.refresh();
        calculateTotal();
        qtyChange();
        txtQty.clear();

    }

    private void qtyChange() {
        int value = Integer.parseInt(txtQtyOnHand.getText());
        if (!txtQty.getText().equals("") & (value > 0)) {
            int q = Integer.parseInt(txtQty.getText());
            int q2 = Integer.parseInt(txtQtyOnHand.getText());
            int result = q2 - q;
        }
    }

    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList) {
            if (tm.getId().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    public void autoOrderId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select orderId from orders order by orderId desc limit 1;");
            if (resultSet.next()) {
                String lastId = resultSet.getString(1);
                lastId = lastId.substring(1, lastId.length());
                int intId = Integer.parseInt(lastId);
                intId++;
                if (intId < 10) {
                    lblOrderId.setText("O00" + intId);
                } else if (intId < 100) {
                    lblOrderId.setText("O0" + intId);
                } else {
                    lblOrderId.setText("O" + intId);
                }
            } else {
                lblOrderId.setText("O001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }



    private void loadDate() {
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }



}
