package Model;

import javafx.scene.layout.AnchorPane;
import principal.Controller;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CreadorClientes extends Random implements Runnable{
private AnchorPane padre;
private Restaurant restaurant;
private Controller controller;
    public CreadorClientes(AnchorPane padre, Restaurant restaurant, Controller controller){
        this.padre=padre;
        this.restaurant=restaurant;
        this.controller = controller;
    }
private Cliente cliente;
    @Override
    public void run() {
        System.out.println("Inicio de creacion de clientes");
        for(int i=0;i<20;i++){
            cliente=new Cliente(padre,restaurant);
            cliente.addObserver(controller);
            Thread Hcliente = new Thread(cliente);
            Hcliente.setName("cliente"+(i+1));
            /*try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(8000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            Hcliente.setDaemon(true);
            Hcliente.start();
        }
    }
}
