package Classes;


import java.lang.Math;

// Organizacao dos elementos internos da Matriz
//             [ matrix[0] matrix[4] matrix[8]  matrix[12] ]
// Transform = [ matrix[1] matrix[5] matrix[9]  matrix[13] ]
//             [ matrix[2] matrix[6] matrix[10] matrix[14] ]
//             [ matrix[3] matrix[7] matrix[11] matrix[15] ]

public final class Transform4d {
	static final double DEG_TO_RAD = 0.017453292519943295769236907684886;

	/// Cria uma matriz de Trasnformacao com uma matriz Identidade.
	private double[] matriz = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };

	public Transform4d() {
	}

	/// Atribui os valores de uma matriz Identidade a matriz de Transformacao.
	public void atribuirIdentidade() {
		for (int i = 0; i < 16; ++i) {
			this.matriz[i] = 0.0;
		}
		this.matriz[0] = this.matriz[5] = this.matriz[10] = this.matriz[15] = 1.0;
	}

	/// Atribui os valores de Translacao (tx,ty,tz) a matriz de Transformacao.
	/// Elemento Neutro eh 0 (zero).
	public void atribuirTranslacao(double tx, double ty, double tz) {
		atribuirIdentidade();
		this.matriz[12] = tx;
		this.matriz[13] = ty;
		this.matriz[14] = tz;
	}

	/// Atribui o valor de Escala (Ex,Ey,Ez) a matriz de Transformacao.
	/// Elemento Neutro eh 1 (um).
	/// Se manter os valores iguais de Ex,Ey e Ez o objeto vai ser reduzido ou
	/// ampliado proporcionalmente.
	public void atribuirEscala(double sX, double sY, double sZ) {
		atribuirIdentidade();

		this.matriz[0] = sX;
		this.matriz[5] = sY;
		this.matriz[10] = sZ;
	}

	/// Atribui o valor de Rotacao (angulo) no eixo X a matriz de Transformacao.
	/// Elemento Neutro eh 0 (zero).
	public void atribuirRotacaoX(double radians) {
		atribuirIdentidade();

		this.matriz[5] = Math.cos(radians);
		this.matriz[9] = -Math.sin(radians);
		this.matriz[6] = Math.sin(radians);
		this.matriz[10] = Math.cos(radians);
	}

	/// Atribui o valor de Rotacao (angulo) no eixo Y a matriz de Transformacao.
	/// Elemento Neutro eh 0 (zero).
	public void atribuirRotacaoY(double radians) {
		atribuirIdentidade();

		this.matriz[0] = Math.cos(radians);
		this.matriz[8] = Math.sin(radians);
		this.matriz[2] = -Math.sin(radians);
		this.matriz[10] = Math.cos(radians);
	}

	/// Atribui o valor de Rotacao (angulo) no eixo Z a matriz de Transformacao.
	/// Elemento Neutro eh 0 (zero).
	public void atribuirRotacaoZ(double radians) {
		atribuirIdentidade();

		this.matriz[0] = Math.cos(radians);
		this.matriz[4] = -Math.sin(radians);
		this.matriz[1] = Math.sin(radians);
		this.matriz[5] = Math.cos(radians);
	}

	public Point4D transformPoint(Point4D point) {
		Point4D pointResult = new Point4D(
				this.matriz[0] * point.GetX() + this.matriz[4] * point.GetY() + this.matriz[8] * point.GetZ()
						+ this.matriz[12] * point.GetW(),
				this.matriz[1] * point.GetX() + this.matriz[5] * point.GetY() + this.matriz[9] * point.GetZ()
						+ matriz[13] * point.GetW(),
				this.matriz[2] * point.GetX() + this.matriz[6] * point.GetY() + this.matriz[10] * point.GetZ()
						+ matriz[14] * point.GetW(),
				this.matriz[3] * point.GetX() + this.matriz[7] * point.GetY() + this.matriz[11] * point.GetZ()
						+ this.matriz[15] * point.GetW());
		return pointResult;
	}

	public Transform4d transformMatrix(Transform4d t) {
		Transform4d result = new Transform4d();

		for (int i = 0; i < 16; ++i) {
			result.matriz[i] = matriz[i % 4] * t.matriz[i / 4 * 4] + matriz[(i % 4) + 4] * t.matriz[i / 4 * 4 + 1]
					+ matriz[(i % 4) + 8] * t.matriz[i / 4 * 4 + 2] + matriz[(i % 4) + 12] * t.matriz[i / 4 * 4 + 3];
		}
		
		return result;
	}

	public double GetElement(int index) {
		return this.matriz[index];
	}

	public void SetElement(int index, double value) {
		this.matriz[index] = value;
	}

	public double[] GetDate() {
		return this.matriz;
	}

	public void SetData(double[] data) {
		int i;

		for (i = 0; i < 16; i++) {
			this.matriz[i] = (data[i]);
		}
	}

	public void exibeMatriz() {
		System.out.println("______________________");
		System.out
				.println("|" + GetElement(0) + " | " + GetElement(4) + " | " + GetElement(8) + " | " + GetElement(12));
		System.out
				.println("|" + GetElement(1) + " | " + GetElement(5) + " | " + GetElement(9) + " | " + GetElement(13));
		System.out
				.println("|" + GetElement(2) + " | " + GetElement(6) + " | " + GetElement(10) + " | " + GetElement(14));
		System.out
				.println("|" + GetElement(3) + " | " + GetElement(7) + " | " + GetElement(11) + " | " + GetElement(15));
	}

	public double RetornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	public double RetornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}
}
