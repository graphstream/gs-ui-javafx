package org.graphstream.ui.fx.renderer;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.fx.util.Selection;
import org.graphstream.ui.graphicGraph.GraphicGraph;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SelectionRenderer {
	
	private Selection selection;
	private GraphicGraph graph;
	
	protected Rectangle2D shape = new Rectangle2D();
	
	protected Color linesColor   = Color.rgb(240, 240, 240);
	protected Color linesColorQ  = Color.rgb(  0,   0,   0, 64);
	protected Color fillColor    = Color.rgb( 50,  50, 200, 32);
	protected Color strokeColorQ = Color.rgb( 50,  50, 200, 64);
	protected Color strokeColor  = Color.rgb(128, 128, 128);
			
	public SelectionRenderer(Selection selection, GraphicGraph graph) {
		this.selection = selection ;
		this.graph = graph ;
	}
	
	public void render(Backend bck, FxDefaultCamera camera, int panelWidth, int panelHeight) {
	    // XXX
	    // TODO make this an abstract class whose implementation are create by the back-end
	    // XXX
		if(selection.isActive() && selection.x1() != selection.x2() && selection.y1() != selection.y2()) {
			GraphicsContext g = bck.graphics2D();
			boolean quality = (graph.hasAttribute("ui.quality") || graph.hasAttribute("ui.antialias"));
			double x1 = selection.x1();
			double y1 = selection.y1();
			double x2 = selection.x2();
			double y2 = selection.y2();
			double t = 0.0;
			
			if(x1 > x2) { t = x1; x1 = x2; x2 = t; }
			if(y1 > y2) { t = y1; y1 = y2; y2 = t; }
   
			if(quality) {
				g.setStroke(linesColorQ);
				g.setFill(linesColorQ);
			}
			else {
				g.setStroke(linesColor);
				g.setFill(linesColor);
			}

			g.setLineWidth(1);
   
			g.strokeLine(0,(int) y1, panelWidth,(int) y1);
			g.strokeLine(0, (int)y2, panelWidth,(int) y2);
			g.strokeLine((int)x1, 0, (int)x1, panelHeight);
			g.strokeLine((int)x2, 0, (int)x2, panelHeight);
	
			shape.setFrame(x1, y1, x2-x1, y2-y1);
			
			if(quality) {
				g.setStroke(fillColor);
				g.setFill(fillColor);
								
				shape.drawByPoints(g);
				g.setStroke(linesColorQ);
				g.setFill(linesColorQ);
			}
			else {
				g.setStroke(strokeColor);
				g.setFill(strokeColor);
			}
   
			shape.drawByPoints(g);
		}
	}
}
