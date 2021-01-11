package idv.david.websocketchat.model;

import java.util.List;

public class ChatPartner {
	private String type;
	private List<String> chatPartners;
	
	public ChatPartner(String type, List<String> chatPartners) {
		this.type = type;
		this.chatPartners = chatPartners;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getChatPartners() {
		return chatPartners;
	}

	public void setChatPartners(List<String> chatPartners) {
		this.chatPartners = chatPartners;
	}

	
}
