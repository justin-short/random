import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

@SuppressWarnings("serial")
public class PythagoreanTriplets extends JPanel implements MouseWheelListener {
	public static void main(String[] args) {
		final PythagoreanTriplets pt = new PythagoreanTriplets();
		final JFrame f = new JFrame();
		f.getContentPane().add(pt);
		f.setVisible(true);
		f.setSize(800, 700);
		f.toFront();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.addMouseWheelListener(pt);
	}
	
	private double zoomLevel = 60;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom(e.getPreciseWheelRotation());
 	}

	private void zoom(double amount) {
		zoomLevel = Math.max(10, zoomLevel * (1 + amount / 100));
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		java.awt.Rectangle rect = g.getClipBounds();
		g.setColor(Color.BLACK);
		g.fillRect((int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY());

		final int w = getWidth();
		final int h = getHeight();
		final int n = (int)zoomLevel;
		final double scale = 3.0 * (double)Math.max(w,h) / (n * n); 
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int pass = 0; pass < 1; ++pass) { // Can set to 2 but not much different TBH
			g.setColor(pass == 0 ? new Color(0,1,1,0.8f) : Color.WHITE);
			for (long j = 1; j < n; ++j) {
				for (long i = 1; i < j; ++i) {
					double dx = (2*j*i) * scale;
					double dy = (j*j-i*i) * scale;
					double dr = Math.sqrt(Math.max((j*j+i*i) * scale / (n/5), 1));
					if (pass > 0) dr = 1;

					g.fill(new Ellipse2D.Double(dx - dr/2, h - 1 - dy - dr/2, dr, dr));
					g.fill(new Ellipse2D.Double(dy - dr/2, h - 1 - dx - dr/2, dr, dr));
				}
			}
		}
	}
}
