package controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashBoardFormController {

    public AnchorPane anchor;
    public JFXButton btnAddCustomer;
    public JFXButton btnAddItem;
    public JFXButton btnViewCustomer;
    public JFXButton btnViewItem;
    public JFXButton btnOrder;
    public JFXButton btnLogOut;

    public ImageView imgOption;
    public Label lblWelcome;
    public AnchorPane root0;


    public void setLordAnchorPane(AnchorPane anchorPane, String location) throws IOException {
        anchorPane.getChildren().clear();
        AnchorPane load = (AnchorPane) FXMLLoader.load(this.getClass().getResource("/view/" + location));
        anchorPane.getChildren().addAll(load.getChildren());
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        lblWelcome.setText("Customer Form");
        new SlideInLeft(anchor).play();
        new FadeInDownBig(lblWelcome).play();

        try {
            setLordAnchorPane(anchor,"CustomerForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void btnAddItemOnAction(ActionEvent actionEvent) {
        lblWelcome.setText("Item Form");
        new SlideInLeft(anchor).play();
        new FadeInDownBig(lblWelcome).play();

        try {
            setLordAnchorPane(anchor, "ItemForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnViewCustomerOnAction(ActionEvent actionEvent) {
        lblWelcome.setText("View Customer");
        new FadeInDownBig(lblWelcome).play();
        new SlideInLeft(anchor).play();

        try {
            setLordAnchorPane(anchor,"viewCustomer.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnViewItemOnAction(ActionEvent actionEvent) {
        lblWelcome.setText("View Items");
        new SlideInLeft(anchor).play();
        new FadeInDownBig(lblWelcome).play();

        try {
            setLordAnchorPane(anchor,"ViewItems.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        lblWelcome.setText("Order");
        new SlideInLeft(anchor).play();
        new FadeInDownBig(lblWelcome).play();

        try {
            setLordAnchorPane(anchor,"OrderForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        new FadeIn(root0).play();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SignUpScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) root0.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }

//    On Mouse Events
    public void OnMouseEnter(MouseEvent mouseEvent) {
       if (mouseEvent.getSource() instanceof Button){
           Button btn = (Button) mouseEvent.getSource();
           switch (btn.getId()){
               case "btnAddCustomer" : btnAddCustomer.setStyle("-fx-background-color: #5352ed");
               lblWelcome.setText("Add Customer");
                   imgOption.setImage(new Image("asset/customer.jpg"));


               break;

               case "btnAddItem" : btnAddItem.setStyle("-fx-background-color: #5352ed");
                   lblWelcome.setText("Add Items");
                   imgOption.setImage(new Image("asset/VegeBack.jpg"));


               break;

               case "btnViewCustomer": btnViewCustomer.setStyle("-fx-background-color: #5352ed");
                   lblWelcome.setText("View Customers");
                   imgOption.setImage(new Image("asset/viewCustomers.jpg"));

               break;

               case "btnViewItem": btnViewItem.setStyle("-fx-background-color: #5352ed");
                   lblWelcome.setText("View Items");
                   imgOption.setImage(new Image("asset/viewItems.jpg"));

               break;

               case "btnOrder": btnOrder.setStyle("-fx-background-color: #5352ed");
                   lblWelcome.setText("Place Order");
                   imgOption.setImage(new Image("asset/cart.jpg"));

               break;

               case "btnLogOut": btnLogOut.setStyle("-fx-background-color: red");
                     lblWelcome.setText("Log out");

               break;

           }
           ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), btn);
           scaleT.setToX(1.1);
           scaleT.setToY(1.2);
           scaleT.play();

           DropShadow glow = new DropShadow();
           glow.setColor(Color.WHITESMOKE);
           glow.setWidth(10);
           glow.setHeight(10);
           glow.setRadius(10);
           btn.setEffect(glow);
       }
    }

    public void OnMouseExit(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button){
            Button btn = (Button) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            btn.setEffect(null);
            btn.setStyle("-fx-background-color:  #050d3b ");

        }
    }
}
