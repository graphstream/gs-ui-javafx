package org.graphstream.ui.javafx.renderer.shape.javafx;

import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Ellipse2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.javafx.util.ColorManager;
import org.graphstream.ui.javafx.util.StrokeFx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;

public abstract class ShapeStroke {
	public abstract StrokeFx stroke(double width, Form shape, Paint fillColor) ;

	public static ShapeStroke strokeForArea(Style style) {
		switch (style.getStrokeMode()) {
			case PLAIN: return new PlainShapeStroke();
			case DOTS: return new DotsShapeStroke();
			case DASHES: return new DashesShapeStroke();
			case DOUBLE: return new DoubleShapeStroke();
			default: return null ;
		}
	}
	
	public static ShapeStroke strokeForConnectorFill(Style style) {
		switch (style.getFillMode()) {
			case PLAIN: return new PlainShapeStroke();
			case DYN_PLAIN: return new PlainShapeStroke();
			case NONE: return null	; // Gracefully handled by the drawing part.
			default: return new PlainShapeStroke() ;
		}
	}
	
	public ShapeStroke strokeForConnectorStroke(Style style) {
		return strokeForArea(style);
	}
	
	public static Color strokeColor(Style style) {
		if( style.getStrokeMode() != org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.StrokeMode.NONE ) {
			return ColorManager.getStrokeColor(style, 0);
		} 
		else {
			return null;
		}
	}
}

class PlainShapeStroke extends ShapeStroke {
	private double oldWidth = 0.0 ;
	private StrokeFx oldStroke = null ;
	
	@Override
	public StrokeFx stroke(double width, Form shape, Paint fillColor) {
		return stroke(width);
	}
	
	public StrokeFx stroke(double width) {
		if( width == oldWidth ) {
			if( oldStroke == null ) 
				oldStroke = new StrokeFx( width );
			return oldStroke;
		} 
		else {
			oldWidth  = width;
			oldStroke = new StrokeFx( width );
			return oldStroke;
		}
	}
}

class DotsShapeStroke extends ShapeStroke {
	private double oldWidth = 0.0 ;
	private StrokeFx oldStroke = null ;
	
	@Override
	public StrokeFx stroke(double width, Form shape, Paint fillColor) {
		return stroke(width);
	}
	
	public StrokeFx stroke(double width) {
		if( width == oldWidth ) {
			if( oldStroke == null ) {
				oldStroke = new StrokeFx( width, width, StrokeLineCap.BUTT);
			}
			return oldStroke;
		} else {
			oldWidth = width;
			oldStroke = new StrokeFx( width, width, StrokeLineCap.BUTT);
			return oldStroke;
		}
	}
}

class DashesShapeStroke extends ShapeStroke {
	private double oldWidth = 0.0 ;
	private StrokeFx oldStroke = null ;
	
	@Override
	public StrokeFx stroke(double width, Form shape, Paint fillColor) {
		return stroke(width);
	}
	
	public StrokeFx stroke(double width) {
		if( width == oldWidth ) {
			if( oldStroke == null ){
				oldStroke = new StrokeFx( width, (3*width), StrokeLineCap.BUTT);
			}
			return oldStroke ;
		} else {
			oldWidth = width ;
			oldStroke = new StrokeFx( width, (3*width), StrokeLineCap.BUTT);
			return oldStroke ;
		}
	}	
}

class DoubleShapeStroke extends ShapeStroke {
	
	@Override
	public StrokeFx stroke(double width, Form shape, Paint fillColor) {
		return new CompositeStroke( new StrokeFx(width*3),  new StrokeFx(width), width, shape, fillColor);
	}
	
	class CompositeStroke extends StrokeFx {
		private StrokeFx stroke1 ;
		private StrokeFx stroke2 ;
		private Form form ;
		private Paint fillColor ;
		
		public CompositeStroke(StrokeFx stroke1, StrokeFx stroke2, double w, Form form, Paint fillColor) {
			super(w);
			this.stroke1 = stroke1 ;
			this.stroke2 = stroke2 ;
			this.form = form ;
			this.fillColor = fillColor ;
		}
		
		@Override
		public void changeStrokeProperties(GraphicsContext g) {
			// if form is null ==> debug in FillableLine 
			
			if (form.getIdForm().equals("Rect")) {
				((Rectangle2D)form).setDoubleStroke(stroke1, stroke2, fillColor);
			}
			else if (form.getIdForm().equals("Path")) {
				System.err.println("DoubleStroke for Path not implemented yet");
				stroke2.changeStrokeProperties(g);
			}
			else if (form.getIdForm().equals("CubicCurve")) {
				System.err.println("DoubleStroke for CubicCurve not implemented yet");
				stroke2.changeStrokeProperties(g);
			}
			else if (form.getIdForm().equals("Line")) {
				double[][] path = (double[][]) form.getPath();
				double x1 = path[0][0];
				double y1 = path[0][1];
				double x2 = path[1][0];
				double y2 = path[1][1];

				stroke2.changeStrokeProperties(g);
				double angle = Math.toDegrees(Math.atan2(y2-y1, x2-x1) - Math.atan2(1, 1));
				if ( angle >= 90 || angle <= -180 || (angle >= -90 && angle < 0)) {
					y1 += width*3 ;
					y2 += width*3 ;
				}
				else {
					x1 += width*3 ;
					x2 += width*3 ;
				}
				g.strokeLine(x1, y1, x2, y2);
			}
			else if (form.getIdForm().equals("Ellipse")) {
				((Ellipse2D)form).setDoubleStroke(stroke1, stroke2, fillColor);
			}
		}
	}
}

