package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class NewSignUpController implements Initializable {
    public Label lblWelcome;
    public Label lblEId;
    public JFXButton btnAdd;
    public JFXTextField txrUserName;
    public JFXTextField txtContact;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConPassword;
    public JFXButton btnRegister;
    public Label lblSignUp;
    public AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autoIncrementId();
        setDisableCommon(true);

    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        setDisableCommon(false);
        txrUserName.requestFocus();
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        Registration();
    }

    private void Registration() {
        String newPassword = txtPassword.getText();
        String conPassword = txtConPassword.getText();

        if (newPassword.equals(conPassword)){
            String id = lblEId.getText();
            String userName = txrUserName.getText();
            String contact = txtContact.getText();

            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("Insert into employee values (?,?,?,?)");
                preparedStatement.setObject(1,id);
                preparedStatement.setObject(2,userName);
                preparedStatement.setObject(3,contact);
                preparedStatement.setObject(4,conPassword);
                int i = preparedStatement.executeUpdate();
                if (i>0){
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/SignUpScreen.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage =(Stage) root.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.centerOnScreen();

                }else{
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").showAndWait();
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }


        }else {
            txtPassword.clear();
            txtConPassword.clear();
            txtPassword.requestFocus();
        }
    }
    public void setDisableCommon(boolean isDisable){
        txrUserName.setDisable(isDisable);
        txtContact.setDisable(isDisable);
        txtPassword.setDisable(isDisable);
        txtConPassword.setDisable(isDisable);


    }
    public void autoIncrementId(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery("select eId from employee order by eId desc limit 1;");
            if (resultSet.next()){
                String lastId = resultSet.getString(1);
                lastId = lastId.substring(1, lastId.length());
                int intId = Integer.parseInt(lastId);
                intId++;
                if (intId<10){
                    lblEId.setText("E00"+intId);
                }else if(intId <100){
                    lblEId.setText("E0"+intId);
                }else{
                    lblEId.setText("E"+intId);
                }

            }else{
                lblEId.setText("E001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onClickSignUp(MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SignUpScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }
}
