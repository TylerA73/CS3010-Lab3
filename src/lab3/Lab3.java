/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;

import java.util.ArrayList;
import java.io.File;
import java.io.FileFilter;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 *
 * @author francoc
 */

class ImageFilter implements FileFilter{

    @Override
    public boolean accept(File pathname) {
       String name = pathname.getName();
       if(name == null) return false;
       return name.toLowerCase().endsWith(".jpg")
               || name.toLowerCase().endsWith(".jpe")
               || name.toLowerCase().endsWith(".png");
               
    }
      
}
public class Lab3 extends Application {
    
    int i = 0;
    
    @Override
    public void start(Stage primaryStage) {
        BackgroundFill bgfill = new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgfill);

        BorderStroke stroke = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(1));
        Border border = new Border(stroke);
        
        BorderStroke lefthboxstroke = new BorderStroke(Color.SKYBLUE, Color.AZURE, Color.SKYBLUE, Color.SKYBLUE, 
                BorderStrokeStyle.DASHED, null, BorderStrokeStyle.DASHED, null, 
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY);
        
        Border lefthboxborder = new Border(lefthboxstroke);
        
        BorderStroke righthboxstroke = new BorderStroke(Color.SKYBLUE, Color.SKYBLUE, Color.SKYBLUE, Color.AZURE, 
                BorderStrokeStyle.DASHED, null, BorderStrokeStyle.DASHED, null, 
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY);
        
        Border righthboxborder = new Border(righthboxstroke);
        
        BorderStroke imageBorderStroke = new BorderStroke(Color.SKYBLUE, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(1));
        Border imageBorder = new Border(imageBorderStroke);
        
        DropShadow shadow = new DropShadow();
        shadow.setRadius(2);
        
        InnerShadow shadowHover = new InnerShadow();
        shadowHover.setRadius(2);
        
        InnerShadow shadowClick = new InnerShadow();
        shadowClick.setRadius(5);
        
        
        // Buttons
        Button nextbtn = new Button();
        Button prevbtn = new Button();
        nextbtn.setText("Next");
        prevbtn.setText("Previous");
        nextbtn.setAlignment(Pos.CENTER);
        prevbtn.setAlignment(Pos.CENTER);
        nextbtn.setBackground(bg);
        nextbtn.setBorder(border);
        prevbtn.setBackground(bg);
        prevbtn.setBorder(border);
        
        nextbtn.setEffect(shadow);
        prevbtn.setEffect(shadow);
        
        // VBox
        VBox leftbox = new VBox();
        VBox rightbox = new VBox();
        
        leftbox.getChildren().add(prevbtn);
        leftbox.setAlignment(Pos.CENTER);
        rightbox.getChildren().add(nextbtn);
        rightbox.setAlignment(Pos.CENTER);
        
        
        // HBox
        HBox top = new HBox();
        HBox center = new HBox();
        
        top.setPadding(new Insets(0, 5, 40, 5));
        top.setSpacing(100);
        top.setAlignment(Pos.TOP_CENTER);
        
        // Files
        File file = new File(".");
        File [] pics = file.listFiles(new ImageFilter());
        
        
        
        // images and ImageView
        ImageView img = new ImageView();
        img.setPreserveRatio(true);
        img.setFitHeight(300);
        
        // Array of images
        ArrayList imgArray = new ArrayList();
        for (File pic : pics) {
            Image picture = new Image("File:" + pic.getName());
            imgArray.add(picture);
        }
        /*
        for(int j = 0; j < pics.length; j++){
        Image picture = new Image("File:" + pics[j].getName());
        imgArray.add(picture);
        }
         */
       
        img.setImage((Image)imgArray.get(0));
        
        
         // Fonts and Text
        nextbtn.setFont(Font.font(STYLESHEET_MODENA, FontWeight.THIN, 15));
        prevbtn.setFont(Font.font(STYLESHEET_MODENA, FontWeight.THIN, 15));
        
        Text filename = new Text((String)pics[0].getName());
        
        
        //---Transitions---//
        FadeTransition out = new FadeTransition(Duration.millis(200), img);
        FadeTransition in = new FadeTransition(Duration.millis(200), img);
            
        out.setFromValue(1.0);
        out.setToValue(0);
        out.setCycleCount(1);
        out.setAutoReverse(false);
        
        in.setFromValue(0);
        in.setToValue(1.0);
        in.setCycleCount(1);
        in.setAutoReverse(false);
 
        
        
        
        //--- Panes ---//
        StackPane stackPane = new StackPane();
        stackPane.setMaxSize(img.getFitWidth(), img.getFitHeight());
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(img);
        stackPane.setBorder(imageBorder);
        
        // Border Pane
        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(bg);
        borderPane.setLeft(leftbox);
        borderPane.setRight(rightbox);
        borderPane.setCenter(stackPane);
        borderPane.setPrefSize(850, 800);
        borderPane.setBottom(top);
        borderPane.setTop(filename);
        
        
        
        Scene scene = new Scene(borderPane, 800, 850);
        
        
        //---Button Actions---///
        
        // Next Button
        nextbtn.setOnAction(e -> {
            
            out.play();
            
            if(i == imgArray.size() - 1){
                i = 0; //loops to the start of the array if we reach the end
            } else
                i++;
            out.setOnFinished(g -> {
                img.setImage((Image)imgArray.get(i));
                filename.setText((String)pics[i].getName());
                in.play();
            });
            
            
            
            nextbtn.setOnMouseReleased(t -> {
                nextbtn.setEffect(shadowHover);
            
            });
            
        });
        
        
        nextbtn.setOnMousePressed(e -> {
            nextbtn.setEffect(shadowClick);
            
        });
        
        
        nextbtn.setOnMouseEntered(e -> {
            nextbtn.setEffect(shadowHover);
            
        });
        
        nextbtn.setOnMouseExited(e -> {
            nextbtn.setEffect(shadow);
            
        });
        
        // Previous Button
        prevbtn.setOnAction(e -> {
            out.play();
            
            if(i == 0){
                i = imgArray.size() - 1; //loops to end of array if we reach the start
            } else
                i--; 
            out.setOnFinished(g -> {
                img.setImage((Image)imgArray.get(i));
                filename.setText((String)pics[i].getName());
                in.play();
            });
           
           prevbtn.setOnMouseReleased(t -> {
                prevbtn.setEffect(shadowHover);
            
            });
           e.consume();
        });
        
        prevbtn.setOnMousePressed(e -> {
            prevbtn.setEffect(shadowClick);
            
        });
        
        prevbtn.setOnMouseEntered(e -> {
            prevbtn.setEffect(shadowHover);
            
        });
        
        prevbtn.setOnMouseExited(e -> {
            prevbtn.setEffect(shadow);
            
        });
        
        // Key Events
        borderPane.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.RIGHT) {
                nextbtn.setEffect(shadowClick);
                out.play();
                if(i == imgArray.size() - 1){
                   i = 0; //loops to the start of the array if we reach the end
                } else
                i++;
                
            out.setOnFinished(g -> {
                img.setImage((Image)imgArray.get(i));
                filename.setText((String)pics[i].getName());
                in.play();
            });
            } else
                if (ke.getCode() == KeyCode.LEFT) {
                    prevbtn.setEffect(shadowClick);
                    out.play();
                if(i == 0){
                i = imgArray.size() - 1; //loops to the start of the array if we reach the end
            } else
                i--;
            out.setOnFinished(g -> {
                img.setImage((Image)imgArray.get(i));
                filename.setText((String)pics[i].getName());
                in.play();
            });
            }
            filename.setText((String)pics[i].getName());
            ke.consume();
        });
        
        borderPane.setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.RIGHT) {
                nextbtn.setEffect(shadow);
            } else
                if (ke.getCode() == KeyCode.LEFT) {
                    prevbtn.setEffect(shadow);
            } 
            ke.consume();   
        });
        
        
        // Swipe Events
        
       /*
        img.setOnSwipeRight(e -> {
            if(i == imgArray.size() - 1){
                i= 0; //loops to the start of the array if we reach the end
            } else
                i++;
            img.setImage((Image)imgArray.get(i));
            
        });
        
        img.setOnSwipeLeft(e -> {
            if(i == 0){
                i = imgArray.size() - 1; //loops to the start of the array if we reach the end
            } else
                i--;
            img.setImage((Image)imgArray.get(i));
            
        });
        */
        
        img.setOnMousePressed(e -> {
            //double xPressed = e.getX();
            
            img.setOnDragDetected(f ->{
                img.setOnMouseReleased(g -> {
                   // double xReleased = g.getX();
                    out.play();
                    if(g.getX() > e.getX()){
                        out.play();
                        if(i == imgArray.size() - 1){
                            i= 0; //loops to the start of the array if we reach the end
                        } else
                            i++;
                        out.setOnFinished(q -> {
                            img.setImage((Image)imgArray.get(i));
                            filename.setText((String)pics[i].getName());
                            in.play();
                        });
                    } 
                    else {
                        if(i == 0){
                            i = imgArray.size() - 1; //loops to the start of the array if we reach the end
                        } else
                            i--;
                        out.setOnFinished(q -> {
                            img.setImage((Image)imgArray.get(i));
                            filename.setText((String)pics[i].getName());
                            in.play();
                        });
                    }
                    e.consume();
                    g.consume();
                });
            });
        });
        
        
        
        primaryStage.setTitle("Image Viewer");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
