package db;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

public class Style {
	public static Color good = new Color(2, 119, 189);
	public static Color normally = new Color(0, 131, 143);
	public static Color bad = new Color(239, 108, 0);
	public static Color very_bad = new Color(216, 67, 21);
	public static Color main_color = new Color(69, 90, 247);
	
	public static Color[] temp_colors = {
		new Color(220, 219, 224),
		new Color(239, 212, 51),
		new Color(255, 168, 38),
		new Color(216, 98, 2),
		new Color(235, 46, 5)
	};
	public static Color[] humid_colors = {
		new Color(188, 235, 255),
		new Color(118, 194, 220),
		new Color(62, 112, 204),
		new Color(37, 76, 141),
		new Color(19, 50, 97)
	};
	public static Color[] cateory_colors = {
		new Color(234, 67, 53),
		new Color(251, 188, 5),
		new Color(61, 172, 91),
		new Color(66, 133, 244)
	};
	public static List<Color> schedule = Arrays.asList(
			new Color(230, 230, 230),
			new Color(150, 206, 239),
			new Color(150, 238, 222),
			new Color(235, 146, 199),
			new Color(235, 166, 125),
			new Color(147, 139, 222),
			new Color(181, 119, 144),
			new Color(225, 90, 104),
			new Color(69, 186, 253),
			new Color(203, 205, 98),
			new Color(147, 199, 0)
	);
	
	public static Color getCategoryColor(int index) {
		return cateory_colors[index];
	}
	
	public static Color getHumidColor(int index) {
		return humid_colors[index];
	}

	public static Color getTempColor(int index) {
		return temp_colors[index];
	}
	
	public static Color getSchedulColor(int index) {
		return schedule.get(index % schedule.size());
	}

	public static Font getFont(int size) {
		return new Font("나눔바른고딕", Font.PLAIN, size);
	}
}
