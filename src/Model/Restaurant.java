package Model;

import java.util.Observable;

public class Restaurant extends Observable {
    public boolean VIPClient;
    public boolean Reservation;
    public boolean client;
    public boolean accEntrar;
    public int numClientes;
    public String Reservado;
    public int orden;
    public int comida;
    public int peticiones;
    public int contador=0;
    public boolean confirmacion;
    public Restaurant(){
        VIPClient=false;
        Reservation = true;
        client=false;
        accEntrar=false;
        numClientes=0;
        Reservado="";
        orden=0;
        comida=0;
        peticiones=0;
        confirmacion=false;
    }
    public synchronized void Reservar(String nombre){
        //Para el hilo cliente
        synchronized (this) {
            if(Reservation){
                System.out.println("Reservado por "+nombre);
                Reservation=false;
                Reservado=nombre;
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Entrar(String nombre){
            try {
                if(Reservado.equals(nombre)){
                    System.out.println("Confirmacion"+nombre);
                    confirmacion=false;
                }else{
                    synchronized (this) {
                    numClientes++;
                    while (accEntrar) {
                        wait();
                    }
                    accEntrar=true;
                    client=true;
                }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
    public void Ordenar(){
        synchronized (this) {
            orden++;
            notifyAll();
        }
    }
    public void DarOrden(){
        synchronized (this) {
            while(orden<=0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            peticiones++;
            orden--;
            notifyAll();
        }
    }
    public void Cocinar(){
        synchronized (this) {
            while (peticiones<=0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            comida++;
            notifyAll();
        }
    }
    public void comerCliente(){
        synchronized (this) {
            while (comida<=0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            comida--;
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void salirCliente(){
        synchronized (this) {
            if(!confirmacion){
                confirmacion=true;
                Reservation=true;
            }else{
                numClientes--;
                client=false;
                System.out.println(numClientes+" Clientes en cola");
            }
            notifyAll();
            contador++;
            setChanged();
            notifyObservers(contador);
        }
    }
    public void Recepcion(){
        //Para el hilo recepcionista
        synchronized (this) {
            while(numClientes < 1 || client){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            accEntrar=false;
            notifyAll();
        }
    }

}
