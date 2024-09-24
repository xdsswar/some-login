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

package xss.it.some.bg;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * @author XDSSWAR
 * Created on 09/24/2024
 */
public final class ImagePane extends AnchorPane {
    /**
     * The front layer pane.
     */
    private final LayerPane front;

    /**
     * The back layer pane.
     */
    private final LayerPane back;

    /**
     * Property to store a list of images.
     */
    private ListProperty<Image> images;

    /**
     * Property to store the delay before visibility changes, initialized to 4.
     */
    private final DoubleProperty visibilityDelay = new SimpleDoubleProperty(this, "visibilityDelay", 4);

    /**
     * Property to store the delay for transitions, initialized to 4.
     */
    private final DoubleProperty transitionDelay = new SimpleDoubleProperty(this, "transitionDelay", 2);

    /**
     * Current Image Index
     */
    private  int index = 0;

    /**
     * Transition
     */
    private FadeTransition transition;

    /**
     * Running flag
     */
    private boolean running = false;

    /**
     * Constructor for ImagePane.
     * Initializes the front and back layer panes.
     */
    public ImagePane() {
        this.front = new LayerPane();
        this.back = new LayerPane();

        initialize();
    }

    /**
     * Init
     */
    private void initialize(){
        getChildren().addAll(front,back);
        front.toFront();

        imagesProperty().addListener((obs, o, images) -> {
            if (images != null && !images.isEmpty() && !running){
                front.setImage(images.get(images.size()-1));
                start();
            }
            else {
                stop();
            }
        });
    }



    /**
     * Gets the observable list of images.
     *
     * @return the current observable list of images
     */
    public ObservableList<Image> getImages() {
        return imagesProperty().get();
    }

    /**
     * Returns the list property for images. Initializes the property
     * with an empty observable list if it's not already set.
     *
     * @return the list property for images
     */
    public ListProperty<Image> imagesProperty() {
        if (images == null) {
            images = new SimpleListProperty<>(this, "images", FXCollections.observableArrayList());
        }
        return images;
    }

    /**
     * Sets the list of images to the provided observable list.
     *
     * @param images the new list of images to set
     */
    public void setImages(ObservableList<Image> images) {
        imagesProperty().set(images);
    }

    /**
     * Gets the current visibility delay value.
     *
     * @return the visibility delay in integer format
     */
    public double getVisibilityDelay() {
        return visibilityDelay.get();
    }

    /**
     * Returns the property object for visibility delay.
     *
     * @return the IntegerProperty for visibility delay
     */
    public DoubleProperty visibilityDelayProperty() {
        return visibilityDelay;
    }

    /**
     * Sets the visibility delay to the specified value.
     *
     * @param visibilityDelay the new visibility delay value to set
     */
    public void setVisibilityDelay(double visibilityDelay) {
        this.visibilityDelay.set(visibilityDelay);
    }

    /**
     * Gets the current transition delay value.
     *
     * @return the transition delay in integer format
     */
    public double getTransitionDelay() {
        return transitionDelay.get();
    }

    /**
     * Returns the property object for transition delay.
     *
     * @return the IntegerProperty for transition delay
     */
    public DoubleProperty transitionDelayProperty() {
        return transitionDelay;
    }

    /**
     * Sets the transition delay to the specified value.
     *
     * @param transitionDelay the new transition delay value to set
     */
    public void setTransitionDelay(double transitionDelay) {
        this.transitionDelay.set(transitionDelay);
    }


    /**
     * Start
     */
    private void start(){
        running = true;
        if (getImages().isEmpty()){
            return;
        }
        front.opacityProperty().addListener((obs, o, opacity) -> {
            if (opacity.doubleValue() == 0){
                updateIndex(front);
            }
            else if (opacity.doubleValue() == 1){
                updateIndex(back);
            }
        });

        transition=new FadeTransition(Duration.seconds(getTransitionDelay()),front);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.setAutoReverse(true);
        transition.setCycleCount(-1);
        transition.play();
    }


    /**
     * Stop
     */
    public void stop(){
        if (transition!=null){
            transition.stop();
        }
    }

    /**
     * Update index and image
     * @param layerPane LayerPane
     */
    private void updateIndex(LayerPane layerPane) {
        PauseTransition pt;
        index ++;
        if (index == getImages().size()){
            index = 0;
        }

        layerPane.setImage(getImages().get(index));
        pt=new PauseTransition(Duration.seconds(getVisibilityDelay()));
        pt.setOnFinished(event -> transition.play());
        transition.pause();
        pt.play();
    }


    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        for (Node child : getChildren()) {
            if (child instanceof LayerPane layerPane){
                AnchorPane.setTopAnchor(layerPane,0d);
                AnchorPane.setRightAnchor(layerPane,0d);
                AnchorPane.setLeftAnchor(layerPane,0d);
                AnchorPane.setBottomAnchor(layerPane,0d);
            }else {
                System.out.println("Removing Children");
                getChildren().remove(child);
            }
        }
    }
}
