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

	private Point4D[] verticesMatriz = { 	
			new Point4D(10.0, 10.0, 0.0, 1.0),
			new Point4D(20.0, 10.0, 0.0, 1.0), 
			new Point4D(20.0, 20.0, 0.0, 1.0),
			new Point4D(10.0, 20.0, 0.0, 1.0) };

	private Transform4d matrizObjeto;
	private Transform4d matrizTmpTranslacao;
	private Transform4d matrizTmpTranslacaoInversa;
	private Transform4d matrizTmpEscala;
	private Transform4d matrizGlobal;

	public ObjGrafico(GL gl) {
		this.gl = gl;
		this.vertices = new LinkedList<Point4D>();
		this.objGraficos = new ArrayList<ObjGrafico>();
		this.tranformacao = new Transform4d();
		this.primitiva = GL.GL_LINE_LOOP;
		this.tamanho = 2.0f;
		
		this.matrizObjeto = new Transform4d();
		this.matrizTmpTranslacao = new Transform4d();
		this.matrizTmpTranslacaoInversa = new Transform4d();
		this.matrizTmpEscala = new Transform4d();
		this.matrizGlobal = new Transform4d();

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

	public void translacaoXYZ(double tx, double ty, double tz) {
		Transform4d matrizTranslate = new Transform4d();
		matrizTranslate.atribuirTranslacao(tx,ty,tz);
		matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);		
	}

	public void escalaXYZ(double Sx,double Sy) {
		Transform4d matrizScale = new Transform4d();		
		matrizScale.atribuirEscala(Sx,Sy,1.0);
		matrizObjeto = matrizScale.transformMatrix(matrizObjeto);
	}

	public void atribuirIdentidade() {
		matrizObjeto.atribuirIdentidade();
	}

	public void escalaXYZPtoFixo(double escala, Point4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.GetX(),ptoFixo.GetY(),ptoFixo.GetZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirEscala(escala, escala, 1.0);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.GetX(),ptoFixo.GetY(),ptoFixo.GetZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}
	
	public void rotacaoZPtoFixo(double angulo, Point4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.GetX(),ptoFixo.GetY(),ptoFixo.GetZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirRotacaoZ(Transform4d.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.GetX(),ptoFixo.GetY(),ptoFixo.GetZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}

	public void exibeMatriz() {
		matrizObjeto.exibeMatriz();
	}

	public void exibeVertices() {
		System.out.println("P0[" + this.verticesMatriz[0].GetX() + "," + this.verticesMatriz[0].GetY() + "," + this.verticesMatriz[0].GetZ() + "," + this.verticesMatriz[0].GetW() + "]");
		System.out.println("P1[" + this.verticesMatriz[1].GetX() + "," + this.verticesMatriz[1].GetY() + "," + this.verticesMatriz[1].GetZ() + "," + this.verticesMatriz[1].GetW() + "]");
		System.out.println("P2[" + this.verticesMatriz[2].GetX() + "," + this.verticesMatriz[2].GetY() + "," + this.verticesMatriz[2].GetZ() + "," + this.verticesMatriz[2].GetW() + "]");
		System.out.println("P3[" + this.verticesMatriz[3].GetX() + "," + this.verticesMatriz[3].GetY() + "," + this.verticesMatriz[3].GetZ() + "," + this.verticesMatriz[3].GetW() + "]");
//		System.out.println("anguloGlobal:" + anguloGlobal);
	}	
	public void desenha() {		
		gl.glColor3f(this.cor[0], this.cor[1], this.cor[2]);
		gl.glLineWidth(3.0f);
		gl.glPointSize(3.0f);
		
		gl.glBegin(this.primitiva);
			for (Point4D vertice : this.vertices) {
				gl.glVertex2d(vertice.GetX(), vertice.GetY());
			}
		gl.glEnd();
	}
}
