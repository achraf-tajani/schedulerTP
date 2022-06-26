package fr.bnf.batchTp2.websocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {
	
	private final SimpMessagingTemplate msgTmp;
	
	@Autowired
	public WebsocketService(final SimpMessagingTemplate msgTmp) {
		this.msgTmp = msgTmp;
	}
	
	public void sendMessage(final String topicSuffix) {
		msgTmp.convertAndSend("/topic/" + topicSuffix,"Default message from our Ws service");
	}
}
