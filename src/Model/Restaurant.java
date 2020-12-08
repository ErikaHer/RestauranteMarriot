package Model;

import java.util.Observable;

public class Restaurant extends Observable {
    public boolean VIPClient;
    public boolean reservacionLibre;
    public boolean client;
    public boolean accEntrar;
    public int numClientes;
    public String reservado;
    public int orden;
    public int comida;
    public int peticiones;
    public int contador=0;
    public boolean confirmacion;
    public int maxNumClientes;
    public boolean[] mesas;

    public Restaurant(){
        VIPClient=false;
        reservacionLibre = true;
        client=false;
        accEntrar=false;
        numClientes=0;
        reservado ="";
        orden=0;
        comida=0;
        peticiones=0;
        confirmacion=false;
        maxNumClientes = 0;
        mesas = new boolean[5];

        for (int i=0; i<5; i++) {
            mesas[i] = false;
        }
    }
    public synchronized boolean reservar(String nombre){
        //Para el hilo cliente
        synchronized (this) {
            if(reservacionLibre){
                System.out.println("Reservado por "+nombre);
                reservacionLibre =false;
                reservado = nombre;
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
            else {
                return false;
            }
        }
    }

    public int entrar(String nombre){
        int numMesa = -1;
            try {
                if(reservado.equals(nombre)){
                    System.out.println("Cliente reservado "+ nombre);
                    confirmacion = false;
                    numMesa = 4;
                }else{
                    synchronized (this) {
                        numClientes++;
                        maxNumClientes++;
                        while (maxNumClientes==4) {
                            wait();
                        }
                        accEntrar=true;
                        client=true;
                        for (int i=0; i<4; i++) {
                            if(!mesas[i]) {
                                numMesa = i;
                                mesas[i] = true;
                                i = 100;
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        setChanged();
        notifyObservers("seat " + numMesa);
        return numMesa;
    }
    public void ordenar(){
        synchronized (this) {
            orden++;
            notifyAll();
        }
    }
    public void servirOrden(){
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
    public void cocinar(){
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
    public void comer(){
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
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void salir(int numMesaLibre){
        synchronized (this) {
            if(!confirmacion){
                confirmacion=true;
                reservacionLibre =true;
            }else{
                numClientes--;
                maxNumClientes--;
                client=false;
                System.out.println(numClientes+" Clientes en fila");
            }
            mesas[numMesaLibre] = false;
            notifyAll();
            contador++;
            setChanged();
            notifyObservers("" + contador);
        }
    }
    public void recepcion(){
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
