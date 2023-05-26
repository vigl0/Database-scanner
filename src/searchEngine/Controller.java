package searchEngine;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    public Statement statement;
    public ResultSet resultSet;
    @FXML
    private TextField charField;
    @FXML
    private Label infoLabel, informasiLabel, gambarLabel, videoLabel;
    @FXML
    private ListView<String> kalimatView, kataView;
    @FXML
    private WebView gambarView, videoView;

    @FXML
    void charKeyReleased(KeyEvent event) {
        //kataView.getItems().clear();
        kalimatView.getItems().clear();
        setVisible(false);
        infoLabel.setText(null);
        try {
            java.sql.Connection conn = (java.sql.Connection) DatabaseConnection.dbConnection();
            statement = conn.createStatement();

            String userInput = charField.getText();
            resultSet = statement.executeQuery("SELECT * FROM kata WHERE kata like '" + userInput + "%' ORDER BY kata ASC");
            while (resultSet.next()) {
                String kata = resultSet.getString("kata"); //get the element in column "kata"
                kataView.getItems().addAll(kata);
            }
            if (charField.getText().equals("")) {
                kataView.getItems().clear();
            }
        } catch (SQLException e) {
        }
    }

    @FXML
    void kataMouseClicked(MouseEvent event) {
        kalimatView.getItems().clear();
        setVisible(false);
        infoLabel.setText(null);
        try {
            java.sql.Connection conn = (java.sql.Connection) DatabaseConnection.dbConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM kalimat WHERE kalimat like'" + (kataView.getSelectionModel().getSelectedItem()) + " %'ORDER BY kalimat ASC");
            while (resultSet.next()) {
                String kalimat = resultSet.getString("kalimat");
                kalimatView.getItems().addAll(kalimat);
            }
        } catch (SQLException e) {
        }
    }

    @FXML
    void kalimatMouseClicked(MouseEvent event) {
        infoLabel.setText(null);
        gambarView.getEngine().loadContent(null);
        videoView.getEngine().loadContent(null);
        try {
            java.sql.Connection conn = (java.sql.Connection) DatabaseConnection.dbConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM kalimat WHERE kalimat ='" + String.valueOf(kalimatView.getSelectionModel().getSelectedItem()) + "'");
            while (resultSet.next()) {
                String informasi = resultSet.getString("informasi");
                String gambar = resultSet.getString("gambar");
                String video = resultSet.getString("video");
                infoLabel.setText(informasi);
                setVisible(true);
                gambarView.getEngine().load(gambar);
                videoView.getEngine().load("https://www.youtube.com/embed/" + video);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setVisible(boolean b){
        gambarLabel.setVisible(b);
        videoLabel.setVisible(b);
        infoLabel.setWrapText(b);
        gambarView.setVisible(b);
        videoView.setVisible(b);
        informasiLabel.setVisible(b);
    }
}
