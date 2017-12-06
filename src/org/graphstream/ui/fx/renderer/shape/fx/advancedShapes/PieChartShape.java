package org.graphstream.ui.fx.renderer.shape.fx.advancedShapes;

import java.util.logging.Logger;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.AreaSkeleton;
import org.graphstream.ui.fx.renderer.Skeleton;
import org.graphstream.ui.fx.renderer.shape.Decorable;
import org.graphstream.ui.fx.renderer.shape.Shape;
import org.graphstream.ui.fx.renderer.shape.fx.Area;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Arc2D;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Ellipse2D;
import org.graphstream.ui.fx.renderer.shape.fx.shapePart.FillableMulticolored;
import org.graphstream.ui.fx.renderer.shape.fx.shapePart.Shadowable;
import org.graphstream.ui.fx.renderer.shape.fx.shapePart.Strokable;
import org.graphstream.ui.fx.util.AttributeUtils;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class PieChartShape extends FillableMulticolored implements Shape, AttributeUtils {
	Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA,
	        Color.CYAN, Color.ORANGE, Color.PINK};
	
	Strokable strokabe ;
	Shadowable shadowable ;
	Decorable decorable ;
	Area area ;
	
	Ellipse2D theShape = new Ellipse2D();
	double[] theValues = null ;
	Object valuesRef = null ;
	
	public PieChartShape() {
		strokabe = new Strokable();
		shadowable = new Shadowable();
		decorable = new Decorable();
		area = new Area();
	}
	
	@Override
	public void configureForGroup(Backend backend, Style style, FxDefaultCamera camera) {
		area.configureAreaForGroup(style, camera);
        configureFillableMultiColoredForGroup(style, camera);
        strokabe.configureStrokableForGroup(style, camera);
        shadowable.configureShadowableForGroup(style, camera);
        decorable.configureDecorableForGroup(style, camera);		
	}
	@Override
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel,
			FxDefaultCamera camera) {
		GraphicsContext g = bck.graphics2D();
		decorable.configureDecorableForElement(bck, camera, element, skel);
		area.configureAreaForElement(bck, camera, (AreaSkeleton)skel, element, decorable.theDecor);		
	}
	@Override
	public void make(Backend backend, FxDefaultCamera camera) {
        theShape.setFrameFromCenter(area.theCenter.x, area.theCenter.y, area.theCenter.x + area.theSize.x / 2, area.theCenter.y + area.theSize.y / 2);		
	}
	
	@Override
	public void makeShadow(Backend backend, FxDefaultCamera camera) {
		theShape.setFrameFromCenter(area.theCenter.x + shadowable.theShadowOff.x, area.theCenter.y + shadowable.theShadowOff.y,
				area.theCenter.x + (area.theSize.x + shadowable.theShadowWidth.x) / 2, area.theCenter.y + (area.theSize.y + shadowable.theShadowWidth.y) / 2);		
	}
	@Override
	public void render(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skel) {
		GraphicsContext g = bck.graphics2D();
		make(bck, camera);
		checkValues(element);
		fillPies(g, element);
		//fill(g, theSize, theShape)
		strokabe.stroke(g, theShape);
		decorable.decorArea(bck, camera, skel.iconAndText, element, theShape);
	}
	
	private void fillPies(GraphicsContext g, GraphicElement element) {
		if (theValues != null) {
			// we assume the pies values sum up to one. And we wont check it, its a mater of speed ;-).
			Arc2D arc = new Arc2D();
            double beg = 0.0;
            double end = 0.0;
            double col = 0;
            double sum = 0.0;
            
            for( int i = 0 ; i < theValues.length ; i++ ) {
            	double value = theValues[i];
            	end = beg + value;
                arc.setArcByCenter(area.theCenter.x, area.theCenter.y, area.theSize.x / 2, beg * 360, value * 360, ArcType.ROUND);
                
                g.setStroke(fillColors[(int) (col % fillColors.length)]);
                g.setFill(fillColors[(int) (col % fillColors.length)]);

                arc.drawByPoints(g);
                beg = end;
                sum += value;
                col += 1;
            }

            if (sum > 1.01f)
                Logger.getLogger(this.getClass().getSimpleName()).warning("[Sprite "+element.getId()+"] The sum of values for ui.pie-value should eval to 1 at max (actually "+sum+").");
        }
		else {
            // Draw a red empty circle to indicate "no value".
            g.setStroke(Color.RED);
            g.setFill(Color.RED);

            theShape.drawByPoints(g);
        }
	}

	private void checkValues(GraphicElement element) {
		Object pieValues = element.getAttribute("ui.pie-values");
	
		if (pieValues != null) {
			// Object oldRef = valuesRef;
			valuesRef = pieValues;
			// We use valueRef to avoid
			// recreating the values array for nothing.
			//if ((theValues == null) || (oldRef ne valuesRef)) {	// Cannot do this : the array reference can be the same and the values changed.
			theValues = getDoubles(valuesRef);
			//}
		}
	}

	@Override
	public void renderShadow(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skeleton) {
		makeShadow(bck, camera);
		shadowable.cast(bck.graphics2D(), theShape);		
	}	
}