package Model;

public class Recepcionista implements Runnable{
private Restaurant restaurant;
    public Recepcionista(Restaurant restaurant){
        this.restaurant=restaurant;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("R");
            restaurant.Recepcion();

        }
    }
}
