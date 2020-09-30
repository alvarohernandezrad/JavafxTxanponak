package ehu.isad;


import com.google.gson.Gson;
import ehu.isad.Txanpona;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ComboBoxExperiments extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Kriptomoneten prezioa");

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("btc");
        comboBox.getItems().add("eth");
        comboBox.getItems().add("ltc");

        //Te permite escribir
        comboBox.setEditable(true);
        Text text = new Text();
        Label label=new Label();
        comboBox.getSelectionModel().selectFirst();
        Txanpona txanpona=null;
        try {
            txanpona = this.readFromUrl(comboBox.getValue().toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        text.setText("prezioa: "+txanpona.price);
        text.setX(200);
        text.setY(200);

        label.setVisible(true);
        label.setText("Txanponak: ");


        comboBox.setOnAction(e -> {
            Txanpona txanpona1=null;
            System.out.println(comboBox.getValue());
            try {
                txanpona1 = this.readFromUrl(comboBox.getValue().toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            text.setText("prezioa: "+txanpona1.price);
            
        });

        VBox vbox = new VBox(label,comboBox,text);
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public Txanpona readFromUrl(String txanpona) throws IOException {
        URL coin;
        String inputLine = "";
        Txanpona txanpon=null;

        try {
            coin = new URL("https://api.gdax.com/products/"+txanpona+"-eur/ticker");
            URLConnection yc = coin.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            inputLine = in.readLine();

            Gson gson = new Gson();
            txanpon = gson.fromJson(inputLine, Txanpona.class);

            in.close();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }

        return txanpon;

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
