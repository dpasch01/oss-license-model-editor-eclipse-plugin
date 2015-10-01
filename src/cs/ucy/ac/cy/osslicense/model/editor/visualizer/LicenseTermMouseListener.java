package cs.ucy.ac.cy.osslicense.model.editor.visualizer;

import java.awt.event.MouseEvent;

import edu.uci.ics.jung.visualization.control.GraphMouseListener;

public class LicenseTermMouseListener<V, E> implements GraphMouseListener<V>{

	@Override
	public void graphClicked(V arg0, MouseEvent arg1) {
		System.out.println("Clicked: "+arg0.toString());
	}

	@Override
	public void graphPressed(V arg0, MouseEvent arg1) {
		System.out.println("Pressed: "+arg0.toString());
	}

	@Override
	public void graphReleased(V arg0, MouseEvent arg1) {
		System.out.println("Released: "+arg0.toString());
	} 

}
