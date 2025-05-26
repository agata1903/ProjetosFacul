package Jogo;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CenaIntroducaoJogo {

    public static Scene criar() {
        String caminhoCSS = CenaIntroducaoJogo.class.getResource("/jogo/Jogo.css").toExternalForm();

        //Outro tipo de conteiner que vai receber um CSS
        BorderPane root = new BorderPane();
        root.getStyleClass().add("fundo-principal");

        //Área para posição dos elementos
        Pane raiz = new Pane();
        raiz.setPrefSize(400, 500);
        
        //Pega uma imagem de sol na pasta de imagem e define o tamanho e posições
        ImageView sol = new ImageView(new Image(CenaIntroducaoJogo.class.getResourceAsStream("/img/sol.png")));
        sol.setFitWidth(80);
        sol.setFitHeight(80);
        sol.setLayoutX(30);
        sol.setLayoutY(30);

        //Mesma coisa com a nuvem
        ImageView nuvem = new ImageView(new Image(CenaIntroducaoJogo.class.getResourceAsStream("/img/nuvem.png")));
        nuvem.setFitWidth(100);
        nuvem.setFitHeight(60);
        nuvem.setLayoutX(200);
        nuvem.setLayoutY(50);

        //Animação para a nuvem: durante 5s, ela vai até 150px no eixo horizontal e volta
        TranslateTransition animaNuvem = new TranslateTransition(Duration.seconds(5), nuvem);
        animaNuvem.setByX(150);
        animaNuvem.setCycleCount(TranslateTransition.INDEFINITE);
        animaNuvem.setAutoReverse(true);
        animaNuvem.play();

        //Pega os elementos e acrescenta na cena
        raiz.getChildren().addAll(sol, nuvem);
        
        //Cria um matinho
        Rectangle matinho = new Rectangle(400, 60, Color.GREEN);

        //Cria o personagem chamado Lucas
        ImageView personagem = new ImageView(new Image(CenaIntroducaoJogo.class.getResourceAsStream("/img/lucas.png")));
        personagem.setFitWidth(250);
        personagem.setFitHeight(250);
        personagem.setPreserveRatio(true);
        personagem.setTranslateY(-20);

        //Balão de fala pro Lucas
        ImageView balaoFala = new ImageView(new Image(CenaIntroducaoJogo.class.getResourceAsStream("/img/balao-fala.png")));
        balaoFala.setFitWidth(230);
        balaoFala.setPreserveRatio(true);

        //Fonte do texto da fala e o alinhamento do mesmo
        Text textoFala = new Text();
        textoFala.setFont(Font.font("Comic Sans MS", 15));
        textoFala.setWrappingWidth(180);
        textoFala.setTextAlignment(TextAlignment.CENTER);
        textoFala.setTranslateY(-20);

        //Define um conteiner para posicionar certinho o balão e a fala
        StackPane containerBalao = new StackPane(balaoFala, textoFala);
        containerBalao.setTranslateX(30);
        containerBalao.setTranslateY(-150);

        
        StackPane rodape = new StackPane();
        rodape.setPrefHeight(150);
        rodape.setAlignment(Pos.BOTTOM_LEFT);
        rodape.getChildren().addAll(matinho, personagem, containerBalao);
        
        root.setBottom(rodape);

        //Posicionado no canto inferior direito, um texto com o comando começará invisível
        Text pressioneEspaco = new Text("Pressione Espaço para continuar");
        pressioneEspaco.setFont(Font.font("Arial", 16));
        pressioneEspaco.setFill(Color.BLACK);
        pressioneEspaco.setVisible(false);
        pressioneEspaco.setLayoutX(150);
        pressioneEspaco.setLayoutY(480);

        //Reserva a camada para o texto
        Pane camadaTexto = new Pane();
        camadaTexto.getChildren().add(pressioneEspaco);
        root.getChildren().add(camadaTexto);
        
        //Cria as falas do Lucas
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> textoFala.setText("Olá amigo(a), meu nome é Lucas. Bem-vindo ao Reciclando!")),
            new KeyFrame(Duration.seconds(3), e -> textoFala.setText("Aqui, eu preciso que você me ajude a colocar cada lixo em sua devida lixeira, pode me ajudar?")),
            new KeyFrame(Duration.seconds(6), e -> pressioneEspaco.setVisible(true))    
        );
        
        //Animação para piscar o comando de pressionar o botão de espaço
        FadeTransition piscar = new FadeTransition(Duration.seconds(0.5), pressioneEspaco);
        piscar.setFromValue(1.0);
        piscar.setToValue(0.0);
        piscar.setCycleCount(FadeTransition.INDEFINITE);
        piscar.setAutoReverse(true);
        piscar.play();

        //Mostra as falas do Lucas
        timeline.play();

        //Cria a cena (eu preciso ficar realmente comentando essa parte?!)
        Scene cena = new Scene(root, 400, 500);
        cena.getStylesheets().add(caminhoCSS);

        //Ativa o evento de pressionar o botão de espaço que dará início ao jogo
        cena.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                Scene cenaJogo = Jogo.criar();
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(cenaJogo);
            }
        });

        return cena;
    }
}