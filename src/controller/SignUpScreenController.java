package controller;

import animatefx.animation.Shake;
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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpScreenController implements Initializable {
    public AnchorPane root2;
    public JFXPasswordField txtPassword;
    public JFXTextField UserName;
    public JFXButton btnLogin;
    public Label lblSignUp;
    public Label lblWelcome;
    public AnchorPane root;
    public Label lblText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserName.requestFocus();
        lblText.setVisible(false);
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String username = UserName.getText();
        String password = txtPassword.getText();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("select * from employee where eName = ? and password=?;");
            pst.setObject(1,username);
            pst.setObject(2,password);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                Parent parent = FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) root.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Dashboard");
                primaryStage.centerOnScreen();
                lblText.setVisible(true);
            }else{
                new Alert(Alert.AlertType.ERROR,"User Doesn't exists ");
                shake(UserName);
                shake2(txtPassword);
                UserName.clear();
                txtPassword.clear();
                UserName.requestFocus();
                lblText.setVisible(true);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
    public void shake(JFXTextField textField) {
        textField.setUnFocusColor(Paint.valueOf("red"));
        new Shake(textField).setCycleCount(1).setDelay(Duration.valueOf("100ms")).play();
        textField.setText("");
        UserName.requestFocus();
    }
    public void shake2(JFXPasswordField textField) {
        textField.setUnFocusColor(Paint.valueOf("red"));
        new Shake(textField).setCycleCount(1).setDelay(Duration.valueOf("100ms")).play();
        textField.setText("");
        UserName.requestFocus();
        System.out.println("Incorrect username or password");
    }


    public void onClickSignUp(MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/NewSignUp.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }



}
