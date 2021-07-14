package db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Dust {
	public String getDustState(String date) {
		try {
			URL url = new URL(getDustUrl(date));
			InputStreamReader input = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
			String dust = (String) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) JSONValue
					.parse(input)).get("response")).get("body")).get("items")).get(0)).get("informGrade");
			
			for(String data:dust.split(",")) {
				if(data.split(" : ")[0].equals("대구")) {
					return data.split(" : ")[1];
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

	public static void main(String[] args) {
		new Dust().getDustState("asdf");
	}

	private String getDustUrl(String date) {
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth";

		url += "?serviceKey=G1ZPRotImv3j%2Fc2pKpbUQOxvdvRSdbiKgBh8TBASCjf4AmfjveOkPlleiYCOleDHAYkM7NrdA5CvdqBlnSaIxA%3D%3D";
		url += "&returnType=json";
		url += "&numOfRows=100";
		url += "&pageNo=1";
		url += "&searchDate=" + date;
		url += "&InformCode=PM10";

		return url;
	}
}
