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

import org.graphstream.ui.geom.Point3;

public class EdgePoints {
	protected Point3[] points ;
	
	
	public EdgePoints(int n) {
		this.points = new Point3[n];
		
		for ( int i = 0 ; i < size() ; i++ )
			points[i] = new Point3();
	}


	public int size() {
		return points.length;
	}
	
	public void copy(Point3[] newPoints) {
		points = new Point3[newPoints.length];
		for ( int i = 0 ; i < size() ; i++ ) {
			points[i] = new Point3(newPoints[i]);
		}
	}
	
	public void set(int i, double x, double y, double z) {
		points[i].set(x, y, z);
	}
	
	public Point3 get(int i) {
		return points[i];
	}
	
	public Point3 apply(int i) {
		return points[i];
	}
	
	public void update(int i, Point3 coos) {
		points[i] = new Point3(coos.x, coos.y, coos.z);
	}
	
	@Override
	public String toString() {
		String s = "pts(";
		for (int i = 0 ; i < size() ; i++)
			s += points[i].toString()+", " ;
		s += ") : "+size();
		
		return s ;
	}
}
