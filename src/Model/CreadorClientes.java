package Model;

import javafx.scene.layout.AnchorPane;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CreadorClientes extends Random implements Runnable{
private AnchorPane padre;
private Restaurant restaurant;
    public CreadorClientes(AnchorPane padre, Restaurant restaurant){
        this.padre=padre;
        this.restaurant=restaurant;
    }
private Cliente cliente;
    @Override
    public void run() {
        System.out.println("Inicio de creacion de clientes");
        for(int i=0;i<20;i++){
            cliente=new Cliente(padre,restaurant);
            Thread Hcliente = new Thread(cliente);
            Hcliente.setName("cliente"+(i+1));
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
