package db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Weather {
	
	public String getWeather(String date) {
		try {
			URL url = new URL(getWeatherURL(date));
			InputStreamReader input = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
			
			JSONObject object = (JSONObject) JSONValue.parse(input);
			
			JSONArray items = (JSONArray) ((JSONObject) ((JSONObject)((JSONObject) object.get("response")).get("body")).get("items")).get("item");
			
			for(int i = 0; i < items.size(); i++) {
				JSONObject obj = (JSONObject) items.get(i);
				String category = (String) obj.get("category");
				if(category.equals("PTY")) {
					switch ((String) obj.get("fcstValue")) {
					case "1":
						return "비";
					case "2":
						return "비";
					case "3": 
						return "눈";
					case "4": 
						return "비";
					}
				} else if(category.equals("SKY")) {
					switch ((String) obj.get("fcstValue")) {
					case "1":
						return "맑음";
					case "3":
						return "구름";
					case "4": 
						return "흐림";
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String getWeatherURL(String date) {
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"; /*URL*/
        url += "?serviceKey=G1ZPRotImv3j%2Fc2pKpbUQOxvdvRSdbiKgBh8TBASCjf4AmfjveOkPlleiYCOleDHAYkM7NrdA5CvdqBlnSaIxA%3D%3D"; /*Service Key*/
        url += "&pageNo=1";
        url += "&numOfRows=10";
        url += "&dataType=JSON";
        url += "&base_date=" + date;
        url += "&base_time=0500";
        url += "&nx=1";
        url += "&ny=1";
        return url;
	}
}
