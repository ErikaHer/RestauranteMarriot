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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;


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
    private Circle client;

    @FXML
    void Finalizar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void IniciarAnimacion(ActionEvent event) {
        btnIniciar.setDisable(true);
        txtPorcentaje.setText("0 %");
        progressBarTotal.setProgress(0);
        System.out.println("Inicio de animacion");
        Restaurant restaurant = new Restaurant();
        restaurant.addObserver(this::update);
        Mesero mesero=new Mesero(anchor, restaurant);
        Recepcionista recepcionista=new Recepcionista(restaurant);
        Cocinero cocinero = new Cocinero(restaurant);
        CreadorClientes creadorClientes= new CreadorClientes(anchor, restaurant, this);
        Thread hiloMesero = new Thread(mesero);
        Thread hiloRecepcionista = new Thread(recepcionista);
        Thread hCocinero = new Thread(cocinero);
        Thread hCreadorClientes = new Thread(creadorClientes);
        hiloMesero.setDaemon(true);
        hiloMesero.start();
        hiloRecepcionista.setDaemon(true);
        hiloRecepcionista.start();
        hCocinero.setDaemon(true);
        hCocinero.start();
        hCreadorClientes.setDaemon(true);
        hCreadorClientes.start();
    }
    private Object o1 = new Object();
    private ArrayList<Circle> clients = new ArrayList<>();
    private void addClient(){
        synchronized (o1) {
            Color rgb;
            /*if (reservation) {
                rgb = Color.rgb(180, 140, 179);
            }*/
            //else {
                rgb = Color.rgb(180, 140, 140);
            //}
            Circle circle = new Circle(15, rgb);
            AtomicInteger num = new AtomicInteger(clients.size());

            Platform.runLater(() -> {
                int x = 50 + ((num.get() % 2) * 40);
                int y = 125 + ((num.get() / 2) * 40);
                circle.setLayoutX(x);
                circle.setLayoutY(y);
                anchor.getChildren().add(circle);
            });
            clients.add(circle); //mientras se escriba hay que usar synchronized
        }
    }

    private void removeClient(String argsCli) {
        Circle circle;
        Paint paint;
        synchronized (o1) {
            //System.out.println("Removiendo");
            circle = clients.remove(clients.size() - 1);
            if(argsCli.equals("cR")) {
                paint = Color.rgb(180, 140, 179);
            }
            else {
                paint = Color.rgb(180, 140, 140);
            }

            Platform.runLater(() -> { //estaba fuera del sync
                client.setFill(paint);
                anchor.getChildren().remove(circle);
            });
        }
    }

    public void paintClient() {
        Circle circle;
        Paint paint;
        synchronized (o1) {
            //System.out.println("Removiendo");
            circle = clients.remove(clients.size() - 1);
            paint = circle.getFill();

            Platform.runLater(() -> { //estaba fuera del sync
                client.setFill(paint);
                anchor.getChildren().remove(circle);
            });
        }
    }

    Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            if (o instanceof Cliente ) {
                String cadena = ((String)arg);
                String[] argsCli = cadena.split(" ");
                switch (argsCli[0]){
                    case "new":
                        this.addClient();
                        break;
                    case "access":
                        this.removeClient(argsCli[1]);
                        break;
                    case "seat":
                        //this.paintTable(argsCli[1]);
                        break;
                }
            }
            else {
                int dato= (int)arg;
                System.out.println("Observer "+dato);
                double valor=dato*(0.05);
                Platform.runLater(()->  progressBarTotal.setProgress(valor));
                int porciento= (int) (valor*100);
                Platform.runLater(()-> txtPorcentaje.setText(porciento+" %"));
                if(porciento==100){
                    alert.setHeaderText("Fin de la animación");
                    alert.setTitle("Terminación");
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
