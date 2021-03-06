package principal;

import Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Observable;
import java.util.Observer;


public class Controller implements Observer {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button btnIniciar;

    @FXML
    private Button btnSalir;

    @FXML
    private ProgressBar progressBarTotal;

    @FXML
    private Label txtPorcentaje;

    @FXML
    private Label lblMesero;

    @FXML
    private Circle chef;

    @FXML
    private Circle mesero;

    private String[] positions;

    @FXML
    void Finalizar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void IniciarAnimacion(ActionEvent event) {
        chef.setFill(Color.GREEN);
        mesero.setFill(Color.GREEN);
        btnIniciar.setDisable(true);
        txtPorcentaje.setText("0 %");
        progressBarTotal.setProgress(0);
        System.out.println("Inicio de animacion");
        Restaurant restaurant = new Restaurant();
        restaurant.addObserver(this::update);
        Mesero mesero=new Mesero(anchor, restaurant);
        Recepcionista recepcionista=new Recepcionista(restaurant);
        Cocinero cocinero = new Cocinero(restaurant);
        CreadorComensales creadorComensales = new CreadorComensales(anchor, restaurant, this);
        Thread hiloMesero = new Thread(mesero);
        Thread hiloRecepcionista = new Thread(recepcionista);
        Thread hCocinero = new Thread(cocinero);
        Thread hCreadorClientes = new Thread(creadorComensales);
        hiloMesero.setDaemon(true);
        hiloMesero.start();
        hiloRecepcionista.setDaemon(true);
        hiloRecepcionista.start();
        hCocinero.setDaemon(true);
        hCocinero.start();
        hCreadorClientes.setDaemon(true);
        hCreadorClientes.start();
        positions = new String[5];
        positions[0] = "533 65";
        positions[1] = "781 65";
        positions[2] = "533 243";
        positions[3] = "533 430";
        positions[4] = "781 430";
    }

    Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            if (((String)arg).contains("ocupadoMesero")){
                String[] cadena = ((String) arg).split(" ");
                int numMesa = Integer.parseInt(cadena[1]);
                Platform.runLater(()-> lblMesero.setText("Atendiendo: "+(numMesa+1)));
                mesero.setFill(Color.RED);
            }else
            if (((String)arg).contains("libreMesero")){
                Platform.runLater(()-> lblMesero.setText(""));
                mesero.setFill(Color.GREEN);
            }else
            if (((String)arg).contains("ocupado")){
                chef.setFill(Color.RED);
            }else if(((String)arg).contains("libre")){
                chef.setFill(Color.GREEN);
            }else
            if(((String)arg).contains("seat")) {
                String[] cadena = ((String) arg).split(" ");
                int numMesa = Integer.parseInt(cadena[1]);
            }
            else {
                int dato= Integer.parseInt((String)arg);
                System.out.println("Observer "+dato);
                double valor=dato*(0.05);
                Platform.runLater(()->  progressBarTotal.setProgress(valor));
                int porciento= (int) (valor*100);
                Platform.runLater(()-> txtPorcentaje.setText(porciento+" %"));
                if(porciento==100){
                    alert.setHeaderText("Se atendieron todos los clientes");
                    alert.setTitle("RESTAURANTE CERRADO");
                    alert.setContentText("Restaurante Marriot");
                    Platform.runLater(()-> {
                        alert.show();
                        btnIniciar.setDisable(false);
                    });
                }
            }
        }
    }
}
