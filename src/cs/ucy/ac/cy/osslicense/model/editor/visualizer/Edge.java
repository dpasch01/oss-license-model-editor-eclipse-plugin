package cs.ucy.ac.cy.osslicense.model.editor.visualizer;

public class Edge {
	private final String name;

	public Edge(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}