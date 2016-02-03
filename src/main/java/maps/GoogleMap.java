package maps;

import path.Path;
import path.PathPoint;
import utils.GoogleMapsProjection;
import utils.LatLong;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mohannad on 02/01/16.
 */
public class GoogleMap extends MapIF {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PathPoint pos;
	Point2D center = null;
	Path path = null;
	boolean pathDrawn = false;
	int opIndex;
	private BufferedImage bi;
	int w, h;
	LatLong converter;

	public GoogleMap(Path p) throws MalformedURLException {
		this.path = p;
		double ccx = (p.getFirst().getX() + p.getLast().getX()) / 2.0;
		double ccy = (p.getFirst().getY() + p.getLast().getY()) / 2.0;
		PathPoint centerPoint = new PathPoint(ccx, ccy, 0);
		double cx = GoogleMapsProjection.deg2mlng(centerPoint.getX());
		double cy = GoogleMapsProjection.deg2mlat(centerPoint.getY());
		int zoom = 6;
		PathPoint pxl = GoogleMapsProjection.M2Pxl(cx, cy, zoom);
		double tx = pxl.getX() - 150; // west
		double ty = pxl.getY() + 150; // north
		double bx = pxl.getX() + 150; // east
		double by = pxl.getY() - 150; // south

		PathPoint mtp = GoogleMapsProjection.M2Pxlinv(tx, ty, zoom);
		PathPoint mbp = GoogleMapsProjection.M2Pxlinv(bx, by, zoom);
		PathPoint tp = GoogleMapsProjection.M2latlng(mtp.getX(), mtp.getY());
		PathPoint bp = GoogleMapsProjection.M2latlng(mbp.getX(), mbp.getY());
		converter = new LatLong(300, 300, tp.getX(), tp.getY(), bp.getX(), bp.getY());
		center = converter.convertLatLongToCoord(centerPoint);

		String center = ccy + "," + ccx;
		String key = "AIzaSyDRRinfRhWRbWSTPAw_3MA6K2LpTziucXQ";
		String mapurl = "http://maps.googleapis.com/maps/api/staticmap?center=" + center
				+ "&size=300x300&zoom=6&sensor=false&key=" + key;
		URL imageSrc = new URL(mapurl);
		try {
			bi = ImageIO.read(imageSrc);
			w = bi.getWidth(null);
			h = bi.getHeight(null);
			if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
				BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics big = bi2.getGraphics();
				big.drawImage(bi, 0, 0, null);
				bi = bi2;
			}
		} catch (IOException e) {
			System.out.println("Image could not be read");
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	void setOpIndex(int i) {
		opIndex = i;
	}

	public void drawRec(PathPoint pos) {
		this.pos = pos;
		repaint();
	}

	public void drawPath() {
		this.pathDrawn = true;
		repaint();
		// this.pathDrawn = false;

	}

	public void paint(Graphics g) {
		g.drawImage(bi, 0, 0, null);
		g.setColor(Color.GRAY);
		double d = 0;
		if (this.pathDrawn) {
			ArrayList<PathPoint> points = this.path.getPathPoints();

			for (PathPoint pp : points) {
				Point2D p = converter.convertLatLongToCoord(pp);
				d = p.getY() - center.getY();
				g.fillOval((int) p.getX() - 2, (int) (p.getY() - 2 * d + 4), 4, 4);
			}
		}
		g.setColor(Color.blue);
		if (this.pos != null) {
			Point2D pos = converter.convertLatLongToCoord(this.pos);
			d = pos.getY() - center.getY();
			g.fillOval((int) pos.getX() - 2, (int) (pos.getY() - 2 * d + 4), 10, 10);
		}
	}

	@Override
	public boolean isPassable(double x, double y, double z) {
		return true;
	}

	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return h;
	}

	@Override
	public void paintMap(Path p) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		this.drawPath();

	}

	@Override
	public void setPossition(PathPoint possition) {
		this.drawRec(possition);
	}
}
