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
  
package org.graphstream.ui.javafx.util;

import java.util.logging.Logger;

import org.graphstream.ui.geom.Point3;

public interface AttributeUtils {
	
	default Point3[] getPoints(Object values) {
		if ( values instanceof Point3[] ) {
			if(((Point3[]) values).length == 0) {
				Logger.getLogger(this.getClass().getSimpleName()).info("0 ui.points");
			}
			return (Point3[]) values ;
		}
		else if (values instanceof Object[]) {
			Object[] tabValues = (Object[]) values ;

			if(tabValues.length > 0) {
				if (tabValues[0] instanceof Point3) {
					Point3[] res = new Point3[tabValues.length];
					for ( int i = 0 ; i < tabValues.length ; i++ ) {
						res[i] = (Point3) tabValues[i] ;
					}
					return res ;
				}
				else if (tabValues[0] instanceof Number) {
					int size = tabValues.length / 3;
					Point3[] res = new Point3[size];
					
					for (int i = 0 ; i < size ; i++) {
						res[i] = new Point3(((Number)tabValues[i*3]).doubleValue(),
								((Number)tabValues[i*3+1]).doubleValue(),
								((Number)tabValues[i*3+2]).doubleValue());
					}
					return res ;
				}
				else {
					Logger.getLogger(this.getClass().getSimpleName()).warning("Cannot interpret ui.points elements type "+((Object[]) values)[0].getClass().getName());
					return new Point3[0];
				}
			}
			else {
				Logger.getLogger(this.getClass().getSimpleName()).warning("ui.points array size is zero !");
				return new Point3[0];
			}
		}
		else if (values instanceof double[]) {
			double[] tabValues = ((double[]) values) ;
			if(tabValues.length > 0) {
				int size = tabValues.length / 3;
				Point3[] res = new Point3[size];
				
				for (int i = 0 ; i < size ; i++) {
					res[i] = new Point3(tabValues[i*3], tabValues[i*3+1],tabValues[i*3+2]);
				}
				return res ;
			}
			else {
				Logger.getLogger(this.getClass().getSimpleName()).warning("ui.points array size is zero !");
				return new Point3[0];
			}
		}
		else if (values instanceof float[] || values instanceof Float[]) {
			float[] tabValues = ((float[]) values) ;
			
			if(tabValues.length > 0) {
				int size = tabValues.length / 3;
				Point3[] res = new Point3[size];
				
				for (int i = 0 ; i < size ; i++) {
					res[i] = new Point3(tabValues[i*3], tabValues[i*3+1],tabValues[i*3+2]);
				}
				return res ;
			}
			else {
				Logger.getLogger(this.getClass().getSimpleName()).warning("ui.points array size is zero !");
				return new Point3[0];
			}
		}
		else {
			Logger.getLogger(this.getClass().getSimpleName()).warning("Cannot interpret ui.points contents "+values.getClass().getName());
			return new Point3[0];
		}
	}
	
	
	default double[] getDoubles(Object values) {
		if (values instanceof Object[]) {
			Object[] tabValues = (Object[]) values ;
			double[] result = new double[tabValues.length];
			
			for(int i = 0 ; i < tabValues.length ; i++) {
				if(tabValues[i] instanceof Number)		
					result[i] = ((Number)tabValues[i]).doubleValue();
				else if(tabValues[i] instanceof String)	
					result[i] = Double.parseDouble((String)tabValues[i]);
				else 
					result[i] = 0.0 ;
			}
			
			return result ;
		}
		else if (values instanceof double[]) {
			double[] tabValues = (double[]) values ;
			return tabValues ;
		}
		else if (values instanceof float[]) {
			float[] tabValues = (float[]) values ;
			double[] result = new double[tabValues.length];
			
			for(int i = 0 ; i < tabValues.length ; i++) {
				result[i] = (double)tabValues[i];
			}
			
			return result ;
		}
		else if (values instanceof int[]) {
			int[] tabValues = (int[]) values ;
			double[] result = new double[tabValues.length];
			
			for(int i = 0 ; i < tabValues.length ; i++) {
				result[i] = (double)tabValues[i];
			}
			
			return result ;
		}
		else if (values instanceof String[]) {
			String[] tabValues = (String[]) values ;
			double[] result = new double[tabValues.length];
			
			for(int i = 0 ; i < tabValues.length ; i++) {
				for(int j = 0 ; j < tabValues[i].split(",").length ; j++) {
					result[i] = Double.parseDouble(tabValues[i].split(",")[j]);	
				}
			}
			
			return result ;
		}
		else {
			Logger.getLogger(this.getClass().getSimpleName()).warning("Cannot extract double values from array "+values.getClass().getName());
			return new double[0];
		}
	}
	
	default Tuple<Point3, Point3> boundingBoxOfPoints(Point3[] points) {
		Double minx = Double.MAX_VALUE;
		Double miny = Double.MAX_VALUE;
		Double minz = Double.MAX_VALUE;
		Double maxx = Double.MIN_VALUE;
		Double maxy = Double.MIN_VALUE;
		Double maxz = Double.MIN_VALUE;
		
		for(int i = 0 ; i < points.length ; i++) {
			if(points[i].x<minx) minx = points[i].x ;
			if(points[i].y<miny) miny = points[i].y ;
			if(points[i].z<minz) minz = points[i].z ;
			if(points[i].x>maxx) maxx = points[i].x ;
			if(points[i].y>maxy) maxy = points[i].y ;
			if(points[i].z>maxz) maxz = points[i].z ;
		}
		
		return new Tuple<Point3, Point3>(new Point3(minx,  miny, minz), new Point3(maxx, maxy, maxz));
	}
	
	
	class Tuple<X, Y> { 
		public final X x; 
		public final Y y; 
		
		public Tuple(X x, Y y) { 
			this.x = x; 
			this.y = y; 
		} 
	}
}
