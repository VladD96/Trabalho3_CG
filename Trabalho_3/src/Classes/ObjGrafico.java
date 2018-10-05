package Classes;

import java.util.LinkedList;
import javax.media.opengl.GL;

public class ObjGrafico {
	public GL gl;
	public LinkedList<Point4D> vertices;
	public int primitva = GL.GL_LINE_LOOP;
	public double tamanho = 2.0f;
	public int[] cor = new int[3];

	public ObjGrafico() {
		
	}

	public void atribuirGL(GL gl) {
		this.gl = gl;
	}

	public double obterTamanho() {
		return this.tamanho;
	}

	public int obterPrimitiva() {
		return this.primitva;
	}

	public void desenha() {

	}
}
