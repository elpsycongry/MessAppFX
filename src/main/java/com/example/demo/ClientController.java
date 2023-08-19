package com.example.demo;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private Button btnSend;
    @FXML
    private TextField tf_mess;
    @FXML
    private ScrollPane sp_main;
    @FXML
    private VBox vbox_message;
    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            client = new Client(new Socket("localhost", 1234),"client");

            System.out.println("Connected with server");
        } catch (IOException e) {
            System.out.println("Cannot connect with server from client");
            throw new RuntimeException(e);
        }
        vbox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sp_main.setVvalue((Double) t1);
            }
        });

        client.receiveMessageFromServer(vbox_message);
        this.addPreviousText();

        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = tf_mess.getText();
                if (!messageToSend.isEmpty()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);

                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);
                    hBox.getChildren().add(textFlow);
                    vbox_message.getChildren().add(hBox);
                    client.sendMessageToServer(messageToSend);
                    tf_mess.clear();
                }
            }
        });
    }

    public static void addLabel(String messageFromServer, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 5));

        Text text = new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);
//        textFlow.setMaxWidth(100);
        textFlow.setPadding(new Insets(5, 10, 5, 10));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
    public void addPreviousText(){
        ArrayList<Data> list = MessageDAO.get20LastMessage();
        for (Data mess: list
             ) {
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(5,10,5,10));
            if (mess.getUserName().equals(client.getUserName())){
                System.out.println("messFromClient");
                hBox.setAlignment(Pos.CENTER_RIGHT);
            } else {
                hBox.setAlignment(Pos.BASELINE_LEFT);
            }
            Text message = new Text(mess.getContent());
            TextFlow messageWithFlow = new TextFlow(message);

            hBox.getChildren().add(messageWithFlow);
            vbox_message.getChildren().add(hBox);
        }
    }



}


