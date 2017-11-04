package QRMessage;

import javafx.scene.paint.Color;

public class Event {
	
	public String message;
	public Color color;
	private String data;
	
	public Event(String s, Color c) {
		message = s;
		color = c;
	}
	
	public void setData(String d) {data=d;}
	public String getData() {return data;}

}
