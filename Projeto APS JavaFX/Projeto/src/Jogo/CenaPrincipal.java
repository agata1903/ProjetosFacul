package Jogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CenaPrincipal extends Application {
    @Override
    
    public void start(Stage primaryStage) {
   
        Scene menu = MenuPrincipal.criar(primaryStage); //Cria a cena que vai ser colocada em todas as telas
        primaryStage.setScene(menu); //Mostra a cena da variável menu
        primaryStage.setTitle("Reciclando!"); //Título que vai ficar na barrinha de cima (não lembro o nome, desculpa)
        primaryStage.getIcons().add(MenuPrincipal.getIcone()); //Vai mostrar o ícone que foi criado na classe MenuPrincipal
        primaryStage.setResizable(false); //Não vai poder maximizar nem minimizar a tela
        primaryStage.show(); //Mostra o resultado da cena na tela
    }

    public static void main(String[] args) {
        launch(args);
    }
}