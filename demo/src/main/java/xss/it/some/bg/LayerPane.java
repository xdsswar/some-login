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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * @author XDSSWAR
 * Created on 09/24/2024
 */
public final class LayerPane extends AnchorPane {
    /**
     * Property to store a single image.
     */
    private ObjectProperty<Image> image;

    /**
     * Gets the current image.
     *
     * @return the current image
     */
    public Image getImage() {
        return imageProperty().get();
    }

    /**
     * Returns the object property for the image.
     *
     * @return the object property for the image
     */
    public ObjectProperty<Image> imageProperty() {
        if (image == null){
            image = new SimpleObjectProperty<>(this, "image", null);
        }
        return image;
    }

    /**
     * Sets the image to the provided value.
     *
     * @param image the new image to set
     */
    public void setImage(Image image) {
        imageProperty().set(image);
    }

    /**
     * Constructor for LayerPane.
     * Adds a listener to the image property to update the background image
     * whenever a new image is set.
     */
    public LayerPane() {
        imageProperty().addListener((obs, o, image) -> {
            if (image != null){
                setBackgroundImage(this, image);
            }
        });
    }

    /**
     * Set the Background Image
     * @param layer LayerPane layer
     * @param image Image
     */
    private static void setBackgroundImage(LayerPane layer, Image image){
        BackgroundSize size=new BackgroundSize(100, 100,true, true,true, true);
        BackgroundImage bg=new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                size
        );
        Background background=new Background(bg);
        layer.setBackground(background);
    }
}
