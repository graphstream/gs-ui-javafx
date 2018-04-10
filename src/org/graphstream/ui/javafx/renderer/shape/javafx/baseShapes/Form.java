/*
 * This file is part of GraphStream <http://graphstream-project.org>.
 * 
 * GraphStream is a library whose purpose is to handle static or dynamic
 * graph, create them from scratch, file or any source and display them.
 * 
 * This program is free software distributed under the terms of two licenses, the
 * CeCILL-C license that fits European law, and the GNU Lesser General Public
 * License. You can  use, modify and/ or redistribute the software under the terms
 * of the CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
 * URL <http://www.cecill.info> or under the terms of the GNU LGPL as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C and LGPL licenses and that you accept their terms.
 */

 /**
  * @author Antoine Dutot <antoine.dutot@graphstream-project.org>
  * @author Guilhelm Savin <guilhelm.savin@graphstream-project.org>
  * @author Hicham Brahimi <hicham.brahimi@graphstream-project.org>
  */
  
package org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes;

import org.graphstream.ui.javafx.util.StrokeFx;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.ClosePath;
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

/**
 * The interface and the classes that implement it used by all shapes in renderer.shape.javafx 
 * for create and display a javafx.scene.shape.Shape in a GraphicsContext.
 */
public interface Form  {	
	public void drawByPoints(GraphicsContext g, boolean stroke) ;
	public void setFrame(double x, double y, double w, double h);
	public Bounds getBounds() ;
	// used by the Double Stroke (see ShapeStroke.class)
	public String getIdForm();
	public Object getPath();
	
	public class Rectangle2D extends Rectangle implements Form {
		private StrokeFx strokeBig ;
		private StrokeFx strokeSmall ;
		private boolean doubleStroke = false ;
		private Paint fillColor = null ;
		
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
		
		public void drawByPoints(GraphicsContext g, boolean stroke) {
			if (doubleStroke) {
				strokeBig.changeStrokeProperties(g);
				g.strokeRoundRect(getX(), getY(), getWidth(), getHeight(), getArcWidth(), getArcHeight());
				strokeSmall.changeStrokeProperties(g);
				
				g.setFill(fillColor);
				g.setStroke(fillColor);
			}
			
			if(stroke) {
				g.strokeRoundRect(getX(), getY(), getWidth(), getHeight(), getArcWidth(), getArcHeight());
			}
			else {
				g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), getArcWidth(), getArcHeight());
			}
		}

		@Override
		public String getIdForm() {
			return "Rect";
		}

		@Override
		public double[][] getPath() {
			return path;
		}

		public void setRoundRect(double x, double y, double w, double h, double r, double r2) {
			setFrame(x, y, w, h);
			setArcWidth(r);
			setArcHeight(r);
		}

		@Override
		public Bounds getBounds() {
			return getBoundsInLocal();
		}

		public void setDoubleStroke(StrokeFx strokeBig, StrokeFx strokeSmall, Paint fillColor) {
			if ( fillColor != null ) {
				this.strokeSmall = strokeSmall;
				this.strokeBig = strokeBig;
				this.doubleStroke = true ;
				this.fillColor = fillColor ;
			}
		}
	}
	
	public class Path2D extends Path implements Form {
		private PathElement[] path ;
		private int size ;
		
		private double minX, minY, maxX, maxY ; // Bounds
		private boolean fillable;
		
		public Path2D(int nbElement, boolean fillable) {
			super();
			this.size = 0;
			this.path = new PathElement[nbElement];
			
			this.minX = Double.POSITIVE_INFINITY;
			this.maxX = Double.NEGATIVE_INFINITY;
			this.minY = Double.POSITIVE_INFINITY;
			this.maxY = Double.NEGATIVE_INFINITY;
			
			this.fillable = fillable ;
		}
		
		public void moveTo(double x, double y) {
			MoveTo moveTo = new MoveTo();
			moveTo.setX(x);
			moveTo.setY(y);
			getElements().add(moveTo);
			
			updateBounds(x, y);
			
			path[size] = moveTo ;
			size++;
		}
		
		public void lineTo(double x, double y) {
			LineTo lineTo = new LineTo(x, y);
			getElements().add(lineTo);
			
			updateBounds(x, y);
			
			path[size] = lineTo ;
			size++;
		}
		
		public void curveTo(double xc1, double yc1, double xc2, double yc2, double x1, double y1) {
			CubicCurveTo curveTo = new CubicCurveTo(xc1, yc1, xc2, yc2, x1, y1);
			getElements().add(curveTo);
			
			updateBounds(xc1, yc1);
			updateBounds(xc1, yc2);
			updateBounds(x1, y1);
			
			path[size] = curveTo ;
			size++;
		}
		
		public void quadTo(double cx, double cy, double x, double y) {
			QuadCurveTo quadTo = new QuadCurveTo(cx, cy, x, y);
			getElements().add(quadTo);
			
			updateBounds(cx, cy);
			updateBounds(x, y);
			
			path[size] = quadTo ;
			size++;
		}
		
		public void closePath() {
			ClosePath closePath = new ClosePath();
			getElements().add(closePath);
			
			path[size] = closePath ;
			size++ ;
		}
		
		public void drawByPoints(GraphicsContext g, boolean stroke) {
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
				else if (path[i] instanceof ClosePath) {
					g.closePath();
				}
				else {
					throw new RuntimeException("Shape unknown "+path[i]);
				}
			}
						
			if (!stroke && fillable)
				g.fill();
			else
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

		@Override
		public void setFrame(double x, double y, double w, double h) {
			throw new RuntimeException("SetFrame with Path2D ?");
		}
		
		@Override
		public Bounds getBounds() {
			return new BoundingBox(minX, minY, maxX-minX, maxY-minY);
		}
		
		private void updateBounds(double x, double y) {
			if(x < minX) minX = x ;
			if(x > maxX) maxX = x ;
			if(y < minY) minY = y ;
			if(y > maxY) maxY = y ;
		}	
	}

	public class CubicCurve2D extends CubicCurve implements Form {
		private double[][] path = new double[4][2];
		
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
		public void drawByPoints(GraphicsContext g, boolean stroke) {
			g.beginPath();
			g.moveTo(getStartX(), getStartY());
			g.bezierCurveTo(getControlX1(),getControlY1(), getControlX2(), getControlY2(), getEndX(), getEndY());
			
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
		
		@Override
		public void setFrame(double x, double y, double w, double h) {
			throw new RuntimeException("SetFrame with CubicCurve ?");
		}
		
		@Override
		public Bounds getBounds() {
			return getBoundsInLocal();
		}
	}
	
	public class Line2D extends Line implements Form {
		private double[][] path = new double[2][2];

		public Line2D() {
			super();
		}

		public Line2D(double x, double y, double x2, double y2) {
			super(x, y, x2, y2);
			path[0][0] = x ; path[0][1] = y ;
			path[1][0] = x2; path[1][1] = y2 ;
		}

		@Override
		public void drawByPoints(GraphicsContext g, boolean stroke) {
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

		@Override
		public void setFrame(double x, double y, double w, double h) {
			path[0][0] = x ; path[0][1] = y ;
			path[1][0] = w; path[1][1] = h ;
			
			setStartX(x);
			setStartY(y);
			setEndX(w);
			setEndY(h);
		}
		
		@Override
		public Bounds getBounds() {
			return getBoundsInLocal();
		}
	}

	public class Arc2D extends Arc implements Form {
		private Object[] path = new Object[8];
		
		public void setArcByCenter(double x, double y, double rad, double angleSt, double angleLen, ArcType type) {
			path[0] = x-rad ; path[1] = y-rad ;
			path[2] = rad*2; path[3] = rad*2 ;
			path[4] = angleSt ; path[5] = angleLen ;
			path[6] = type ; path[7] = type ;
			
			setCenterX(x-rad);
			setCenterY(y-rad);
			setRadiusX(rad*2);
			setRadiusY(rad*2);
			setStartAngle(angleSt);
			setLength(angleLen);
			setType(type);
		}
		
		@Override
		public void drawByPoints(GraphicsContext g, boolean stroke) {
			if (stroke)
				g.strokeArc(getCenterX(), getCenterY(), getRadiusX(), getRadiusY(), getStartAngle(), getLength(), getType());
			else
				g.fillArc(getCenterX(), getCenterY(), getRadiusX(), getRadiusY(), getStartAngle(), getLength(), getType());
		}
		
		@Override
		public String getIdForm() {
			return "Arc";
		}

		@Override
		public Object getPath() {
			return path;
		}
		
		@Override
		public void setFrame(double x, double y, double w, double h) {
			throw new RuntimeException("SetFrame with Arc2D ?");
		}
		
		@Override
		public Bounds getBounds() {
			return getBoundsInLocal();
		}
	}

	public class Ellipse2D extends Ellipse implements Form {
		private StrokeFx strokeBig ;
		private StrokeFx strokeSmall ;
		private boolean doubleStroke = false ;
		private Paint fillColor = null ;
		
		private double[][] path = new double[2][2];
		
		public void setFrameFromCenter(double centerX, double centerY, double cornerX, double cornerY) {
			setCenterX(centerX);
 	        setCenterY(centerY); 	        
 	        setRadiusX(cornerX-centerX);
 	        setRadiusY(cornerY-centerY);
			
			path[0][0] = centerX-(cornerX-centerX) ; path[0][1] = centerY-(cornerY-centerY) ;
			path[1][0] = (cornerX-centerX)*2 ; path[1][1] = (cornerY-centerY)*2;
			
			doubleStroke = false ;
		}
		
		public void setFrame(double x, double y, double cornerX, double cornerY) {
			// Setting the local bound
			setCenterX(x+(cornerX/2));
 	        setCenterY(y+(cornerY/2));
 	        setRadiusX(cornerX/2);
 	        setRadiusY(cornerY/2);
 	        
			path[0][0] = x ; path[0][1] = y ;
			path[1][0] = cornerX ; path[1][1] = cornerY ;
			
			doubleStroke = false ;
		}
		
		@Override
		public void drawByPoints(GraphicsContext g, boolean stroke) {
			if (doubleStroke) {
				strokeBig.changeStrokeProperties(g);
				g.strokeOval(path[0][0], path[0][1], path[1][0], path[1][1]);
				strokeSmall.changeStrokeProperties(g);
				
				g.setFill(fillColor);
				g.setStroke(fillColor);
			}
			
			if(stroke) {
				g.strokeOval(path[0][0], path[0][1], path[1][0], path[1][1]);
				//g.setStroke(Color.RED); g.strokeRect(getBoundsInLocal().getMinX(), getBoundsInLocal().getMinY(), getBoundsInLocal().getWidth(), getBoundsInLocal().getHeight());
			}
			else
				g.fillOval(path[0][0], path[0][1], path[1][0], path[1][1]);
		}

		@Override
		public String getIdForm() {
			return "Ellipse";
		}

		@Override
		public Object getPath() {
			return path;
		}
		
		@Override
		public Bounds getBounds() {
			return getBoundsInLocal();
		}

		public void setDoubleStroke(StrokeFx strokeBig, StrokeFx strokeSmall, Paint fillColor) {
			if ( fillColor != null ) {
				this.strokeSmall = strokeSmall;
				this.strokeBig = strokeBig;
				this.doubleStroke = true ;
				this.fillColor = fillColor ;
			}
		}
	}
}
