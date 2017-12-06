package org.graphstream.ui.fx.renderer.shape.fx.advancedShapes;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.Skeleton;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Path2D;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.LineConnectorShape;
import org.graphstream.ui.graphicGraph.GraphicElement;

import javafx.scene.canvas.GraphicsContext;

public class FreePlaneEdgeShape extends LineConnectorShape {
	Path2D theShape = new Path2D();

	@Override
	public void make(Backend backend, FxDefaultCamera camera) {
		make(camera, 0, 0, 0, 0);
	}
	
	private void make(FxDefaultCamera camera, double sox, double soy, double swx, double swy) {
		if (skel.multi() > 1 || skel.isLoop()) // is a loop or a multi edge
			makeMultiOrLoop(camera, sox, soy, swx, swy);
		else
			makeSingle(camera, sox, soy, swx, swy); // is a single edge.
	}

	private void makeMultiOrLoop(FxDefaultCamera camera, double sox, double soy, double swx, double swy) {
		if (skel.isLoop())
			makeLoop(camera, sox, soy, swx, swy);
		else
			makeMulti(camera, sox, soy, swx, swy);
	}

	private void makeLoop(FxDefaultCamera camera, double sox, double soy, double swx, double swy) {
		double fromx = skel.apply(0).x + sox;
		double fromy = skel.apply(0).y + soy;
		double tox = skel.apply(3).x + sox;
		double toy = skel.apply(3).y + soy;
		double c1x = skel.apply(1).x + sox;
		double c1y = skel.apply(1).y + soy;
		double c2x = skel.apply(2).x + sox;
		double c2y = skel.apply(2).y + soy;	

		theShape.reset();
		theShape.moveTo(fromx, fromy);
		theShape.curveTo(c1x, c1y, c2x, c2y, tox, toy);
	}

	private void makeMulti(FxDefaultCamera camera, double sox, double soy, double swx, double swy) {
		double fromx = skel.apply(0).x + sox;
		double fromy = skel.apply(0).y + soy;
		double tox = skel.apply(3).x + sox;
		double toy = skel.apply(3).y + soy;
		double c1x = skel.apply(1).x + sox;
		double c1y = skel.apply(1).y + soy;
		double c2x = skel.apply(2).x + sox;
		double c2y = skel.apply(2).y + soy;	
		
		theShape.reset();
		theShape.moveTo(fromx, fromy);
		theShape.curveTo(c1x, c1y, c2x, c2y, tox, toy);
	}

	private void makeSingle(FxDefaultCamera camera, double sox, double soy, double swx, double swy) {
		try {
			double fromx = skel.from().x + sox;
			double fromy = skel.from().y + soy - theSourceSize.y / 2;
			double tox = skel.to().x + sox;
			double toy = skel.to().y + soy - theTargetSize.y / 2;
			double length = Math.abs(skel.to().x - skel.from().x);
			double c1x = 0.0;
			double c1y = 0.0;
			double c2x = 0.0;
			double c2y = 0.0;
			
			if (skel.from().x < skel.to().x) {
			    // At right.
			    fromx += theSourceSize.x / 2;
			    tox -= theTargetSize.x / 2;
			    c1x = fromx + length / 3;
			    c2x = tox - length / 3;
			    c1y = fromy;
			    c2y = toy;
			} else {
			    // At left.
			    fromx -= theSourceSize.x / 2;
			    tox += theTargetSize.x / 2;
			    c1x = fromx - length / 3;
			    c2x = tox + length / 3;
			    c1y = fromy;
			    c2y = toy;
			}
			
			theShape.reset();
			theShape.moveTo(fromx, fromy);
			theShape.curveTo(c1x, c1y, c2x, c2y, tox, toy);
			
			// Set the connector as a curve.
			if (sox == 0 && soy == 0) {
			    skel.setCurve(
			        fromx, fromy, 0,
			        c1x, c1y, 0,
			        c2x, c2y, 0,
			        tox, toy, 0);
			}
		}
		catch(Exception e) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.WARNING, "FOUND!", e);
		}
	}

	@Override
	public void makeShadow(Backend backend, FxDefaultCamera camera) {
		if (skel.isCurve())
			makeMultiOrLoop(camera, shadowableLine.theShadowOff.x, shadowableLine.theShadowOff.y, shadowableLine.theShadowWidth, shadowableLine.theShadowWidth);
		else
			makeSingle(camera, shadowableLine.theShadowOff.x, shadowableLine.theShadowOff.y, shadowableLine.theShadowWidth, shadowableLine.theShadowWidth);
	}

	@Override
	public void render(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skeleton) {
		GraphicsContext g = bck.graphics2D();
		make(bck, camera);
		strokableLine.stroke(g, theShape);
		fillableLine.fill(g, theSize, theShape);
		decorable.decorConnector(bck, camera, skel.iconAndText, element, theShape);
	}

	@Override
	public void renderShadow(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skeleton) {
		makeShadow(bck, camera);
		shadowableLine.cast(bck.graphics2D(), theShape);
	}
}
