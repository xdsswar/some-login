/*
 * Copyright © 2024. XTREME SOFTWARE SOLUTIONS
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software or any portion of it is strictly prohibited and may
 * result in severe civil and criminal penalties. This code is the sole
 * proprietary of XTREME SOFTWARE SOLUTIONS.
 *
 * Commercialization, redistribution, and use without explicit permission
 * from XTREME SOFTWARE SOLUTIONS, are expressly forbidden.
 */

package xss.it.some.login;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import xss.it.some.Assets;
import xss.it.some.bg.ImagePane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author XDSSWAR
 * Created on 09/24/2024
 */
public final class LoginController implements Initializable {
    @FXML
    private AnchorPane base;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Button loginBrn;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField userInput;

    @FXML
    private VBox loginBox;

    /**
     * Pane for displaying the background image.
     */
    private final ImagePane backgroundPane;

    /**
     * Images
     */
    private static final List<Image> images;

    static {
        images = new ArrayList<>();
        images.add(new Image(Assets.load("/assets/1.png").toExternalForm()));
        images.add(new Image(Assets.load("/assets/2.jpg").toExternalForm()));
        images.add(new Image(Assets.load("/assets/3.jpg").toExternalForm()));
        images.add(new Image(Assets.load("/assets/4.jpg").toExternalForm()));
        /*
            Add images here
         */
    }

    private static final Image forCursor =
            new Image(Assets.load("/assets/cursor.gif")
                    .toExternalForm());

    /**
     * Constructor for LoginController.
     * Initializes the backgroundPane with a new ImagePane instance.
     */
    public LoginController() {
        this.backgroundPane = new ImagePane();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        base.getChildren().add(backgroundPane);
        backgroundPane.toBack();
        AnchorPane.setTopAnchor(backgroundPane,0d);
        AnchorPane.setRightAnchor(backgroundPane,0d);
        AnchorPane.setLeftAnchor(backgroundPane,0d);
        AnchorPane.setBottomAnchor(backgroundPane,0d);

        backgroundPane.setImages(FXCollections.observableArrayList(images));


        /*
         * Login event, add your logic
         */

        loginBrn.setOnAction(actionEvent -> {
            setLoadingCursor();
            simulateLoginDelay();
        });
    }

    /**
     * Sets a custom loading cursor and disables the login box.
     * The cursor is set to the center of the specified image.
     * <p>
     * If the scene is available, the cursor is updated to the custom cursor.
     */
    private void setLoadingCursor(){
        loginBox.setDisable(true);
        ImageCursor customCursor = new ImageCursor(forCursor,
                forCursor.getWidth() / 2,
                forCursor.getHeight() / 2
        );

       Scene scene = flowPane.getScene();
       if (scene != null){
           scene.setCursor(customCursor);
       }
    }

    /**
     * Simulates a login delay of 5 seconds.
     * During the delay, the cursor is set to default and the login box is disabled.
     * After the delay, the cursor is reset and the login box is enabled.
     */
    private void simulateLoginDelay(){
        PauseTransition pt = new PauseTransition(Duration.seconds(5));
        pt.setOnFinished(actionEvent -> {
            //Reset Cursor
            Scene scene = flowPane.getScene();
            if (scene != null){
                scene.setCursor(Cursor.DEFAULT);
            }
            loginBox.setDisable(false);
        });
        pt.play();
    }
}
