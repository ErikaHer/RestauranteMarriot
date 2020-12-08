package Model;

import javafx.scene.layout.AnchorPane;
import principal.Controller;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CreadorComensales extends Random implements Runnable{
private AnchorPane anchor;
private Restaurant restaurant;
private Controller controller;
    public CreadorComensales(AnchorPane anchor, Restaurant restaurant, Controller controller){
        this.anchor = anchor;
        this.restaurant=restaurant;
        this.controller = controller;
    }
private Cliente cliente;
    @Override
    public void run() {
        for(int i=0;i<20;i++){
            cliente=new Cliente(anchor,restaurant);
            Thread Hcliente = new Thread(cliente);
            Hcliente.setName("Cliente "+(i+1));
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(8000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Hcliente.setDaemon(true);
            Hcliente.start();
        }
    }
}
