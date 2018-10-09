package Classes;

import java.util.ArrayList;
import java.util.LinkedList;
import javax.media.opengl.GL;

public class ObjGrafico {
	public GL gl;
	public LinkedList<Point4D> vertices;
	public ArrayList<ObjGrafico> objGraficos;
	public Transform4d tranformacao;
	public int primitiva;
	public double tamanho;
	public float[] cor;

	public ObjGrafico(GL gl) {
		this.gl = gl;
		this.vertices = new LinkedList<Point4D>();
		this.objGraficos = new ArrayList<ObjGrafico>();
		this.tranformacao = new Transform4d();
		this.primitiva = GL.GL_LINE_LOOP;
		this.tamanho = 2.0f;

		this.cor = new float[3];
		this.cor[0] = 1.0f;
		this.cor[1] = 0.0f;
		this.cor[2] = 0.0f;
	}

	public void atribuirGL(GL gl) {
		this.gl = gl;
	}

	public double obterTamanho() {
		return this.tamanho;
	}

	public int obterPrimitiva() {
		return this.primitiva;
	}

	public void desenha() {		
		gl.glColor3f(this.cor[0], this.cor[1], this.cor[2]);
		gl.glLineWidth(3.0f);
		gl.glBegin(this.primitiva);
			for (Point4D ponto : this.vertices) {
				gl.glVertex2d(ponto.GetX(), ponto.GetY());
			}
		gl.glEnd();
	}
}
