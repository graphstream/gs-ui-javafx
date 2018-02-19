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
package org.graphstream.stream.file.images;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.GraphRenderer;

import java.awt.image.BufferedImage;

public class FxImageRenderer implements ImageRenderer {
	private Canvas canvas;
	private WritableImage image;
	private FxGraphRenderer renderer;

	public FxImageRenderer() {
		this.renderer = new FxGraphRenderer();
	}

	@Override public void clear(int color) {
		PixelWriter pixelWriter = image.getPixelWriter();

		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++)
				pixelWriter.setArgb(x, y, color);
	}

	@Override public GraphRenderer<?, ?> getGraphRenderer() {
		return renderer;
	}

	@Override public BufferedImage getRenderedImage() {
		return SwingFXUtils.fromFXImage(image, null);
	}

	@Override public void init(Resolution resolution, FileSinkImages.OutputType outputType) {
		image = new WritableImage(resolution.getWidth(), resolution.getHeight());
		canvas = new Canvas(resolution.getWidth(), resolution.getHeight());
	}

	@Override public void render(int x, int y, int width, int height) {
		//
		// Little hack to initialize JavaFX Toolkit.
		//
		new JFXPanel();

		//
		// Rendering cannot be done outside the JavaFX thread.
		//
		Platform.runLater(() -> {
			GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
			renderer.render(graphicsContext, x, y, width, height);
			canvas.snapshot(null, image);

			synchronized (FxImageRenderer.this) {
				FxImageRenderer.this.notify();
			}
		});

		try {
			synchronized (this) {
				this.wait();
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
