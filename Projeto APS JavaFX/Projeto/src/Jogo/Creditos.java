package Jogo;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Creditos {

    public static Scene criar() {
        String caminhoCSS = Creditos.class.getResource("/jogo/Jogo.css").toExternalForm();

        //Label para a função começando como invisível
        Label funcaoLabel = new Label();
        funcaoLabel.setOpacity(0);
        funcaoLabel.setTranslateY(0);
        funcaoLabel.getStyleClass().add("credito-funcao");

        //Label para o nome de quem fez tal função, também começando como invisível
        Label nomeLabel = new Label();
        nomeLabel.setOpacity(0);
        nomeLabel.setTranslateY(30);
        nomeLabel.getStyleClass().add("credito-nome");

        //Evento de aparecer a função
        FadeTransition aparecerFuncao = new FadeTransition(Duration.seconds(1), funcaoLabel);
        aparecerFuncao.setToValue(1.0);
        aparecerFuncao.setCycleCount(1);

        //Evento de aparecer o nome
        FadeTransition aparecerNome = new FadeTransition(Duration.seconds(1), nomeLabel);
        aparecerNome.setToValue(1.0);
        aparecerNome.setCycleCount(1);

        /*Cena de créditos em timeline: cada nome aparecendo durante 3s
         * e terminando com a mensagem de fim e agradecimento
         */
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                funcaoLabel.setText("Menu principal:");
                nomeLabel.setText("Anna");
                aparecerFuncao.play();
                aparecerNome.play();
            }),
            new KeyFrame(Duration.seconds(4), e -> {
                funcaoLabel.setText("Introdução:");
                nomeLabel.setText("Gustavo");
                aparecerFuncao.play();
                aparecerNome.play();
            }),
            new KeyFrame(Duration.seconds(7), e -> {
                funcaoLabel.setText("Jogo e créditos:");
                nomeLabel.setText("Ágata");
                aparecerFuncao.play();
                aparecerNome.play();
            }),
            new KeyFrame(Duration.seconds(10), e -> {
                funcaoLabel.setText("Fim.");
                nomeLabel.setText("Obrigado por jogar!");
                aparecerFuncao.play();
                aparecerNome.play();
            })
        );
        timeline.setCycleCount(1);
        timeline.play();
        
        //Em 13s de timeline, ele volta para o menu principal
        PauseTransition delay = new PauseTransition(Duration.seconds(13));
        delay.setOnFinished(e -> {
            Stage stage = (Stage) funcaoLabel.getScene().getWindow();
            Scene menuInicial = MenuPrincipal.criar(stage);
            stage.setScene(menuInicial);
        });
        delay.play();
        
        //Box para receber os créditos
        VBox vbox = new VBox(funcaoLabel, nomeLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #DFF5E1;");
        
        Scene cena = new Scene(vbox, 400, 500);
        cena.getStylesheets().addAll(caminhoCSS, "https://fonts.googleapis.com/css2?family=Bungee+Spice");

        return cena;
    }
}