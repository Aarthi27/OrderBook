package com.demo.controllers;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.APIInfo;
import com.demo.MyClient;
import com.demo.util.MyMessageHandler;

@Controller

public class OrderBookController {

	@Autowired
	private MyClient myClient;

	@RequestMapping(value = "/viewOrderBook")
	public String getQuote(Model model) {
//		System.out.println("Inside Controller!! " + model);
		MyMessageHandler handler = (MyMessageHandler) myClient.getMessageHandler();
		try {

			
			model.addAttribute("buy", handler.getBuyMap());
			model.addAttribute("sell", handler.getSellMap());
			System.out.println("-----" + handler.getBuyMap());
			System.out.println("====" + handler.getSellMap().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "order";

	}

}
