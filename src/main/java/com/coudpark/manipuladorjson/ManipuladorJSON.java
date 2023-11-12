package com.coudpark.manipuladorjson;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManipuladorJSON extends Application {

    private static final String FILE_NAME = "config.json";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Configurar arquivo de Acesso ao Servidor");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button readButton = new Button("Read configuration");
        Button writeButton = new Button("Write configuration");

        grid.add(readButton, 0, 0);
        grid.add(writeButton, 1, 0);

        readButton.setOnAction(e -> readConfiguration());
        writeButton.setOnAction(e -> writeConfiguration());

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void readConfiguration() {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(FILE_NAME);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            reader.close();

            if (jsonObject.isEmpty()) {
                showAlert("Configuração vazia", "O arquivo não contém informações.");
            } else {
                showAlert("Read Configuration", jsonObject.toJSONString());
            }

        } catch (IOException ioE) {
            showAlert("Erro", "O arquivo de configuração de servidor não existe, crie um na opção 'Write Configuration'.");
        } catch (ParseException parseE){
            showAlert("Erro", "Um erro aconteceu ao tentar ler o arquivo de configurações.");
        }
    }

    private void writeConfiguration() {
        JSONObject jsonObject = new JSONObject();
        try {
            String serverName = dialogoInsercao("Informe o nome do servidor:");
            String serverIP = dialogoInsercao("Informe o IP do servidor:");
            String serverPassword = dialogoInsercao("Informe a senha do servidor:");

            jsonObject.put("server_name", serverName);
            jsonObject.put("server_ip", serverIP);
            jsonObject.put("server_password", serverPassword);

            FileWriter writer = new FileWriter(FILE_NAME);
            writer.write(jsonObject.toJSONString());
            writer.close();

            showAlert("Write Configuration", "Configuração salva com sucesso:\n" + jsonObject.toJSONString());

        } catch (IOException e) {
            showAlert("Erro", "Um erro aconteceu ao tentar gravar o arquivo de configuração.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String dialogoInsercao(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Inserir Informações");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);

        return dialog.showAndWait().orElse("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
