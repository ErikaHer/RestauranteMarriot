package Model;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cliente implements Runnable{
    private AnchorPane anchor;
    private Restaurant restaurant;
    private static String[] positions;
    public Cliente(AnchorPane anchor, Restaurant restaurant){
        this.anchor = anchor;
        this.restaurant=restaurant;
        positions = new String[5];
        positions[0] = "533 65";
        positions[1] = "781 65";
        positions[2] = "533 243";
        positions[3] = "533 430";
        positions[4] = "781 430";
    }
    @Override
    public void run() {
        Circle cliente = new Circle(15, Color.rgb(180, 140, 140));
        Platform.runLater(() -> {
            cliente.setLayoutX(24);
            cliente.setLayoutY(340);
            anchor.getChildren().add(cliente);
        });
        //Avanzar
        for(int i=0;i<5;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()+50));
        }
        boolean reservation = restaurant.reservar(Thread.currentThread().getName());
        if(reservation) {
            Platform.runLater(()-> cliente.setFill(Color.rgb(180, 140, 179)));
        }
        else {
            Platform.runLater(()-> cliente.setFill(Color.rgb(180, 140, 140)));
        }

        //Entrar
        int numMesa = restaurant.entrar(Thread.currentThread().getName());
        String[] layout = positions[numMesa].split(" ");
        Platform.runLater(()-> {
            cliente.setLayoutX(Integer.parseInt(layout[0]));
            cliente.setLayoutY(Integer.parseInt(layout[1])+50);
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Ordenar
        Platform.runLater(()-> cliente.setFill(Color.BLUE));
        restaurant.ordenar();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Comer
        Platform.runLater(()-> cliente.setFill(Color.YELLOW));
        restaurant.comer();

        //Salir
        Platform.runLater(()-> cliente.setFill(Color.rgb(180, 140, 140)));
        restaurant.salir(numMesa);
        if(numMesa == 0 || numMesa == 2 || numMesa == 3 ) {
            switch (numMesa) {
                case 0: //mesa1
                    for(int i=0;i<2;i++){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()+90));
                    }
                    for(int i=0;i<5;i++){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()+120));
                    }
                    break;
                case 2://mesa3
                    for(int i=0;i<2;i++){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()+90));
                    }
                    for(int i=0;i<4;i++){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()+120));
                    }
                    break;
                case 3://mesa4
                    for(int i=0;i<3;i++){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()+90));
                    }
                    Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()+120));
                    break;
            }
        }
        else {
            switch (numMesa) {
                case 1://mesa2
                    for(int i=0;i<2;i++){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()-20));
                    }
                    for(int i=0;i<5;i++){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()+120));
                    }
                    break;
                case 4://mesa5
                    Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()-50));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(int i=0;i<3;i++){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()+80));
                    }
                    break;
            }
        }
    }
}
