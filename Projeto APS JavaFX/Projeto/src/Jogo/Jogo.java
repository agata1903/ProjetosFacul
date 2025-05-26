//HORA DA TORTURA EM 200 LINHAS OU MAIS, PESSOAL!!!
package Jogo;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Jogo {

    //Contador de lixinhos posicionados corretamente
    private static int lixinhosCorretos = 0;

    public static Scene criar() {
        String caminhoCSS = Jogo.class.getResource("/jogo/Jogo.css").toExternalForm();

        //Cria a área do jogo
        Pane areaDeJogo = new Pane();
        areaDeJogo.setPrefSize(400, 500);
        areaDeJogo.getStyleClass().add("fundo-principal");

        //Cria um retângulo como matinho
        Rectangle matinho = new Rectangle(400, 60, Color.GREEN);
        matinho.setLayoutY(440);

        //Criação das lixeiras (aqui só define as imagens)
        ImageView lixeiraPapel = criarLixeira("/img/lixeira-papel.png", "papel");
        ImageView lixeiraPlastico = criarLixeira("/img/lixeira-plastico.png", "plastico");
        ImageView lixeiraMetal = criarLixeira("/img/lixeira-metal.png", "metal");
        ImageView lixeiraVidro = criarLixeira("/img/lixeira-vidro.png", "vidro");

        //Posicionamento das lixeiras
        HBox lixeirasBox = new HBox(30, lixeiraPapel, lixeiraPlastico, lixeiraMetal, lixeiraVidro);
        lixeirasBox.setAlignment(Pos.CENTER);
        lixeirasBox.setLayoutY(400);
        lixeirasBox.setPrefWidth(400);

        //Label de feedback
        Label feedback = new Label();
        feedback.getStyleClass().add("feedback");
        feedback.setLayoutX(120);
        feedback.setLayoutY(20);

        //Criação dos lixinhos "arrastáveis"
        ImageView lixoPapel = criarLixo("/img/lixo-papel.png", "papel", 50, 110, feedback, lixeirasBox, areaDeJogo);
        ImageView lixoPlastico = criarLixo("/img/lixo-plastico.png", "plastico", 200, 70, feedback, lixeirasBox, areaDeJogo);
        ImageView lixoMetal = criarLixo("/img/lixo-metal.png", "metal", 120, 150, feedback, lixeirasBox, areaDeJogo);
        ImageView lixoVidro = criarLixo("/img/lixo-vidro.png", "vidro", 300, 100, feedback, lixeirasBox, areaDeJogo);

        //Imagem do solzinho posicionada na tela
        ImageView sol = new ImageView(new Image(Jogo.class.getResourceAsStream("/img/sol.png")));
        sol.setFitWidth(80);
        sol.setFitHeight(80);
        sol.setLayoutX(30);
        sol.setLayoutY(30);

        //Imagem da nuvenzinha animada
        ImageView nuvem = new ImageView(new Image(Jogo.class.getResourceAsStream("/img/nuvem.png")));
        nuvem.setFitWidth(100);
        nuvem.setFitHeight(60);
        nuvem.setLayoutX(200);
        nuvem.setLayoutY(50);

        TranslateTransition animaNuvem = new TranslateTransition(Duration.seconds(5), nuvem);
        animaNuvem.setByX(150);
        animaNuvem.setCycleCount(TranslateTransition.INDEFINITE);
        animaNuvem.setAutoReverse(true);
        animaNuvem.play();

        //Adiciona os elementos a área do jogo
        areaDeJogo.getChildren().addAll(
            matinho, sol, nuvem,
            lixoPapel, lixoPlastico, lixoMetal, lixoVidro,
            lixeirasBox, feedback
        );

        Scene cena = new Scene(areaDeJogo, 400, 500);
        cena.getStylesheets().addAll(caminhoCSS, "https://fonts.googleapis.com/css2?family=Bungee+Spice");

        return cena;
    }

    //Criação das lixeiras com posicionamento
    private static ImageView criarLixeira(String caminhoImagem, String tipoAceito) {
        Image img = new Image(Jogo.class.getResourceAsStream(caminhoImagem));
        ImageView lixeira = new ImageView(img);
        lixeira.setFitWidth(100);
        lixeira.setFitHeight(100);
        lixeira.setPreserveRatio(true);
        lixeira.setUserData(tipoAceito);
        return lixeira;
    }

    //Criação dos lixinhos arrastáveis com posicionamento
    private static ImageView criarLixo(String caminhoImagem, String tipo, double startX, double startY,
                                       Label feedback, HBox lixeirasBox, Pane areaDeJogo) {

        ImageView lixo = new ImageView(new Image(Jogo.class.getResourceAsStream(caminhoImagem)));
        lixo.setFitWidth(50);
        lixo.setFitHeight(50);
        lixo.setUserData(tipo);
        lixo.setLayoutX(startX);
        lixo.setLayoutY(startY);

        final double[] offsetX = new double[1];
        final double[] offsetY = new double[1];

        //Evento de clicar no lixo
        lixo.setOnMousePressed(event -> {
            offsetX[0] = event.getSceneX() - lixo.getLayoutX();
            offsetY[0] = event.getSceneY() - lixo.getLayoutY();
        });

        //Evento de arrastar o lixo
        lixo.setOnMouseDragged(event -> {
            lixo.setLayoutX(event.getSceneX() - offsetX[0]);
            lixo.setLayoutY(event.getSceneY() - offsetY[0]);
        });

        //Evento de soltar o lixo
        lixo.setOnMouseReleased(event -> {
            boolean acertou = false;
            //Limpa o estilo do feedback anterior (usado para não haver conflito entre cores)
            feedback.getStyleClass().removeAll("feedback-acerto", "feedback-erro", "feedback-parabens");

            //Aqui ele pega as posições de cada lixeira e cada lixinho
            for (javafx.scene.Node node : lixeirasBox.getChildren()) {
                ImageView lixeira = (ImageView) node;
                Bounds boundsLixeira = lixeira.localToScene(lixeira.getBoundsInLocal());
                Bounds boundsLixo = lixo.localToScene(lixo.getBoundsInLocal());
                
                //Aqui verifica se o lixinho foi jogado na lixeira correta
                if (boundsLixeira.intersects(boundsLixo)) {
                    String tipoAceito = (String) lixeira.getUserData();

                    /*Se arrastar um lixinho para a lixeira certa, 
                     * ele dá uma mensagem de acerto e o lixinho some da tela
                     */
                    if (tipo.equals(tipoAceito)) {
                        ((Pane) lixo.getParent()).getChildren().remove(lixo);
                        feedback.setText("Acertou!");
                        feedback.getStyleClass().add("feedback-acerto");
                        acertou = true;
                        lixinhosCorretos++;
                        
                        /*Se todos os lixinhos forem arrastados corretamente
                         * ele dá uma mensagem de parabéns e depois de 3s, vai para a cena de créditos.
                         */
                        if (lixinhosCorretos == 4) {
                            mostrarConfetes(areaDeJogo);
                            feedback.setText("Parabéns! \nVocê colocou \ntodos os lixinhos \nem suas devidas \nlixeiras!");
                            feedback.getStyleClass().add("feedback-parabens");
                            feedback.setTextAlignment(TextAlignment.CENTER);

                            PauseTransition delay = new PauseTransition(Duration.seconds(3));
                            delay.setOnFinished(e -> {
                                Scene creditosScene = Creditos.criar();
                                Stage stage = (Stage) areaDeJogo.getScene().getWindow();
                                stage.setScene(creditosScene);
                            });
                            delay.play();
                        }

                    //Se o lixinho for para a lixeira errada, aparece a mensagem de erro
                    } else {
                        feedback.setText("Errou!");
                        feedback.getStyleClass().add("feedback-erro");
                    }
                    
                    //Depois de 2s, a mensagem de feedback some
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            javafx.application.Platform.runLater(() -> feedback.setText(""));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    break;
                }
            }
            //Caso o lixinho esteja errado, ele volta para a posição inicial
            if (!acertou) {
                lixo.setLayoutX(startX);
                lixo.setLayoutY(startY);
            }
        });

        return lixo;
    }
    
    // Efeito de confetes quando todos os lixinhos são colocados corretamente
    private static void mostrarConfetes(Pane areaDeJogo) {
        for (int i = 0; i < 20; i++) {
            Circle confete = new Circle(5, Color.color(Math.random(), Math.random(), Math.random()));
            confete.setLayoutX(Math.random() * 400);
            confete.setLayoutY(Math.random() * 400);
            areaDeJogo.getChildren().add(confete);

            TranslateTransition animacao = new TranslateTransition(Duration.seconds(3), confete);
            animacao.setByY(-200);
            animacao.play();
            animacao.setOnFinished(event -> areaDeJogo.getChildren().remove(confete));
        }
    }
}