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

public class Cliente extends Observable implements Runnable{
    private AnchorPane padre;
    private Restaurant restaurant;
    public Cliente(AnchorPane padre, Restaurant restaurant){
        this.padre=padre;
        this.restaurant=restaurant;
    }
    @Override
    public void run() {
        this.setChanged();
        this.notifyObservers("new");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1500) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean reserva = restaurant.Reservar(Thread.currentThread().getName());
        String argumento;
        if(reserva) {
            argumento = "cR";
        }
        else {
            argumento = "sR";
        }
        this.setChanged();
        this.notifyObservers("access " + argumento);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1500) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*String url="/Imagenes/Cliente"+ThreadLocalRandom.current().nextInt(4)+".png";
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
        }*/
        /*for(int i=0;i<8;i++){
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
        }*/
        int numMesa = restaurant.Entrar(Thread.currentThread().getName());
        this.setChanged();
        this.notifyObservers("seat " + numMesa);
        /*for(int i=0;i<5;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()-25));
        }*/
        restaurant.Ordenar();
        restaurant.comerCliente();
        //restaurant.salirCliente();
        /*for(int i=0;i<2;i++){
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
        Platform.runLater(()-> cliente.setVisible(false));*/
    }
}
