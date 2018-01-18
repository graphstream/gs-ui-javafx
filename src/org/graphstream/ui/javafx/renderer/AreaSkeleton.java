package org.graphstream.ui.javafx.renderer;

import org.graphstream.ui.geom.Point2;

/** Skeleton for nodes and sprites. */
public class AreaSkeleton extends Skeleton implements org.graphstream.ui.view.camera.AreaSkeleton {
	public Point2 theCenter = new Point2(0, 0);
	public Point2 theSize = new Point2(0, 0);
	
	@Override
	public Point2 theSize() {
		// TODO Auto-generated method stub
		return theSize;
	}
	@Override
	public Point2 theCenter() {
		// TODO Auto-generated method stub
		return theCenter;
	}
}