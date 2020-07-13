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

import java.io.FileNotFoundException;
import java.io.PrintStream;

/** Very simple logger for Frame-Per-Second measurements.
 */
public class FPSLogger {
	
	String fileName ;
	
	 /** Start time for a frame. */
    protected long T1 = 0;
    
    /** End Time for a frame. */
    protected long T2 = 0;
    
    /** Output channel. */
    protected PrintStream out = null;
    
    /** @param fileName The name of the file where measurements will be written, frame by frame. */
	public FPSLogger(String fileName) {
		this.fileName = fileName ;
	}
	
	/**Start a new frame measurement. */
	public void beginFrame() {
        T1 = System.currentTimeMillis();
    }
	
	public void endFrame() {
		T2 = System.currentTimeMillis();
				
        if(out == null) {
            try {
				out = new PrintStream(fileName);
			}
            catch (FileNotFoundException e) {	e.printStackTrace();	}
            out.println("# Each line is a frame.");
            out.println("# 1 FPS instantaneous frame per second");
            out.println("# 2 Time in milliseconds of the frame");
        }
        
        long time = T2 - T1;
        double fps  = 1000.0 / time;
        out.println(fps+".2f   "+time);
	}
	
	/** Ensure the log file is flushed and closed. Be careful, calling `endFrame()`
	 * after `close()` will reopen the log file and erase prior measurements. */
	public void close() {
		if(out != null) {
			out.flush();
			out.close();
			out = null;
		}
	}
}
