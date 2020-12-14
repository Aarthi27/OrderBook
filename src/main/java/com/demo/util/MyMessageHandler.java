package com.demo.util;

import java.math.BigDecimal;
import java.util.Map;

import javax.websocket.MessageHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyMessageHandler implements MessageHandler.Whole<String> {
	private static final String BUY = "buy";
	private static final String SELL = "sell";
	Map<BigDecimal, BigDecimal> buyMap;
	Map<BigDecimal, BigDecimal> sellMap;

	MyMessageHandler(Map<BigDecimal, BigDecimal> buyMap, Map<BigDecimal, BigDecimal> sellMap) {
		this.buyMap = buyMap;
		this.sellMap = sellMap;
	}

	@Override
	public void onMessage(String message) {
		try {
			JSONObject object = new JSONObject(message);
			for (Object item : ((JSONArray) object.get("data")).toList()) {
//				System.out.println(item);
				Map map = (Map) item;
				if (BUY.equals(map.get("side").toString())) {
					updateMap(buyMap, new BigDecimal(map.get("price").toString()),
							new BigDecimal(map.get("size").toString()));
				} 
				if (SELL.equals(map.get("side").toString())) {
					updateMap(sellMap, new BigDecimal(map.get("price").toString()),
							new BigDecimal(map.get("size").toString()));
				}

			}
		} catch (Exception e) {
		}
	}

	private void updateMap(Map<BigDecimal, BigDecimal> map, BigDecimal price, BigDecimal qty) {
		if (BigDecimal.ZERO.equals(qty)) {
			map.remove(price);
			System.out.println("removed price "+price);
			return;
		}
		BigDecimal oldQty = map.get(price);
		if (oldQty != null) {
			map.put(price, oldQty.add(qty));
			System.out.println("updated price "+price);
		}
		else if (map.size() < 50) {
			map.put(price, qty);
			System.out.println("Added price "+price);
		}
		System.out.println("Mapp---" + map);
	}
	
	public Map<BigDecimal, BigDecimal> getBuyMap(){
		return buyMap;
	}
	public Map<BigDecimal, BigDecimal> getSellMap(){
		return sellMap;
	}
}
