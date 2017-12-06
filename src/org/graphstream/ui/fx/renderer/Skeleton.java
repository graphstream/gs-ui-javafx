package org.graphstream.ui.fx.renderer;

import org.graphstream.ui.fx.renderer.shape.fx.IconAndText;

/** Elements of rendering that, contrary to the shapes, are specific to the element, not the style
 * group and define the basic geometry of the shape. */
public abstract class Skeleton {
	public static String attributeName = "ui.j2dsk" ;
	
	public IconAndText iconAndText = null ;
	
	public enum EdgeShapeKind {
		LINE,
		CURVE,
		POLYLINE 
	}
	
	public class Triplet<X, Y, Z> { 
		public final X i; 
		public final Y sum; 
		public final Z ps; 
		
		public Triplet(X i, Y sum, Z ps) { 
			this.i = i; 
			this.sum = sum; 
			this.ps = ps;
		} 
	}
}