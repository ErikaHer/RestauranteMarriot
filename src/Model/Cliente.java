package Model;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Cliente extends Random implements Runnable{
    private AnchorPane padre;
    private Restaurant restaurant;
    public Cliente(AnchorPane padre, Restaurant restaurant){
        this.padre=padre;
        this.restaurant=restaurant;
    }
    @Override
    public void run() {
        String url="/Imagenes/Cliente"+ThreadLocalRandom.current().nextInt(4)+".png";
        System.out.println("Inicio de cliente: "+url);
        Button cliente = new Button("");
        cliente.setLayoutX(600);
        cliente.setLayoutY(500);
        URL link= getClass().getResource(url);
        Image imagen= new Image(link.toString(),50,60,false,true);
        cliente.setGraphic((new ImageView(imagen)));
        Platform.runLater(()-> this.padre.getChildren().add(cliente));
        for(int i=0;i<10;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()-50));
        }
        restaurant.Reservar(Thread.currentThread().getName());
        for(int i=0;i<8;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()-25));
        }
        for(int i=0;i<6;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()+50));
        }
        for(int i=0;i<5;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()-50));
        }
        restaurant.Entrar(Thread.currentThread().getName());
        for(int i=0;i<5;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()-25));
        }
        restaurant.Ordenar();
        restaurant.comerCliente();
        restaurant.salirCliente();
        for(int i=0;i<2;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()+50));
        }
        for(int i=0;i<5;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()+50));
        }
        for(int i=0;i<3;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()+50));
        }
        Platform.runLater(()-> cliente.setVisible(false));
    }
}
