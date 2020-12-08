package Model;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

public class Mesero implements Runnable{
    private Restaurant restaurant;
    private AnchorPane padre;
    public Mesero(AnchorPane padre, Restaurant restaurant){
        this.restaurant=restaurant;
        this.padre=padre;
    }
    @Override
    public void run() {
        /*Button mesero = new Button("");
        mesero.setLayoutX(130);
        mesero.setLayoutY(110);
        URL link= getClass().getResource("/Imagenes/Mesero.png");
        Image imagen= new Image(link.toString(),50,60,false,true);
        mesero.setGraphic((new ImageView(imagen)));
        Platform.runLater(()-> this.padre.getChildren().add(mesero));
        int ref = 0;*/
        while (true){
            restaurant.servirOrden();
        }
        }
}
