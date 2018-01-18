package org.graphstream.ui.javafx.renderer.shape;

import org.graphstream.ui.graphicGraph.GraphicEdge;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.StyleGroup;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.IconAndText;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapeDecor;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;

import javafx.geometry.Bounds;

/** Trait for shapes that can be decorated by an icon and/or a text. */
public class Decorable extends HasSkel {
	/** The string of text of the contents. */
	public String text = null;
 
	/** The text and icon. */
	public ShapeDecor theDecor = null ;
  
 	/** Paint the decorations (text and icon). */
 	public void decorArea(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, GraphicElement element, Form shape ) {
 	  	boolean visible = true ;
 	  	if( element != null ) visible = camera.isTextVisible( element );
 	  	if( theDecor != null && visible ) {
 	  		Bounds bounds = shape.getBounds();
 	  		theDecor.renderInside(backend, camera, iconAndText, bounds.getMinX(), bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY() );
 	  	}
 	}
	
	public void decorConnector(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, GraphicElement element, Form shape ) {
		boolean visible = true ;
 	  	if( element != null ) visible = camera.isTextVisible( element );
 	  	if( theDecor != null && visible ) {
 	  		if ( element instanceof GraphicEdge ) {
 	  			GraphicEdge edge = (GraphicEdge)element;
 	  			if((skel != null) && (skel.isCurve())) {
 	  				theDecor.renderAlong(backend, camera, iconAndText, skel);
 	  			} 
 	  			else {
 	  				theDecor.renderAlong(backend, camera, iconAndText, edge.from.x, edge.from.y, edge.to.x, edge.to.y);
 	  			}
 	  		}
 	  		else {
 	 	  		Bounds bounds = shape.getBounds();
 	  			theDecor.renderAlong(backend, camera, iconAndText, bounds.getMinX(), bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY() );
 	  		}
 	  	}
	}
	
	/** Configure all the static parts needed to decor the shape. */
  	public void configureDecorableForGroup( Style style, DefaultCamera2D camera) {
		/*if( theDecor == null )*/ theDecor = ShapeDecor.apply( style );
  	}
  	/** Setup the parts of the decor specific to each element. */
  	public void configureDecorableForElement(Backend backend, DefaultCamera2D camera, GraphicElement element, Skeleton skel) {
  		text = element.label;
  		if( skel != null ) {
  			StyleGroup style = element.getStyle();
  			skel.iconAndText = IconAndText.apply( style, camera, element );
  			if( style.getIcon() != null && style.getIcon().equals( "dynamic" ) && element.hasAttribute( "ui.icon" ) ) {
  				String url = element.getLabel("ui.icon").toString();
  				skel.iconAndText.setIcon(backend, url);
  			}
  			skel.iconAndText.setText(backend, element.label);
  		}
  	}
}