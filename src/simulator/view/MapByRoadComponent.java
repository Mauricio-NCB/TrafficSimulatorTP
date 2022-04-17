package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private static final int _JRADIUS = 10;
	private static final int _GENERIC_SIZE = 32;

	private static final Color _ROAD_COLOR = Color.BLACK;
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;

	private Image _car;
	
	public MapByRoadComponent(Controller ctrl) {

		initGUI();
		setPreferredSize(new Dimension(300, 200));
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		// TODO Auto-generated method stub
		_car = loadImage("car.png");
	}

	private Image loadImage(String img) {
		// TODO Auto-generated method stub
		Image i = null;
		
        try {
            return ImageIO.read(new File("resources/icons/" + img));
        } catch (IOException e) {
        }
        
        return i;
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		// TODO Auto-generated method stub
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(_RED_LIGHT_COLOR);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMap(g);
		}
		
	}

	private void drawMap(Graphics2D graphics) {
		// TODO Auto-generated method stub
		
		int i = 0;
		int x1 = 50;
		int x2 = getWidth() - 100;
		int y = 0;
		
		for (Road r: _map.getRoads()) {
			
			y = (i + 1) * 50;
			
			drawRoads(graphics, r, x1, x2, y);
			
			drawJunctions(graphics, r, x1, x2, y);
			
			drawVehicles(graphics, r, x1, x2, y);
			
			drawWeather(graphics, r, x1, x2, y);
			
			drawCont(graphics, r, x1, x2, y);
			
			i++;
		}
	}

	private void drawRoads(Graphics2D graphics, Road r, int x1, int x2, int y) {
		// TODO Auto-generated method stub
		graphics.setColor(_ROAD_COLOR);
		graphics.drawString(r.getId(), x1 - 30, y + _JRADIUS / 4);
		graphics.drawLine(x1, y, x2, y);
	}

	private void drawJunctions(Graphics2D graphics, Road r, int x1, int x2, int y) {
		// TODO Auto-generated method stub
		graphics.setColor(_JUNCTION_COLOR);
		graphics.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

		graphics.setColor(_JUNCTION_LABEL_COLOR);
		graphics.drawString(r.getSrc().getId(), x1 - _JRADIUS / 2, y - _JRADIUS);

        if (r.getDest().getGreenLightIndex() != -1
                && r.equals(r.getDest().getInRoads().get(r.getDest().getGreenLightIndex())) ) {
        	graphics.setColor(_GREEN_LIGHT_COLOR);
        }
        else {
        	graphics.setColor(_RED_LIGHT_COLOR);
        }
        graphics.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

        graphics.setColor(_JUNCTION_LABEL_COLOR);
        graphics.drawString(r.getDest().getId(), x2 - _JRADIUS / 2, y - _JRADIUS);
	}

	private void drawVehicles(Graphics2D graphics, Road r, int x1, int x2, int y) {
		// TODO Auto-generated method stub
		for (Vehicle v : r.getVehicles()) {
            if (v.getStatus() != VehicleStatus.ARRIVED) {
                int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength()));

                graphics.setColor(_GREEN_LIGHT_COLOR);
                graphics.drawImage(_car, x - _JRADIUS / 2 , y - _JRADIUS, 16, 16, this);


                int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
                graphics.setColor(new Color(0, vLabelColor, 0));
                graphics.drawString(v.getId(), x - 4, y - _JRADIUS);
            }
        }
	}

	private void drawWeather(Graphics2D graphics, Road r, int x1, int x2, int y) {
		// TODO Auto-generated method stub
		Image weatherImg;
        switch (r.getWeather()) {
            case STORM:
                weatherImg = loadImage("storm.png");
                break;
            case SUNNY:
                weatherImg = loadImage("sun.png");
                break;
            case RAINY:
                weatherImg = loadImage("rain.png");
                break;
            case WINDY:
                weatherImg = loadImage("wind.png");
                break;
            case CLOUDY:
                weatherImg = loadImage("cloud.png");
                break;
            default:
                weatherImg = null;
        }
        graphics.setColor(_BG_COLOR);
        graphics.drawImage(weatherImg, x2 + 11, y - 15, _GENERIC_SIZE, _GENERIC_SIZE, this);
	}

	private void drawCont(Graphics2D graphics, Road r, int x1, int x2, int y) {
		// TODO Auto-generated method stub
		int c = (int) Math.floor(Math.min((double)r.getTotalCO2() / (1.0 + (double)r.getContLimit() ), 1.0) / 0.19);
        Image co2Img;
        
        switch (c) {
            case 0:
                co2Img = loadImage("cont_0.png");
                break;
            case 1:
                co2Img = loadImage("cont_1.png");
                break;
            case 2:
                co2Img = loadImage("cont_2.png");
                break;
            case 3:
                co2Img = loadImage("cont_3.png");
                break;
            case 4:
                co2Img = loadImage("cont_4.png");
                break;
            case 5:
                co2Img = loadImage("cont_5.png");
                break;
            default:
                co2Img = null;
        }
        graphics.setColor(_BG_COLOR);
        graphics.drawImage(co2Img, x2 + 50, y - 15, _GENERIC_SIZE, _GENERIC_SIZE, this);
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
