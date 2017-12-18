package org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.javafx.FxDefaultCamera;
import org.graphstream.ui.javafx.renderer.AreaSkeleton;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.Decorable;
import org.graphstream.ui.javafx.renderer.shape.Shape;
import org.graphstream.ui.javafx.renderer.shape.javafx.Area;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Fillable;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Shadowable;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Strokable;

public abstract class AreaShape extends Decorable implements Shape {
	
	public Fillable fillable ;
	public Strokable strokable ;
	public Shadowable shadowable ;
	public Area area ;
	
	public AreaShape() {
		this.fillable = new Fillable();
		this.strokable = new Strokable();
		this.shadowable = new Shadowable();
		this.area = new Area();
	}
	
	
	public void configureForGroup(Backend bck, Style style, FxDefaultCamera camera) {
 	  	fillable.configureFillableForGroup(bck, style, camera);
 	  	strokable.configureStrokableForGroup(style, camera);
 	  	shadowable.configureShadowableForGroup(style, camera);
 	  	configureDecorableForGroup(style, camera);
 	  	area.configureAreaForGroup(style, camera);
 	}
 
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, FxDefaultCamera camera) {
		fillable.configureFillableForElement(element.getStyle(), camera, element);
		configureDecorableForElement(bck, camera, element, skel);
		area.configureAreaForElement(bck, camera, (AreaSkeleton)skel, element, theDecor);
	}
}