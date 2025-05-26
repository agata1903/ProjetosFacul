package Jogo;

import java.util.Random;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuPrincipal {

    public static Image getIcone() {
        return new Image(MenuPrincipal.class.getResourceAsStream("/img/recycle-sign.png")); /*Retorna um ícone da 
        pasta de imagens*/
    }

    public static Scene criar(Stage stage) {
        String caminhoCSS = MenuPrincipal.class.getResource("/jogo/Jogo.css").toExternalForm(); //Link para CSS

        Label titulo = new Label("Reciclando!"); //Título do jogo
        titulo.setOpacity(0); //Vai começar invisível
        titulo.setTranslateY(50); //Posição dele no eixo vertical

        /*Vou resumir as 4 linhas logo: Aqui vão ter duas animações: 
         * O título, antes invisível vai ganhar duas 
         * animações, uma de aparecer até ficar completamente visível 
         * e outra de subir até chegar a posição na vertical 
         * declarada, cada uma com 2s de duração
         */
        FadeTransition aparecer = new FadeTransition(Duration.seconds(2), titulo);
        aparecer.setToValue(1.0);
        TranslateTransition subir = new TranslateTransition(Duration.seconds(2), titulo);
        subir.setToY(0);

        //Aqui vai ser uma animação do título que vai ficar piscando infinitamente
        FadeTransition piscar = new FadeTransition(Duration.seconds(0.5), titulo);
        piscar.setFromValue(1.0);
        piscar.setToValue(0.0);
        piscar.setCycleCount(Animation.INDEFINITE);
        piscar.setAutoReverse(true);
        piscar.play();

        //Imagem que ficará logo abaixo do título do jogo
        ImageView imagemMenu = new ImageView(new Image(MenuPrincipal.class.getResourceAsStream("/img/imagem-menu.png")));
        imagemMenu.setFitWidth(250);
        imagemMenu.setFitHeight(250);
        imagemMenu.setPreserveRatio(true); //A imagem ficará ajustada de acordo com o tamanho da tela

        //Botão "Iniciar" que, ao clicar, dará na cena de introdução ao jogo
        Button iniciar = new Button("Iniciar");
        iniciar.getStyleClass().add("botoes");
        iniciar.setOnAction(e -> stage.setScene(CenaIntroducaoJogo.criar()));

        //Botão "Sair" que (caso você consiga), fechará o jogo
        Button sair = new Button("Sair");
        sair.getStyleClass().add("botoes");
        sair.setOnAction(e -> stage.close());

        /*Aqui temos a zoeira resumida da próxima linha até a deslizar.play(): 
         * coloque o mouse no botão "Sair" e veja ele fugindo de você 
         */
        Random rand = new Random();
        sair.setOnMouseEntered(e -> {
            double novaX = rand.nextDouble() * 600 - 300;
            double novaY = rand.nextDouble() * 400 - 200; 

            TranslateTransition deslizar = new TranslateTransition(Duration.seconds(0.5), sair);
            deslizar.setToX(sair.getTranslateX() + novaX);
            deslizar.setToY(sair.getTranslateY() + novaY);
            deslizar.play();
        });

        //Box que alinhará os botões do menu
        VBox botoes = new VBox(10, iniciar, sair);
        botoes.setAlignment(Pos.CENTER);

        //Box do menu completo
        VBox menu = new VBox(20, titulo, imagemMenu, botoes);
        menu.setAlignment(Pos.CENTER);
        menu.getStyleClass().add("fundo-principal");
        titulo.getStyleClass().add("titulo");

        //Conteiner que vai armazenar os elementos do menu e aplicar a fonte do Google Fonts
        StackPane root = new StackPane(menu);
        Scene cena = new Scene(root, 400, 500);
        cena.getStylesheets().addAll(caminhoCSS, "https://fonts.googleapis.com/css2?family=Bungee+Spice");

        //Rodando as animações de título
        subir.play();
        aparecer.play();

        //Preciso explicar?
        return cena;
    }
}