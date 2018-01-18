package org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Fillable;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Shadowable;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Strokable;

public abstract class AreaConnectorShape extends ConnectorShape {
	
	public Fillable fillable ;
	public Strokable strokable ;
	public Shadowable shadowable ;
	
	public AreaConnectorShape() {
		this.fillable = new Fillable();
		this.strokable = new Strokable();
		this.shadowable = new Shadowable();
	}
	
	public void configureForGroup(Backend bck, Style style, DefaultCamera2D camera) {
		fillable.configureFillableForGroup(bck, style, camera);
		strokable.configureStrokableForGroup(style, camera);
		shadowable.configureShadowableForGroup(style, camera);
		super.configureForGroup(bck, style, camera);
 	}
 
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, DefaultCamera2D camera) {
		fillable.configureFillableForElement(element.getStyle(), camera, element);
		super.configureForElement(bck, element, skel, camera);
	}
}
