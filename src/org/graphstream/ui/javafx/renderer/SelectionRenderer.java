package org.graphstream.ui.javafx.renderer;

import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.javafx.util.ColorManager;
import org.graphstream.ui.javafx.util.Selection;
import org.graphstream.ui.javafx.util.StrokeFx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SelectionRenderer {
	
	private Selection selection;
	
	protected Rectangle2D shape = new Rectangle2D();
	
	protected Color linesColorQ  = ColorManager.getColor(new org.graphstream.ui.graphicGraph.stylesheet.Color(  0,   0,   0, 64));
	protected Color fillColor    = ColorManager.getColor(new org.graphstream.ui.graphicGraph.stylesheet.Color( 50,  50, 200, 32));
			
	public SelectionRenderer(Selection selection, GraphicGraph graph) {
		this.selection = selection ;
	}
	
	public void render(Backend bck, DefaultCamera2D camera, int panelWidth, int panelHeight) {
	    // XXX
	    // TODO make this an abstract class whose implementation are create by the back-end
	    // XXX
		if(selection.isActive() && selection.x1() != selection.x2() && selection.y1() != selection.y2()) {
			GraphicsContext g = bck.graphics2D();

			double x1 = selection.x1();
			double y1 = selection.y1();
			double x2 = selection.x2();
			double y2 = selection.y2();
			double t = 0.0;
			
			if(x1 > x2) { t = x1; x1 = x2; x2 = t; }
			if(y1 > y2) { t = y1; y1 = y2; y2 = t; }
			
			g.setStroke(linesColorQ);
			g.setFill(linesColorQ);

			StrokeFx stroke = new StrokeFx(1);
			stroke.changeStrokeProperties(g);
			
			g.strokeLine(0, y1, panelWidth, y1);
			g.strokeLine(0, y2, panelWidth, y2);
			g.strokeLine(x1, 0, x1, panelHeight);
			g.strokeLine(x2, 0, x2, panelHeight);
			
			shape.setFrame(x1, y1, x2-x1, y2-y1);
			
			g.setStroke(fillColor);
			g.setFill(fillColor);			
			shape.drawByPoints(g, false);
			g.setStroke(linesColorQ);
			g.setFill(linesColorQ);
			shape.drawByPoints(g, true);
		}
	}
}
