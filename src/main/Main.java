package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.Connect;
import db.Dust;
import db.Style;
import db.Weather;
import gui.BaseJFrame;
import gui.BaseJPanel;

public class Main extends BaseJFrame {
	private static final long serialVersionUID = 1L;
	InformationCard[] cards = new InformationCard[4];
	CategoryCard[] category = new CategoryCard[4];

	public Main() {
		setFrame(1200, 800);
		setBackground(new Color(250, 250, 250));

		JPanel panel = new BaseJPanel(new BorderLayout(), this);

		panel.add(getNorthPanel(), BorderLayout.NORTH);
		panel.add(getCenterPanel(), BorderLayout.CENTER);
		panel.add(getSouthPanel(), BorderLayout.SOUTH);

		add(panel);
		panel.setBackground(getBackground());
		
		setVisible(true);
	}

	private JPanel getNorthPanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(FlowLayout.RIGHT), this);

		JLabel label = new JLabel(getImage("close", 20, 20));
		panel.add(label);

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Connect.stop = true;
				System.exit(0);
			}
		});

		panel.setBackground(getBackground());
		return panel;
	}

	private JPanel getCenterPanel() {
		JPanel panel = new BaseJPanel(new BorderLayout(), this);
		
		panel.add(new Calendar(this), BorderLayout.CENTER);
		panel.add(getCategoryPanel(), BorderLayout.WEST);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		panel.setBackground(getBackground());
		return panel;
	}
	
	private JPanel getCategoryPanel() {
		JPanel panel = new BaseJPanel(new GridLayout(2, 2), this);
		
		String[] split = "메모,오늘의 약,현재 자세,오늘 마신 물".split(",");
		for(int i = 0; i < 4; i++) {
			category[i] = new CategoryCard(this, split[i], "\\category\\" + split[i], (i == 0 ? null : (i == 3 ? "0잔" : "데이터가 없습니다.")), Style.getCategoryColor(i));
			panel.add(category[i]);
		}
		
		category[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new note.Main();
			}
		});
		category[3].setName("0");
		category[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setWaterPanel();
			}
		});
		panel.setPreferredSize(new Dimension(600, 450));
		
		JPanel flow = new BaseJPanel(new FlowLayout(), this);
		flow.add(panel);
		flow.setPreferredSize(new Dimension(600, 450));
		flow.setBackground(getBackground());
		flow.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
		
		return flow;
	}
	
	private void setPillPanel(String data) {
		category[1].setSubPanel(data);
	}
	
	private void setWaterPanel() {
		category[3].setName(Integer.toString((Integer.parseInt(category[3].getName()) + 1)));
		category[3].setSubPanel(category[3].getName() + "잔");
	}
	
	public void setChairPanel(String data) {
		if(Integer.parseInt(data) >= 0 && Integer.parseInt(data) <= 10) {
			data = "좋은 자세입니다.";
		} else if(Integer.parseInt(data) >= 11 && Integer.parseInt(data) <= 20) {
			data = "자세를 고쳐 앉는게 좋겠어요.";
		} else {
			data = "의자에 앉아 있지 않네요.";
		}
		
		category[2].setSubPanel(data);
	}

	private JPanel getSouthPanel() {
		JPanel panel = new BaseJPanel(new GridLayout(1, 0), this);

		String[] split = "날씨,미세먼지,온도,습도".split(",");
		for (int i = 0; i < 4; i++) {
			panel.add((cards[i] = new InformationCard(this, split[i])));
			if ("날씨".equals(split[i]))
				setWeatherCard();
			else if ("미세먼지".equals(split[i]))
				setDustCard();
		}
		return panel;
	}

	public void setHumidCard(String data) {
		cards[3].setInformationCard("\\humid\\물 " + (Integer.parseInt(data) / 10 + 1), 100, 120, data + "%",
				(Integer.parseInt(data) >= 100 ? Style.getHumidColor(4)
						: Style.getHumidColor(Integer.parseInt(data) / 20)));
	}

	public void setTempCard(String data) {
		cards[2].setInformationCard("\\temp\\" + Integer.toString(Integer.parseInt(data) / 4), 30, 120, data + "°C",
				(Integer.parseInt(data) >= 35 ? Style.getTempColor(4)
						: Style.getTempColor(Integer.parseInt(data) / 7)));
	}

	public void setDustCard() {
		String dust = new Dust().getDustState(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

		Color bg = null;
		if (dust.equals("좋음"))
			bg = Style.good;
		else if (dust.equals("보통"))
			bg = Style.normally;
		else if (dust.equals("나쁨"))
			bg = Style.bad;
		else if (dust.equals("매우나쁨"))
			bg = Style.very_bad;

		cards[1].setInformationCard("\\dust\\" + dust, 120, 120, dust, bg);
	}

	public void setWeatherCard() {
		String weather = new Weather().getWeather(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		cards[0].setInformationCard("\\weather\\" + weather, 120, 120, weather, new Color(37, 97, 160));
	}

	public static void main(String[] args) {
		new Main();
	}
}
