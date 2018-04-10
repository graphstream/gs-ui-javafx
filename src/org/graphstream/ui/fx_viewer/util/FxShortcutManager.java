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
  
package org.graphstream.ui.fx_viewer.util;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.util.ShortcutManager;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FxShortcutManager implements ShortcutManager {
	// Attributes

	/**
	 * The viewer to control.
	 */
	protected View view;
	
	protected double viewPercent = 1;
	
	protected Point3 viewPos = new Point3();
	
	protected double rotation = 0;
	
	// Construction
	
	public void init(GraphicGraph graph, View view) {
		this.view = view;
		view.addListener(KeyEvent.KEY_PRESSED, keyPressed);
	}
	
	public void release() {
		view.removeListener(KeyEvent.KEY_PRESSED, keyPressed);
	}
	
	// Events
	
	/**
	 * A key has been pressed.
	 * 
	 * @param event  The event that generated the key.
	 */
	EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(KeyEvent event) {
			Camera camera = view.getCamera();
		
			if (event.getCode() == KeyCode.PAGE_UP) {
				camera.setViewPercent(Math.max(0.0001f,
						camera.getViewPercent() * 0.9f));
			} else if (event.getCode() == KeyCode.PAGE_DOWN) {
				camera.setViewPercent(camera.getViewPercent() * 1.1f);
			} else if (event.getCode() == KeyCode.LEFT) {
				if (event.isAltDown()) {
					double r = camera.getViewRotation();
					camera.setViewRotation(r - 5);
				} else {
					double delta = 0;
		
					if (event.isShiftDown())
						delta = camera.getGraphDimension() * 0.1f;
					else
						delta = camera.getGraphDimension() * 0.01f;
		
					delta *= camera.getViewPercent();
		
					Point3 p = camera.getViewCenter();
					camera.setViewCenter(p.x - delta, p.y, 0);
				}
			} else if (event.getCode() == KeyCode.RIGHT) {
				if (event.isAltDown()) {
					double r = camera.getViewRotation();
					camera.setViewRotation(r + 5);
				} else {
					double delta = 0;
		
					if (event.isShiftDown())
						delta = camera.getGraphDimension() * 0.1f;
					else
						delta = camera.getGraphDimension() * 0.01f;
		
					delta *= camera.getViewPercent();
		
					Point3 p = camera.getViewCenter();
					camera.setViewCenter(p.x + delta, p.y, 0);
				}
			} else if (event.getCode() == KeyCode.UP) {
				double delta = 0;
		
				if (event.isShiftDown())
					delta = camera.getGraphDimension() * 0.1f;
				else
					delta = camera.getGraphDimension() * 0.01f;
		
				delta *= camera.getViewPercent();
		
				Point3 p = camera.getViewCenter();
				camera.setViewCenter(p.x, p.y + delta, 0);
			} else if (event.getCode() == KeyCode.DOWN) {
				double delta = 0;
		
				if (event.isShiftDown())
					delta = camera.getGraphDimension() * 0.1f;
				else
					delta = camera.getGraphDimension() * 0.01f;
		
				delta *= camera.getViewPercent();
		
				Point3 p = camera.getViewCenter();
				camera.setViewCenter(p.x, p.y - delta, 0);
			}
	    }
	};
}
