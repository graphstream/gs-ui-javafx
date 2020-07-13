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
  
package org.graphstream.ui.javafx;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.StyleGroup;
import org.graphstream.ui.javafx.renderer.GraphBackgroundRenderer;
import org.graphstream.ui.javafx.renderer.shape.Shape;
import org.graphstream.ui.javafx.renderer.shape.javafx.advancedShapes.AngleShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.advancedShapes.BlobShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.advancedShapes.CubicCurveShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.advancedShapes.FreePlaneEdgeShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.advancedShapes.HorizontalSquareEdgeShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.advancedShapes.LSquareEdgeShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.advancedShapes.PieChartShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.arrowShapes.ArrowOnEdge;
import org.graphstream.ui.javafx.renderer.shape.javafx.arrowShapes.CircleOnEdge;
import org.graphstream.ui.javafx.renderer.shape.javafx.arrowShapes.DiamondOnEdge;
import org.graphstream.ui.javafx.renderer.shape.javafx.arrowShapes.ImageOnEdge;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.LineShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.PolylineEdgeShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes.CircleShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes.CrossShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes.DiamondShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes.FreePlaneNodeShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes.PolygonShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes.RoundedSquareShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes.SquareShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes.TriangleShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.spriteShapes.OrientableSquareShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.spriteShapes.SpriteArrowShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.spriteShapes.SpriteFlowShape;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class BackendJ2D implements Backend {
	
	private Pane surface ;
	private GraphicsContext g2 ;
	private Stack<Affine> matrixStack ;
	private Affine Tx ;
	private Affine xT ;
	private Point2D dummyPoint = new Point2D(-1, -1);
	
	public BackendJ2D() {
		surface = null ;
		g2 = null ;
		matrixStack = new Stack<>() ;
		Tx = null ;
		xT = null ;
	}
	
	public void setGraphicsContext(GraphicsContext g) {
		this.g2 = g ;
	}
		
	@Override
	public void open(Pane drawingSurface) {
		surface = drawingSurface ;
	}

	@Override
	public void close() {
		surface = null ;
	}

	@Override
	public void prepareNewFrame(GraphicsContext g) {
		this.g2 = g ;
		Tx = g2.getTransform();
		matrixStack.clear();
	}

	@Override
	public Point3 transform(double x, double y, double z) {
		dummyPoint = new Point2D(x, y);
		dummyPoint = Tx.transform(dummyPoint.getX(), dummyPoint.getY());
		return new Point3(dummyPoint.getX(), dummyPoint.getY(), 0);
	}

	@Override
	public Point3 inverseTransform(double x, double y, double z) {
		dummyPoint = new Point2D(x, y);
		dummyPoint = xT.transform(dummyPoint.getX(), dummyPoint.getY());
        return new Point3(dummyPoint.getX(), dummyPoint.getY(), 0);
	}

	@Override
	public Point3 transform(Point3 p) {
		dummyPoint = new Point2D(p.x, p.y);
		dummyPoint = Tx.transform(dummyPoint.getX(), dummyPoint.getY());
		p.set(dummyPoint.getX(), dummyPoint.getY(), 0);
		return p;
	}

	@Override
	public Point3 inverseTransform(Point3 p) {
		dummyPoint = new Point2D(p.x, p.y);
		dummyPoint = xT.transform(dummyPoint.getX(), dummyPoint.getY());
		p.set(dummyPoint.getX(), dummyPoint.getY(), 0);
		return p;
	}

	@Override
	public void pushTransform() {
		matrixStack.push(g2.getTransform());
	}

	@Override
	public void beginTransform() {}

	@Override
	public void setIdentity() {
		Tx.setToIdentity();
	}

	@Override
	public void translate(double tx, double ty, double tz) {
		g2.translate(tx, ty);
	}

	@Override
	public void rotate(double angle, double ax, double ay, double az) {
		g2.rotate(angle);
	}

	@Override
	public void scale(double sx, double sy, double sz) {
		g2.scale(sx, sy);
	}

	@Override
	public void endTransform() {
		Tx = g2.getTransform();
		computeInverse();
	}

	private void computeInverse() {
		try {
			xT = new Affine(Tx);
			if ( xT.determinant() != 0)
				xT.invert();
		}
		catch (NonInvertibleTransformException e) {
			 Logger.getLogger(this.getClass().getSimpleName()).log(Level.WARNING, "Cannot inverse matrix.", e);
		}
	}

	@Override
	public void popTransform() {
		assert(!matrixStack.isEmpty());
		g2.setTransform(matrixStack.pop());
	}

	@Override
	/**
	 * Antialiasing and Quality are set directly in the main Application (see DefaultApplication.class)
	 */
	public void setAntialias(Boolean on) {
		/*if(on) {
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,    RenderingHints.VALUE_STROKE_PURE);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		}
		else {
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,    RenderingHints.VALUE_STROKE_DEFAULT);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_OFF);
		}*/
	}

	@Override
	public void setQuality(Boolean on) {
		/*if(on) {
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,           RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,     RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		} else {
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,           RenderingHints.VALUE_RENDER_SPEED);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,       RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,     RenderingHints.VALUE_COLOR_RENDER_SPEED);
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		}*/
	}

	@Override
	public GraphicsContext graphics2D() {
		return g2 ;
	}

	@Override
	public Shape chooseNodeShape(Shape oldShape, StyleGroup group) {
		switch (group.getShape()) {
			case CIRCLE:
				if(oldShape instanceof CircleShape)	
					return oldShape ;
				else 								
					return new CircleShape();
			case BOX:
				if(oldShape instanceof SquareShape)
					return oldShape ;
				else
					return new SquareShape();
			case ROUNDED_BOX:
				if(oldShape instanceof RoundedSquareShape)	
					return oldShape ;
				else
					return new RoundedSquareShape();
			case DIAMOND:
				if(oldShape instanceof DiamondShape)	
					return oldShape ;
				else
					return new DiamondShape();
			case TRIANGLE:
				if(oldShape instanceof TriangleShape)
					return oldShape ;
				else
					return new TriangleShape();
			case CROSS:
				if(oldShape instanceof CrossShape)
					return oldShape ;
				else
					return new CrossShape();
			case FREEPLANE:
				if(oldShape instanceof FreePlaneNodeShape)	
					return oldShape ;
				else
					return new FreePlaneNodeShape();
			case PIE_CHART:
				if(oldShape instanceof PieChartShape)
					return oldShape ;
				else
					return new PieChartShape();
			case POLYGON:
				if(oldShape instanceof PolygonShape)	
					return oldShape ;
				else
					return new PolygonShape();
			//------
			case TEXT_BOX:
				Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY text-box shape not yet implemented **");  
				return new SquareShape();
			case TEXT_PARAGRAPH:
				Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY text-para shape not yet implemented **");  
				return new SquareShape();
			case TEXT_CIRCLE:
				Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY text-circle shape not yet implemented **");  
				return new CircleShape();
			case TEXT_DIAMOND:
				Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY text-diamond shape not yet implemented **");  
				return new CircleShape();
			case ARROW:
				Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY arrow shape not yet implemented **");  
				return new CircleShape();
			case IMAGES:
				Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY images shape not yet implemented **");  
				return new SquareShape();
			//----
			case JCOMPONENT:
				throw new RuntimeException("Jcomponent should have its own renderer");
			default:
				throw new RuntimeException(group.getShape().toString()+" shape cannot be set for nodes");
		}
	}

	@Override
	public Shape chooseEdgeShape(Shape oldShape, StyleGroup group) {
		switch(group.getShape()) {
			case LINE:
				if(oldShape instanceof LineShape)	
					return oldShape ;
				else 								
					return new LineShape();
			case ANGLE:
				if(oldShape instanceof AngleShape)	
					return oldShape ;
				else 								
					return new AngleShape();
			case BLOB:
				if(oldShape instanceof BlobShape)	
					return oldShape ;
				else 								
					return new BlobShape();
			case CUBIC_CURVE:
				if(oldShape instanceof CubicCurveShape)	
					return oldShape ;
				else 								
					return new CubicCurveShape();
			case FREEPLANE:
				if(oldShape instanceof FreePlaneEdgeShape)	
					return oldShape ;
				else 								
					return new FreePlaneEdgeShape();
			case POLYLINE:
				if(oldShape instanceof PolylineEdgeShape)	
					return oldShape ;
				else 								
					return new PolylineEdgeShape();
			case SQUARELINE:
				Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY square-line shape not yet implemented **");
				return new HorizontalSquareEdgeShape() ;
			case LSQUARELINE:
				if(oldShape instanceof LSquareEdgeShape)	
					return oldShape ;
				else 								
					return new LSquareEdgeShape();
			case HSQUARELINE:
				if(oldShape instanceof HorizontalSquareEdgeShape)	
					return oldShape ;
				else 								
					return new HorizontalSquareEdgeShape();
			case VSQUARELINE:
				Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY square-line shape not yet implemented **");
				return new HorizontalSquareEdgeShape() ;
			default:
				throw new RuntimeException(group.getShape()+" shape cannot be set for edges");
		}
	}

	@Override
	public Shape chooseEdgeArrowShape(Shape oldShape, StyleGroup group) {
		switch (group.getArrowShape()) {
			case NONE:
				return null ;
			case ARROW:
				if(oldShape instanceof ArrowOnEdge)	
					return oldShape ;
				else 								
					return new ArrowOnEdge();
			case CIRCLE:
				if(oldShape instanceof CircleOnEdge)	
					return oldShape ;
				else 								
					return new CircleOnEdge();
			case DIAMOND:
				if(oldShape instanceof DiamondOnEdge)	
					return oldShape ;
				else 								
					return new DiamondOnEdge();
			case IMAGE:
				if(oldShape instanceof ImageOnEdge)	
					return oldShape ;
				else 								
					return new ImageOnEdge();
			default:
				throw new RuntimeException(group.getArrowShape().toString()+" shape cannot be set for edge arrows");
		}
	}

	@Override
	public Shape chooseSpriteShape(Shape oldShape, StyleGroup group) {
		switch (group.getShape()) {
		case CIRCLE:
			if(oldShape instanceof CircleShape)	
				return oldShape ;
			else 								
				return new CircleShape();
		case BOX:
			if(oldShape instanceof OrientableSquareShape)
				return oldShape ;
			else
				return new OrientableSquareShape();
		case ROUNDED_BOX:
			if(oldShape instanceof RoundedSquareShape)	
				return oldShape ;
			else
				return new RoundedSquareShape();
		case DIAMOND:
			if(oldShape instanceof DiamondShape)	
				return oldShape ;
			else
				return new DiamondShape();
		case TRIANGLE:
			if(oldShape instanceof TriangleShape)
				return oldShape ;
			else
				return new TriangleShape();
		case CROSS:
			if(oldShape instanceof CrossShape)
				return oldShape ;
			else
				return new CrossShape();
		case ARROW:
			if(oldShape instanceof SpriteArrowShape)
				return oldShape ;
			else
				return new SpriteArrowShape();
		case FLOW:
			if(oldShape instanceof SpriteFlowShape)	
				return oldShape ;
			else
				return new SpriteFlowShape();
		case PIE_CHART:
			if(oldShape instanceof PieChartShape)
				return oldShape ;
			else
				return new PieChartShape();
		case POLYGON:
			if(oldShape instanceof PolygonShape)	
				return oldShape ;
			else
				return new PolygonShape();
		//------
		case TEXT_BOX:
			Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY text-box shape not yet implemented **");  
			return new SquareShape();
		case TEXT_PARAGRAPH:
			Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY text-para shape not yet implemented **");  
			return new SquareShape();
		case TEXT_CIRCLE:
			Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY text-circle shape not yet implemented **");  
			return new CircleShape();
		case TEXT_DIAMOND:
			Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY text-diamond shape not yet implemented **");  
			return new CircleShape();
		case IMAGES:
			Logger.getLogger(this.getClass().getSimpleName()).warning("** SORRY images shape not yet implemented **");  
			return new SquareShape();
		//----
		case JCOMPONENT:
			throw new RuntimeException("Jcomponent should have its own renderer");
		default:
			throw new RuntimeException(group.getShape().toString()+" shape cannot be set for nodes");
		}
	}

	@Override
	public GraphBackgroundRenderer chooseGraphBackgroundRenderer() {
		return null ;
	}

	@Override
	public Pane drawingSurface() {
		return surface ;
	}

}
