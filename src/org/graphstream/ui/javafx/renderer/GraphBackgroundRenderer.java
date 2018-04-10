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
  
package org.graphstream.ui.javafx.renderer;

import org.graphstream.ui.fx_viewer.util.GradientFactory;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.graphicGraph.StyleGroup;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.util.ColorManager;
import org.graphstream.ui.javafx.util.ImageCache;
import org.graphstream.ui.view.util.GraphMetrics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

/**
 * Renderer for the graph background.
 * 
 * This class is not a StyleRenderer because the graph is not a GraphicElement.
 * 
 * TODO XXX make this class an abstract one, and create several distinct back-ends.
 */
public class GraphBackgroundRenderer implements GraphicElement.SwingElementRenderer
{
	private GraphicGraph graph ;
	private StyleGroup style ;
	
	public GraphBackgroundRenderer(GraphicGraph graph, StyleGroup style) {
		this.graph = graph ;
		this.style = style ;
	}
	
	/**
     * Render a background indicating there is nothing to draw. 
	 */
	public void displayNothingToDo(Backend bck, int w, int h) {
		String msg1 = "Graph width/height/depth is zero !!";
		String msg2 = "Place components using the 'xyz' attribute." ;
		GraphicsContext g = bck.graphics2D() ;
		
		g.setStroke(Color.WHITE);
		g.setFill(Color.WHITE);
		g.fillRect( 0, 0, w, h );
		g.setStroke(Color.RED);
		g.setFill(Color.RED);
		g.strokeLine(0, 0, w, h);
		g.strokeLine(0, h, w, 0);
				
		final Text text1 = new Text(msg1);
		final Text text2 = new Text(msg2);
		text1.applyCss();
		text2.applyCss();
		
		double msg1length = text1.getLayoutBounds().getWidth();
		double msg2length = text2.getLayoutBounds().getWidth();
		int x = w / 2;
		int y = h / 2;
		
		g.setStroke(Color.BLACK);
		g.setFill(Color.BLACK);
		g.fillText(msg1, (float) (x - msg1length / 2), (float) (y - 20));
		g.fillText(msg2, (float) (x - msg2length / 2), (float) (y + 20));
	}
	
	
	public void render(Backend bck, DefaultCamera2D camera, int w, int h) {
		if ( (camera.graphViewport() == null) && camera.getMetrics().diagonal == 0
				&& (graph.getNodeCount() == 0 && graph.getSpriteCount() == 0)) {
			displayNothingToDo(bck, w, h);
		}
		else {
			renderGraphBackground(bck, camera);
			strokeGraph(bck, camera);
		}
	}

	private void renderGraphBackground(Backend bck, DefaultCamera2D camera) {
		GraphicsContext g = bck.graphics2D() ;
		switch (graph.getStyle().getFillMode()) {
		case NONE:
			break;
		case IMAGE_TILED: fillImageTiled(g, camera);
			break;
		case IMAGE_SCALED: fillImageScaled(g, camera, 0);
			break;
		case IMAGE_SCALED_RATIO_MAX: fillImageScaled(g, camera, 1);
			break;
		case IMAGE_SCALED_RATIO_MIN: fillImageScaled(g, camera, 2);
			break;
		case GRADIENT_DIAGONAL1: fillGradient(g, camera);
			break;
		case GRADIENT_DIAGONAL2: fillGradient(g, camera);
			break;
		case GRADIENT_HORIZONTAL: fillGradient(g, camera);
			break;
		case GRADIENT_VERTICAL: fillGradient(g, camera);
			break;
		case GRADIENT_RADIAL: fillGradient(g, camera);
			break;
		case DYN_PLAIN: fillBackground(g, camera);
			break;
		default: fillBackground(g, camera);
			break;
		}
	}

	private void fillBackground(GraphicsContext g, DefaultCamera2D camera) {
		GraphMetrics metrics = camera.getMetrics();
		
		g.setFill(ColorManager.getFillColor(style, 0));
		g.fillRect(0, 0, (int)metrics.viewport[2], (int)metrics.viewport[3]);
	}
	
	private void fillCanvasBackground(GraphicsContext g, DefaultCamera2D camera) {
		GraphMetrics metrics = camera.getMetrics();

		g.setFill(ColorManager.getCanvasColor(style, 0));
		g.fillRect( 0, 0, (int) metrics.viewport[2], (int) metrics.viewport[3]);
	}
	

	private void fillImageTiled(GraphicsContext g, DefaultCamera2D camera) {
		GraphMetrics metrics = camera.getMetrics();
		double px2gu = metrics.ratioPx2Gu;
		Image img = null ;
		
		img = ImageCache.loadImage(style.getFillImage());
		if ( img == null ) {
			img = ImageCache.dummyImage();
		}
		
		double gw    = ( metrics.graphWidthGU()  * px2gu ) ;// + ( padx * 2 )	// consider the padding ???
		double gh    = ( metrics.graphHeightGU() * px2gu ) ;// + ( pady * 2 )	// probably not.
		double x     = ( metrics.viewport[2] / 2 ) - ( gw / 2 ) ;
		double y     = metrics.viewport[3] - ( metrics.viewport[3] / 2 ) - ( gh / 2 ) ;
				
		g.setFill(new ImagePattern( img ));
		g.fillRect(0, 0, metrics.viewport[2], metrics.viewport[3]);
	}
	

	private void fillImageScaled(GraphicsContext g, DefaultCamera2D camera, int mode) {
		GraphMetrics metrics = camera.getMetrics();
		double px2gu = metrics.ratioPx2Gu;
		Image img = null ;
				
		img = ImageCache.loadImage(style.getFillImage());
		if ( img == null ) {
			img = ImageCache.dummyImage();
		}
				
		fillCanvasBackground( g, camera );
		double gw    = ( metrics.graphWidthGU()  * px2gu ) ;
		double gh    = ( metrics.graphHeightGU() * px2gu ) ;
		double x     = ( metrics.viewport[2] / 2 ) - ( gw / 2 ) ;
		double y     = metrics.viewport[3] - ( metrics.viewport[3] / 2 ) - ( gh / 2 ) ;
		
		if (mode == 0) { // Ratio
			g.drawImage(img, x, y, (x+gw), (y+gh), 0.0, 0.0, img.getWidth(), img.getHeight());
		}
		else if (mode == 1) { // Ratio-max
			double ratioi = (double)img.getWidth() / (double)img.getHeight();
			double ratiog = gw / gh;
			
			if(ratioi > ratiog) {
				double newgw = gh * ratioi;
				double newx  = x - ((newgw-gw)/2);
				g.drawImage( img, (int)newx, (int)y, (int)(newx+newgw), (int)(y+gh), 0, 0, img.getWidth(), img.getHeight() );
			}
			else {
				double newgh = gw / ratioi;
				double newy  = y - ((newgh-gh)/2);
				g.drawImage(img, (int)x, (int)newy, (int)(x+gw), (int)(newy+newgh),	0, 0, img.getWidth(), img.getHeight());
			}
		}
		else if (mode == 2) { // Ratio-min
			double ratioi = (double) img.getWidth() / (double) img.getHeight();
			double ratiog = gw / gh;
					
			if( ratiog > ratioi ) {
				double newgw = gh * ratioi;
				double newx  = x + ((gw-newgw)/2);
				g.drawImage( img, (int)newx, (int)y, (int)(newx+newgw), (int)(y+gh), 0, 0, img.getWidth(), img.getHeight() );
			}
			else {
				double newgh = gw / ratioi;
				double newy  = y + ((gh-newgh)/2);
				g.drawImage(img, (int)x, (int)newy, (int)(x+gw), (int)(newy+newgh),	0, 0, img.getWidth(), img.getHeight());
			}
		}
		else {
			throw new RuntimeException("Error graphBackground");
		}
	}

	private void strokeGraph(Backend bck, DefaultCamera2D camera) {
		GraphMetrics metrics = camera.getMetrics();
		GraphicsContext g = bck.graphics2D() ;
		
		if( style.getStrokeMode() != StyleConstants.StrokeMode.NONE && style.getStrokeWidth().value > 0 ) {
			g.setStroke( ColorManager.getStrokeColor(style, 0) );
			g.setFill( ColorManager.getStrokeColor(style, 0) );
			
			g.setLineWidth(metrics.lengthToGu( style.getStrokeWidth()));
			int padx = (int)metrics.lengthToPx( style.getPadding(), 0 ) ;
			int pady = padx ;
			if( style.getPadding().size() > 1 ) 
				pady = (int)metrics.lengthToPx( style.getPadding(), 1 );
			
			g.fillRect(padx, pady, (int)metrics.viewport[2] - padx*2, (int)metrics.viewport[3] - pady*2 );
		}
	}
	
	protected void fillGradient(GraphicsContext g, DefaultCamera2D camera) {
		GraphMetrics metrics = camera.getMetrics();

		if( style.getFillColors().size() < 2 ) {
			fillBackground( g, camera );
		}
		else {
			int w = (int)metrics.viewport[2] ; 
			int h = (int)metrics.viewport[3] ;
			
			g.setStroke( GradientFactory.gradientInArea( 0, 0, w, h, style ) );
			g.setFill( GradientFactory.gradientInArea( 0, 0, w, h, style ));
			
			g.fillRect( 0, 0, w, h );
		}
	}
	
}

