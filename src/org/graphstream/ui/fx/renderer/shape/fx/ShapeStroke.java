package org.graphstream.ui.fx.renderer.shape.fx;

import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form;
import org.graphstream.ui.fx.util.ColorManager;
import org.graphstream.ui.fx.util.StrokeFx;
import org.graphstream.ui.graphicGraph.stylesheet.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.StrokeLineCap;

public abstract class ShapeStroke {
	public abstract StrokeFx stroke(double width, Form shape) ;

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
	public StrokeFx stroke(double width, Form shape) {
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
	public StrokeFx stroke(double width, Form shape) {
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
	public StrokeFx stroke(double width, Form shape) {
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
	private double oldWidth = 0.0 ;
	private StrokeFx oldStroke = null ;
	
	@Override
	public StrokeFx stroke(double width, Form shape) {
		if(width == oldWidth) {
            if(oldStroke == null) 
            	oldStroke = new CompositeStroke( new StrokeFx(width*3),  new StrokeFx(width), width, shape);
            return oldStroke;
        } else {
            oldWidth = width;
            oldStroke = new CompositeStroke(new StrokeFx(width*2), new StrokeFx(width), width, shape);
            return oldStroke;
        }
	}
	
	class CompositeStroke extends StrokeFx {
		private StrokeFx stroke1 ;
		private StrokeFx stroke2 ;
		private Form form ;
		
		public CompositeStroke(StrokeFx stroke1, StrokeFx stroke2, double w, Form form) {
			super(w);
			this.stroke1 = stroke1 ;
			this.stroke2 = stroke2 ;
			this.form = form ;
		}
		
		@Override
		public void changeStrokeProperties(GraphicsContext g) {
			// if form is null ==> debug in FillableLine 
			
			if (form.getIdForm().equals("Rect")) {
				double[][] path = (double[][]) form.getPath();
				
				stroke2.changeStrokeProperties(g);
				g.strokeRect(path[0][0]+width, path[0][1]+width, path[1][0]-(2*width), path[1][1]-(2*width));
				stroke1.changeStrokeProperties(g);	
			}
			else if (form.getIdForm().equals("Path")) {
				PathElement[] path = (PathElement[]) form.getPath();
				
				stroke2.changeStrokeProperties(g);
				
				for (PathElement element : path) {
					if ( element instanceof MoveTo ) 
						drawMoveTo(g, (MoveTo)element) ;
					else if ( element instanceof LineTo )
						drawLineTo(g, (LineTo)element) ;
					else if ( element instanceof CubicCurveTo ) 
						drawCurveTo(g, (CubicCurveTo)element) ;
					else if ( element instanceof QuadCurveTo ) 
						drawQuadTo(g, (QuadCurveTo)element) ;
				}
				
				g.closePath();
				g.stroke();
				stroke1.changeStrokeProperties(g);
			}
			else if (form.getIdForm().equals("CubicCurve")) {
				double[][] path = (double[][]) form.getPath();
				
				if ( path[0][0] < path[0][1] ) path[0][0] += width ; else path[0][1] += width ;
				if ( path[1][0] < path[1][1] ) path[1][0] += width ; else path[1][1] += width ;
				if ( path[2][0] < path[2][1] ) path[2][0] += width ; else path[2][1] += width ;
				if ( path[3][0] < path[3][1] ) path[3][0] += width ; else path[3][1] += width ;
				
				stroke2.changeStrokeProperties(g);
				g.beginPath();
				g.moveTo(path[0][0], path[0][1]);
				g.bezierCurveTo(path[1][0],path[1][1], path[2][0], path[2][1], path[3][0], path[3][1]);
				g.closePath();
				g.stroke();
				stroke1.changeStrokeProperties(g);
			}
			else if (form.getIdForm().equals("Line")) {
				double[][] path = (double[][]) form.getPath();
				double x1 = path[0][0];
				double y1 = path[0][1];
				double x2 = path[1][0];
				double y2 = path[1][1];
				
				stroke2.changeStrokeProperties(g);
				double angle = Math.toDegrees(Math.atan2(y2-y1, x2-x1) - Math.atan2(1, 1));
				System.out.println(angle+" "+Math.atan2(1, 1));
				if ( angle >= 90 || (angle < 0 && angle >= -90) ) {
					y1 += width ;
					y2 += width ;
				}
				else {
					x1 += width ;
					x2 += width ;
				}
				g.strokeLine(x1, y1, x2, y2);
				stroke1.changeStrokeProperties(g);
			}
			else if (form.getIdForm().equals("Ellipse")) {
				double[][] path = (double[][]) form.getPath();
				
				stroke2.changeStrokeProperties(g);
				g.strokeOval(path[0][0]-width, path[0][1]-width, path[1][0]+(width*2), path[1][1]+(width*2));
				stroke1.changeStrokeProperties(g);
			}
		}

		private void drawQuadTo(GraphicsContext g, QuadCurveTo cT) {
			if ( cT.getControlX() < cT.getControlY() ) 
				cT.setControlX(cT.getControlX()+width) ; 
			else 
				cT.setControlY(cT.getControlY()+width) ;
			
			if ( cT.getX() < cT.getY() )
				cT.setX(cT.getX()+width); 	
			else
				cT.setY(cT.getY()+width);
			
			g.quadraticCurveTo(cT.getControlX(),cT.getControlY(), cT.getX(), cT.getY());
		}

		private void drawCurveTo(GraphicsContext g, CubicCurveTo cT) {			
			if ( cT.getControlX1() < cT.getControlY1() ) 
				cT.setControlX1(cT.getControlX1()+width) ; 
			else 
				cT.setControlY1(cT.getControlY1()+width) ;
			
			if ( cT.getControlX2() < cT.getControlY2() ) 
				cT.setControlX2(cT.getControlX2()+width) ;
			else
				cT.setControlY2(cT.getControlY2()+width) ;
			
			if ( cT.getX() < cT.getY() )
				cT.setX(cT.getX()+width); 	
			else
				cT.setY(cT.getY()+width);
			
			g.bezierCurveTo(cT.getControlX1(),cT.getControlY1(), cT.getControlX2(), cT.getControlY2(), cT.getX(), cT.getY());
		}

		private void drawLineTo(GraphicsContext g, LineTo lT) {
			double x = lT.getX();
			double y = lT.getY();
			
			if ( x < y )	x += width ; 
			else 			y += width ;
			g.lineTo(x, y);
		}
		
		private void drawMoveTo(GraphicsContext g, MoveTo mT) {
			if ( mT.getX() < mT.getY() )
				mT.setX(mT.getX()+width) ; 
			else 
				mT.setY(mT.getY()+width) ;
			
			g.moveTo(mT.getX(), mT.getY());
		}
	}
}

