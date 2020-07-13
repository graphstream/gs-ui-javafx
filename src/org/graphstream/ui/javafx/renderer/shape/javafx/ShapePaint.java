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
  
package org.graphstream.ui.javafx.renderer.shape.javafx;

import org.graphstream.ui.graphicGraph.stylesheet.Colors;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;
import org.graphstream.ui.javafx.util.ColorManager;
import org.graphstream.ui.javafx.util.ImageCache;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public interface ShapePaint {
	
	public static float[] predefFractions2 = {0f, 1f} ;
	public static float[] predefFractions3 = {0f, 0.5f, 1f};
	public static float[] predefFractions4 = {0f, 0.33f, 0.66f, 1f };
	public static float[] predefFractions5 = {0f, 0.25f, 0.5f, 0.75f, 1f };
	public static float[] predefFractions6 = {0f, 0.2f, 0.4f, 0.6f, 0.8f, 1f };
	public static float[] predefFractions7 = {0f, 0.1666f, 0.3333f, 0.4999f, 0.6666f, 0.8333f, 1f };
	public static float[] predefFractions8 = {0f, 0.1428f, 0.2856f, 0.4284f, 0.5712f, 0.7140f, 0.8568f, 1f };
	public static float[] predefFractions9 = {0f, 0.125f, 0.25f, 0.375f, 0.5f, 0.625f, .75f, 0.875f, 1f };
	public static float[] predefFractions10= {0f, 0.1111f, 0.2222f, 0.3333f, 0.4444f, 0.5555f, 0.6666f, 0.7777f, 0.8888f, 1f };

	public static float[][] predefFractions = {null, null, predefFractions2, predefFractions3, predefFractions4, predefFractions5,
			predefFractions6, predefFractions7, predefFractions8, predefFractions9, predefFractions10};
	
	public static ShapePaint apply(Style style) {
		return ShapePaint.apply(style, false);
	}
	
	public static ShapePaint apply(Style style, boolean forShadow) {
		if( forShadow ) {
			switch (style.getShadowMode()) {
				case GRADIENT_VERTICAL:
					return new ShapeVerticalGradientPaint(createColors( style, true ) );
				case GRADIENT_HORIZONTAL:
					return new ShapeHorizontalGradientPaint(createColors( style, true ) );
				case GRADIENT_DIAGONAL1:
					return new ShapeDiagonal1GradientPaint(createColors( style, true ) );
				case GRADIENT_DIAGONAL2:
					return new ShapeDiagonal2GradientPaint(createColors( style, true ) );
				case GRADIENT_RADIAL:
					return new ShapeRadialGradientPaint(createColors( style, true ) );
				case PLAIN:
					return new ShapePlainColorPaint(ColorManager.getShadowColor(style, 0));
				case NONE:
					return null;
				default:
					return null;
			}
		}
		else {
			switch (style.getFillMode()) {
				case GRADIENT_VERTICAL:
					return new ShapeVerticalGradientPaint(createColors( style, false ));
				case GRADIENT_HORIZONTAL:
					return new ShapeHorizontalGradientPaint(createColors( style, false ));
				case GRADIENT_DIAGONAL1:
					return new ShapeDiagonal1GradientPaint(createColors( style, false ));
				case GRADIENT_DIAGONAL2:
					return new ShapeDiagonal2GradientPaint(createColors( style, false ));
				case GRADIENT_RADIAL:
					return new ShapeRadialGradientPaint(createColors( style, false ));
				case DYN_PLAIN:
					return new ShapeDynPlainColorPaint(createColors( style, false ));
				case PLAIN:
					return new ShapePlainColorPaint(ColorManager.getFillColor(style, 0));
				case IMAGE_TILED:
					return new ShapeImageTiledPaint(style.getFillImage());
				case IMAGE_SCALED:
					return new ShapeImageScaledPaint(style.getFillImage());
				case IMAGE_SCALED_RATIO_MAX:
					return new ShapeImageScaledRatioMaxPaint(style.getFillImage());
				case IMAGE_SCALED_RATIO_MIN:
					return new ShapeImageScaledRatioMinPaint(style.getFillImage());
				case NONE:
					return null;
				default:
					return null;
			}
		}
	}
	
	
	/**
	 * Create colors and fractions
	 * 
	 * The array of colors in the fill-color property of the style.
	 * 
	 * An array of floats regularly spaced in range [0,1], the number of floats is given by the
	 * style fill-color count.
	 * 
	 * @param style The style to use.
	 */
	static Stop[] createColors( Style style, boolean forShadow ) {
		if( forShadow )
			return createColors( style, style.getShadowColorCount(), style.getShadowColors(), style.getShadowColorCount()  );
		else
			return createColors( style, style.getFillColorCount(), style.getFillColors(), style.getFillColorCount() );
	}
 
	static Stop[] createColors( Style style, int nColor, Colors theColors, int nFraction ) {
		Stop[] colors = new Stop[nColor];
		
		float[] fractions = new float[nFraction]; 
		if ( nFraction >= predefFractions.length ) {
			float div = 1f / (nFraction - 1);

			for( int i = 0 ; i < nFraction ; i++ )
				fractions[i] = div * i;
	
			fractions[0] = 0f ;
			fractions[nFraction-1] = 1f ; 
		}
		
		for (int i = 0 ; i < theColors.size() ; i++) {
			if ( nFraction < 2 ) {
				colors[i] = new Stop(i, ColorManager.getColor(theColors.get(i))) ;
			}
			else {
				if( nFraction < predefFractions.length ) {
					colors[i] = new Stop(predefFractions[nFraction][i], ColorManager.getColor(theColors.get(i))) ;
				}
				else {
					colors[i] = new Stop(fractions[i], ColorManager.getColor(theColors.get(i))) ;
				}
			}
		}

		return colors ;
	}
	
	static Color interpolateColor( Colors colors, double value ) {
		int n = colors.size();
		Color c = ColorManager.getColor(colors.get(0));

		if( n > 1 ) {
			double v = value ;
			if( value < 0 ) 
				v = 0;
			else if( value > 1 )
				v = 1;
		
		
			if( v == 1 ) {
				c = ColorManager.getColor(colors.get(n-1));	// Simplification, faster.
			}
			else if( v != 0 ) {	// If value == 0, color is already set above.
				double div = 1.0 / (n-1);
				int col = (int) ( value / div ) ;
		
				div = ( value - (div*col) ) / div ;
		
				Color color0 = ColorManager.getColor(colors.get( col ));
				Color color1 = ColorManager.getColor(colors.get( col + 1 ));
				double red = ( ((color0.getRed()*255)  *(1-div)) + ((color1.getRed()*255)  *div) ) / 255f;
				double green = ( ((color0.getGreen()*255)*(1-div)) + ((color1.getGreen()*255)*div) ) / 255f;
				double blue = ( ((color0.getBlue()*255) *(1-div)) + ((color1.getBlue()*255) *div) ) / 255f;
				double alpha = ( ((color0.getOpacity()*255)*(1-div)) + ((color1.getOpacity()*255)*div) ) / 255f;
				
				c = new Color( red, green, blue, alpha );
			}
		}
	 
		return c;
	}

	default Color interpolateColor( Stop[] colors, double value ) {
		int n = colors.length;
		Color c = colors[0].getColor();

		if( n > 1 ) {
			double v = value ;
			if( value < 0 ) 
				v = 0;
			else if( value > 1 )
				v = 1;
		
		
			if( v == 1 ) {
				c = colors[n-1].getColor();	// Simplification, faster.
			}
			else if( v != 0 ) {	// If value == 0, color is already set above.
				double div = 1.0 / (n-1);
				int col = (int) ( value / div ) ;
		
				div = ( value - (div*col) ) / div ;
		
				Color color0 = colors[ col ].getColor();
				Color color1 = colors[ col + 1 ].getColor();
				double  red    = ( ((color0.getRed()*255)  *(1-div)) + ((color1.getRed()*255)  *div) ) / 255f;
				double green  = ( ((color0.getGreen()*255)*(1-div)) + ((color1.getGreen()*255)*div) ) / 255f;
				double blue   = ( ((color0.getBlue()*255) *(1-div)) + ((color1.getBlue()*255) *div) ) / 255f;
				double alpha  = ( ((color0.getOpacity()*255)*(1-div)) + ((color1.getOpacity()*255)*div) ) / 255f;
				
				c = new Color( red, green, blue, alpha );
			}
		}
	 
		return c;
	}

	public abstract class ShapeAreaPaint extends Area implements ShapePaint {
		public abstract Paint paint(double xFrom, double yFrom, double xTo, double yTo, double px2gu ) ;
		
		public Paint paint(Form shape, double px2gu) {
			Bounds s = shape.getBounds();
			
			return paint(s.getMinX(), s.getMinY(), s.getMaxX(), s.getMaxY(), px2gu) ;
		}
	}
	
	public abstract class ShapeColorPaint implements ShapePaint {
		public abstract Paint paint(double value, Color optColor);
	}
	
	
	public abstract class ShapeGradientPaint extends ShapeAreaPaint {
		protected Stop[] colors ;
		//protected float[] fractions ;

		public ShapeGradientPaint(Stop[] colors ) {
			this.colors = colors;
			//this.fractions = fractions;
		}
		
		public Paint paint(double xFrom, double yFrom, double xTo, double yTo, double px2gu ) {
			if( colors.length > 1 ) {
				double x0 = xFrom; 
				double y0 = yFrom;
				double x1 = xTo;  
				double y1 = yTo;
				
				if( x0 > x1 ) { double tmp = x0; x0 = x1; x1 = tmp ;}
				if( y0 > y1 ) { double tmp = y0; y0 = y1; y1 = tmp ;}
				if( x0 == x1 ) { x1 = x0 + 0.001f ;}
				if( y0 == y1 ) { y1 = y0 + 0.001f ;}
				
				return realPaint( x0, y0, x1, y1 );
			} 
			else {
				if( colors.length > 0 )
					return colors[0].getColor();
				else
					return Color.WHITE;
			}
		}
	  
		public abstract Paint realPaint( double x0, double y0, double x1, double y1) ;
	 }
	
	public class ShapeVerticalGradientPaint extends ShapeGradientPaint {
		public ShapeVerticalGradientPaint(Stop[] colors) {
			super(colors);
		}
		
		public Paint realPaint( double x0, double y0, double x1, double y1 ) {
			return new LinearGradient( x0, y0, x0, y1, false, CycleMethod.REFLECT, colors );
		}
	}
	
	public class ShapeHorizontalGradientPaint extends ShapeGradientPaint {
		public ShapeHorizontalGradientPaint(Stop[] colors) {
			super(colors);
		}
		
		public Paint realPaint( double x0, double y0, double x1, double y1 ) {
			return new LinearGradient( x0, y0, x1, y0, false, CycleMethod.REFLECT, colors );
		}
	}
	
	public class ShapeDiagonal1GradientPaint extends ShapeGradientPaint {
		public ShapeDiagonal1GradientPaint(Stop[] colors) {
			super(colors);
		}
		
		public Paint realPaint( double x0, double y0, double x1, double y1 ) {
			return new LinearGradient( x0, y0, x1, y1, false, CycleMethod.REFLECT, colors );
		}
	}
	
	public class ShapeDiagonal2GradientPaint extends ShapeGradientPaint {
		public ShapeDiagonal2GradientPaint(Stop[] colors) {
			super(colors);
		}
		
		public Paint realPaint( double x0, double y0, double x1, double y1 ) {
			return new LinearGradient( x0, y1, x1, y0, false, CycleMethod.REFLECT, colors );
		}
	}
	
	public class ShapeRadialGradientPaint extends ShapeGradientPaint {
		public ShapeRadialGradientPaint(Stop[] colors) {
			super(colors);
		}

		public Paint realPaint( double x0, double y0, double x1, double y1 ) {
			double w = ( x1 - x0 ) / 2;
			double h = ( y1 - y0 ) / 2;
			double cx = x0 + w;
			double cy = y0 + h;
			
			float rad = (float) h ;
			if( w > h )
				rad = (float) w ;
			
			return new RadialGradient( 0, 0, cx, cy, rad, false, CycleMethod.REFLECT, colors );
		}
	}
	
	public class ShapePlainColorPaint extends ShapeColorPaint {
		public Color color;
		public ShapePlainColorPaint( Color color ) {
			this.color = color ;
		}
		public Paint paint( double value, Color optColor ) { return this.color;}
	}
	
	public class ShapeDynPlainColorPaint extends ShapeColorPaint {
		public Stop[] colors;
		public ShapeDynPlainColorPaint( Stop[] colors ) {
			this.colors = colors ;
		}
		public Paint paint( double value, Color optColor ) {
			if(optColor != null) 
				return optColor ;
			else 
				return interpolateColor( colors, value );	
		}
	}
	
	public class ShapeImageTiledPaint extends ShapeAreaPaint {
		private String url ;
		
		public ShapeImageTiledPaint( String url ) {
			this.url = url ;
		}
		
		public Paint paint( double xFrom, double yFrom, double xTo, double yTo, double px2gu ) {
			Image img = ImageCache.loadImage(url) ;
			
			if (img != null) {
				return new ImagePattern( img, xFrom, yFrom, img.getWidth()/px2gu, -(img.getHeight()/px2gu), false );
			}
			else {
				img = ImageCache.dummyImage();
				return new ImagePattern( img, xFrom, yFrom, img.getWidth()*px2gu, -(img.getHeight()*px2gu), false );
			}
		}
	}
	
	public class ShapeImageScaledPaint extends ShapeAreaPaint {
		private String url ;
		
		public ShapeImageScaledPaint( String url ) {
			this.url = url ;
		}
		
		public Paint paint( double xFrom, double yFrom, double xTo, double yTo, double px2gu ) {
			Image img = ImageCache.loadImage(url) ;
			
			if (img != null) {
				return new ImagePattern( img, xFrom, yFrom, xTo-xFrom, -(yTo-yFrom), false );
			}
			else {
				img = ImageCache.dummyImage();
				return new ImagePattern(img, xFrom, yFrom, xTo-xFrom, -(yTo-yFrom), false );
			}
		}
	}
	
	public class ShapeImageScaledRatioMaxPaint extends ShapeAreaPaint {
		private String url ;
		
		public ShapeImageScaledRatioMaxPaint( String url ) {
			this.url = url ;
		}
		
		public Paint paint( double xFrom, double yFrom, double xTo, double yTo, double px2gu ) {
			Image img = ImageCache.loadImage(url) ;
			
			if (img != null) {
				double w = xTo-xFrom;
				double h = yTo-yFrom;
				double ratioi = (double)img.getWidth() / (double)img.getHeight();
				double ration = w / h;

				if( ratioi > ration ) {
					double neww = h * ratioi;
					return new ImagePattern( img, xFrom-((neww-w)/2), yFrom, neww, -h, false );
				} else {
					double newh = w / ratioi;
					return new ImagePattern( img, xFrom, yFrom-((newh-h)/2), w, -newh, false );
				}
			}
			else {
				img = ImageCache.dummyImage();
				return new ImagePattern(img, xFrom, yFrom, xTo-xFrom, -(yTo-yFrom), false );
			}
		}
	}
	
	public class ShapeImageScaledRatioMinPaint extends ShapeAreaPaint {
		private String url ;
		
		public ShapeImageScaledRatioMinPaint( String url ) {
			this.url = url ;
		}
		
		public Paint paint( double xFrom, double yFrom, double xTo, double yTo, double px2gu ) {
			Image img = ImageCache.loadImage(url) ;
			
			if (img != null) {
				double w = xTo-xFrom ;
				double h = yTo-yFrom ;
				double ratioi = (double)img.getWidth() / (double)img.getHeight();
				double ration = w / h ;

				if( ration > ratioi ) {
					double neww = h * ratioi ;
					return new ImagePattern( img, xFrom+((w-neww)/2), yFrom, neww, -h, false ) ;
				} else {
					double newh = w / ratioi ;
					return new ImagePattern( img, xFrom, yFrom-((h-newh)/2), w, -newh, false ) ;
				}
			}
			else {
				img = ImageCache.dummyImage();
				return new ImagePattern(img, xFrom, yFrom, xTo-xFrom, -(yTo-yFrom), false );
			}
		}
	}
}
