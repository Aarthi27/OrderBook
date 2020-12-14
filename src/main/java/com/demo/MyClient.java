package com.demo;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

@ClientEndpoint(configurator = MyClient.AuthorizationConfigurator.class)
public class MyClient {

	Session userSession = null;

	Map<BigDecimal, BigDecimal> buyMap = new TreeMap<>();
	Map<BigDecimal, BigDecimal> sellMap = new TreeMap<>();

	private static final String BUY = "buy";
	private static final String SELL = "sell";

	/*
	 * private static MyClient instance;
	 * 
	 * static { try { System.out.println("sttaitc iniitlalu"); instance = new
	 * MyClient(new URI(APIInfo.getInstance().getWebsocketURL())); } catch
	 * (URISyntaxException e) { e.printStackTrace(); } }
	 * 
	 * public static MyClient getInstance() { return instance; }
	 */
	public MyClient() {

	}

	public MyClient(String url, MessageHandler msgHandler) {
		try {
			if (userSession == null) {
				WebSocketContainer container = ContainerProvider.getWebSocketContainer();
				userSession = container.connectToServer(MyClient.class, new URI(url));
				userSession.addMessageHandler(msgHandler);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("opening websocket--");
		this.userSession = session;

		try {
			userSession.getBasicRemote().sendText(
					"{\"type\":\"subscribe\",\"chan_name\":\"orderbook\",\"subchan_name\":\"" + "btc-eur" + "\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * CountDownLatch latch = new CountDownLatch(1); try { latch.await(); } catch
		 * (InterruptedException e) { e.printStackTrace(); }
		 */
	

	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		System.out.println("closing websocket : " + reason.getReasonPhrase() + " " + reason.getCloseCode());
		this.userSession = null;
	}

	@OnError
	public void onError(Session session, Throwable t) {
		t.printStackTrace();
	}

	public static class AuthorizationConfigurator extends ClientEndpointConfig.Configurator {
		public void beforeRequest(Map<String, List<String>> headers) {

			System.out.println("Inside before Request");
			APIInfo apiInfo = APIInfo.getInstance();

			headers.put("Authorization", asList(apiInfo.getAuthHeaderInfo()));
			headers.put("Date", asList(apiInfo.getDate()));
			headers.put("ApiKey", asList(apiInfo.getApiKey()));
		}

		private List<String> asList(String content) {
			List<String> list = new ArrayList<>();
			list.add(content);
			return list;
		}
	}

	public MessageHandler getMessageHandler() {
		MessageHandler[] handlers = userSession.getMessageHandlers().toArray(new MessageHandler[0]);
		return handlers[0];
	}

	
}
