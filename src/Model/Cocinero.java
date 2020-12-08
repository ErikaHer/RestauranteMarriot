package Model;

public class Cocinero implements Runnable{
    private Restaurant restaurant;
    public Cocinero(Restaurant restaurant){
        this.restaurant=restaurant;
    }
    @Override
    public void run() {
        while(true){
            restaurant.Cocinar();
        }
    }
}
