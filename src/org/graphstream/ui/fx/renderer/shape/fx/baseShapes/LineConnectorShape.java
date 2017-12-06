package org.graphstream.ui.fx.renderer.shape.fx.baseShapes;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.Skeleton;
import org.graphstream.ui.fx.renderer.shape.fx.shapePart.FillableLine;
import org.graphstream.ui.fx.renderer.shape.fx.shapePart.ShadowableLine;
import org.graphstream.ui.fx.renderer.shape.fx.shapePart.StrokableLine;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;

public abstract class LineConnectorShape extends ConnectorShape {
	
	public FillableLine fillableLine ;
	public StrokableLine strokableLine;
	public ShadowableLine shadowableLine;
	
	public LineConnectorShape() {
		this.fillableLine = new FillableLine() ;
		this.strokableLine = new StrokableLine() ;
		this.shadowableLine = new ShadowableLine() ;
	}
	
	
	public void configureForGroup(Backend bck, Style style, FxDefaultCamera camera) {
		super.configureForGroup(bck, style, camera);
		fillableLine.configureFillableLineForGroup(bck, style, camera, theSize);
		strokableLine.configureStrokableLineForGroup(style, camera);
		shadowableLine.configureShadowableLineForGroup(style, camera);
 	}
 
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, FxDefaultCamera camera) {
		fillableLine.configureFillableLineForElement(element.getStyle(), camera, element);
		super.configureForElement(bck, element, skel, camera);
	}
}