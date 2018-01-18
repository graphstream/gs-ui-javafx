package org.graphstream.ui.javafx;

import org.graphstream.ui.graphicGraph.StyleGroup;
import org.graphstream.ui.javafx.renderer.GraphBackgroundRenderer;
import org.graphstream.ui.javafx.renderer.shape.Shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public interface Backend extends org.graphstream.ui.view.camera.Backend {
    
    /** Called before any prior use of this back-end. */
    void open(Pane drawingSurface);
    
    /** Called after finished using this object. */
    void close();
    
    /** Setup the back-end for a new rendering session. */
    void prepareNewFrame(GraphicsContext g2);

    /** The Java2D graphics. */
    GraphicsContext graphics2D();
    
    Shape chooseNodeShape(Shape oldShape, StyleGroup group);
    Shape chooseEdgeShape(Shape oldShape, StyleGroup group); 
    Shape chooseEdgeArrowShape(Shape oldShape, StyleGroup group); 
    Shape chooseSpriteShape(Shape oldShape, StyleGroup group); 
    GraphBackgroundRenderer chooseGraphBackgroundRenderer();
    
    /** The drawing surface.
     * The drawing surface may be different than the one passed as
     * argument to open(), the back-end is free to create a new surface
     * as it sees fit. */
    Pane drawingSurface();
}	
