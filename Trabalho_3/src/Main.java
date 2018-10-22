
/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.NoSuchElementException;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import Classes.Mundo;
import Classes.ObjGrafico;
import Classes.Point4D;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Mundo mundo = new Mundo();
	private boolean inicioPol = true;
	private boolean criaVert = false;
	private float valorX = 200.0f, valorY = -200.0f;
	private int antigoX, antigoY = 0;
	private float d = 0;

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		this.clearColor();
	}

	public void clearColor() {
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(0.0f, 400.0f, 400.0f, 0.0f);

		// SRU();
		
		d = (((valorX-200)*(valorX-200)) + ((valorY+200)*(valorY+200)));
		
		// seu desenho ...
		for (ObjGrafico obj : this.mundo.lisObjGrafico) {
			obj.desenha();
		}

		gl.glFlush();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
		System.out.println(width);
		System.out.println(height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	public void SRU() {
		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(-200.0f, 0.0f);
		gl.glVertex2f(200.0f, 0.0f);
		gl.glEnd();

		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, -200.0f);
		gl.glVertex2f(0.0f, 200.0f);
		gl.glEnd();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_1:
			this.criaVert = true;
			break;
		case KeyEvent.VK_ENTER:
			this.inicioPol = true;
			break;

		case KeyEvent.VK_DELETE:
			if (this.mundo.poligonoSelecionado != null
					&& this.mundo.lisObjGrafico.remove(this.mundo.poligonoSelecionado)) {
				try {
					this.mundo.poligonoSelecionado = this.mundo.lisObjGrafico.getLast();
				} catch(NoSuchElementException el) {
					this.mundo.poligonoSelecionado = null;
				}
				inicioPol = true;
				criaVert = true;
				glDrawable.display();
			}
			break;
		
		case KeyEvent.VK_ESCAPE:
			this.criaVert = false;
			break;
		}
	}

	public void mousePressed(MouseEvent arg0) {
		if (inicioPol) {
			this.mundo.lisObjGrafico.add(new ObjGrafico(this.gl));
			this.mundo.poligonoSelecionado = this.mundo.lisObjGrafico.getLast();
			inicioPol = false;
		}
		
		if (criaVert) {
			this.mundo.poligonoSelecionado.vertices.add(new Point4D(arg0.getX(), arg0.getY(), 0.0, 1.0));
			glDrawable.display();
		}

		if (d <= 20000) {
			antigoX = arg0.getX();
	        antigoY = arg0.getY();
		} 
		

	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub
	}

	public void mouseDragged(MouseEvent arg0) {
		if (this.mundo.poligonoSelecionado != null) {
			Point4D pontoSelecionado = null;
			
			for (Point4D ponto : this.mundo.poligonoSelecionado.vertices) {
				if ((int)ponto.GetX() == arg0.getX() && (int)ponto.GetY() == arg0.getY())	{
					pontoSelecionado = ponto;
					break;
				}
			}
			
			if (pontoSelecionado == null) {
				pontoSelecionado = this.mundo.poligonoSelecionado.vertices.getLast();
			}
			if (d <= 20000) {
				int movtoX = arg0.getX() - this.antigoX;
			    int movtoY = arg0.getY() - this.antigoY;
			    pontoSelecionado.SetX(pontoSelecionado.GetX() + movtoX) ;
			    pontoSelecionado.SetY(pontoSelecionado.GetY() + movtoY);
			    
			    antigoX = arg0.getX();
				antigoY = arg0.getY();

				glDrawable.display();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent arg0) {
		if (d > 20000) {
			antigoX = 200;
			antigoY = -200;
			valorX = 200;
			valorY = -200;
			glDrawable.display();
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

}
