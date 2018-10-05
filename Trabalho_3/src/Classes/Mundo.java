package Classes;

import java.util.LinkedList;

public class Mundo {
	public Camera camera;
	public LinkedList<ObjGrafico> lisObjGrafico;
	public ObjGrafico poligonoSelecionado;
	
	public Mundo() {
		this.camera = new Camera();
		this.lisObjGrafico = new LinkedList<ObjGrafico>();
		this.poligonoSelecionado = null;
	}
}
