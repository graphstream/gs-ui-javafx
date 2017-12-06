package org.graphstream.ui.fx.renderer.shape.fx.baseShapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;

public interface Form  {
	public void drawByPoints(GraphicsContext g) ;
	// used by the Double Stroke (see ShapeStroke.class)
	public String getIdForm();
	public Object getPath();
	
	public class Rectangle2D extends Rectangle implements Form {
		double[][] path = new double[2][2];
		
		public void setFrame(double x, double y, double w, double h) {
			/*path[0][0] = x ; path[0][1] = y ;
			path[1][0] = x+w ; path[1][1] = y ;
			path[2][0] = x+w ; path[2][1] = y-h ;
			path[3][0] = x ; path[3][1] = y-h ;*/
			
			path[0][0] = x ; path[0][1] = y ;
			path[1][0] = w; path[1][1] = y ;
			
			setX(x);
			setY(y);
			setWidth(w);
			setHeight(h);
		}
		
		public void drawByPoints(GraphicsContext g) {
			g.strokeRect(getX(), getY(), getWidth(), getHeight());
		}

		@Override
		public String getIdForm() {
			return "Rect";
		}

		@Override
		public double[][] getPath() {
			return path;
		}
	}
	
	public class Path2D extends Path implements Form {
		
		PathElement[] path = new PathElement[10];
		
		int size = 0;
		
		public void reset() {
			path = new PathElement[10];
			size = 0 ;		
			
			getElements().removeAll();
		}
		
		public void moveTo(double x, double y) {
			MoveTo moveTo = new MoveTo();
			moveTo.setX(x);
			moveTo.setY(y);
			getElements().add(moveTo);
			
			path[size] = moveTo ;
			size++;
		}
		
		public void lineTo(double x, double y) {
			LineTo lineTo = new LineTo(x, y);
			getElements().add(lineTo);
			
			path[size] = lineTo ;
			size++;
		}
		
		public void curveTo(double xc1, double yc1, double xc2, double yc2, double x1, double y1) {
			CubicCurveTo curveTo = new CubicCurveTo(xc1, yc1, xc2, yc2, x1, y1);
			getElements().add(curveTo);
			
			path[size] = curveTo ;
			size++;
		}
		
		public void quadTo(double cx, double cy, double x, double y) {
			QuadCurveTo quadTo = new QuadCurveTo(cx, cy, x, y);
			getElements().add(quadTo);
			
			path[size] = quadTo ;
			size++;
		}
		
		
		public void drawByPoints(GraphicsContext g) {
			g.beginPath();
			
			g.moveTo(((MoveTo)path[0]).getX(), ((MoveTo)path[0]).getY());
			for (int i = 1 ; i < size ; i++) {
				if (path[i] instanceof LineTo) {
					double x = ((LineTo)path[i]).getX();
					double y = ((LineTo)path[i]).getY();
					
					g.lineTo(x, y);	
				}
				else if (path[i] instanceof CubicCurveTo) {
					double xc1 = ((CubicCurveTo)path[i]).getControlX1() ;
					double yc1 = ((CubicCurveTo)path[i]).getControlY1() ;
					double xc2 = ((CubicCurveTo)path[i]).getControlX2() ;
					double yc2 = ((CubicCurveTo)path[i]).getControlY2() ;
					double x1 = ((CubicCurveTo)path[i]).getX(); 
					double y1 = ((CubicCurveTo)path[i]).getY();
					
					g.bezierCurveTo(xc1, yc1, xc2, yc2, x1, y1);
				}
				else if (path[i] instanceof QuadCurveTo) {
					double xc = ((QuadCurveTo)path[i]).getControlX() ;
					double yc = ((QuadCurveTo)path[i]).getControlY() ;
					double x = ((QuadCurveTo)path[i]).getX(); 
					double y = ((QuadCurveTo)path[i]).getY();
					
					g.quadraticCurveTo(xc, yc, x, y);
				}
				else {
					throw new RuntimeException("Shape unknown "+path[i]);
				}
			}
			g.closePath();
			g.stroke();
		}

		@Override
		public String getIdForm() {
			return "Path";
		}

		@Override
		public PathElement[] getPath() {
			return path;
		}
	}

	public class CubicCurve2D extends CubicCurve implements Form {
		double[][] path = new double[4][2];
		
		public CubicCurve2D() {
			super();
		}
		
		public CubicCurve2D(double xFrom, double yFrom, double xCtrl1, double yCtrl1, double xCtrl2, double yCtrl2, double xTo, double yTo) {
			super(xFrom, yFrom, xCtrl1, yCtrl1, xCtrl2, yCtrl2, xTo, yTo);
			path[0][0] = xFrom ; path[0][1] = yFrom ;
			path[1][0] = xCtrl1 ; path[1][1] = yCtrl1 ;
			path[2][0] = xCtrl2 ; path[2][1] = yCtrl2 ;
			path[3][0] = xTo ; path[3][1] = yTo ;
		}

		@Override
		public void drawByPoints(GraphicsContext g) {
			g.beginPath();
			g.moveTo(getStartX(), getStartY());
			g.bezierCurveTo(getControlX1(),getControlY1(), getControlX2(), getControlY2(), getEndX(), getEndY());
			g.closePath();
			g.stroke();
		}

		@Override
		public String getIdForm() {
			return "CubicCurve";
		}

		@Override
		public double[][] getPath() {
			return path;
		}	
	}

	public class Line2D extends Line implements Form {
		double[][] path = new double[2][2];

		public Line2D() {
			super();
		}

		public Line2D(double x, double y, double x2, double y2) {
			super(x, y, x2, y2);
			path[0][0] = x ; path[0][1] = y ;
			path[1][0] = x2; path[1][1] = y2 ;
		}

		@Override
		public void drawByPoints(GraphicsContext g) {
			g.strokeLine(getStartX(), getStartY(), getEndX(), getEndY());
		}

		@Override
		public String getIdForm() {
			return "Line";
		}

		@Override
		public double[][] getPath() {
			return path;
		}
	}

	public class Arc2D extends Arc implements Form {
		Object[] path = new Object[8];
		
		public void setArcByCenter(double x, double y, double rad, double angleSt, double angleLen, ArcType type) {
			path[0] = x ; path[1] = y ;
			path[2] =rad; path[3] = rad ;
			path[4] = angleSt ; path[5] = angleLen ;
			path[6] = type ; path[7] = type ;
			
			
			setCenterX(x);
			setCenterY(y);
			setRadiusX(rad);
			setRadiusY(rad);
			setStartAngle(angleSt);
			setLength(angleLen);
			setType(type);
		}
		
		@Override
		public void drawByPoints(GraphicsContext g) {
			g.strokeArc(getCenterX(), getCenterY(), getRadiusX(), getRadiusY(), getStartAngle(), getLength(), getType());
		}

		@Override
		public String getIdForm() {
			return "Arc";
		}

		@Override
		public Object getPath() {
			return path;
		}
		
	}

	public class Ellipse2D extends Ellipse implements Form {
		
		double[][] path = new double[2][2];
		
		public void setFrameFromCenter(double centerX, double centerY, double cornerX, double cornerY) {
			setCenterX(centerX);
			setCenterY(centerY);
			setRadiusX(cornerX);
			setRadiusY(cornerY);
			
			path[0][0] = centerX ; path[0][1] = centerY ;
			path[1][0] = cornerX ; path[1][1] = cornerY ;
		}
		
		@Override
		public void drawByPoints(GraphicsContext g) {
			g.strokeOval(getCenterX(), getCenterY(), getRadiusX(), getRadiusY());
		}

		@Override
		public String getIdForm() {
			return "Ellipse";
		}

		@Override
		public Object getPath() {
			return path;
		}
	}
}
