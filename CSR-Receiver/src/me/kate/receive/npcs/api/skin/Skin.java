package me.kate.receive.npcs.api.skin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Skin {

	private String value, signature, name;
	
	
	public Skin(String name) {
		this.name = name;
	}
	
	public CompletableFuture<Skin> future() throws IllegalStateException {
		return future;
	}
	
	private CompletableFuture<Skin> future = CompletableFuture.supplyAsync(() -> {
	    try {
	    	URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
			String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

			URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
			JsonObject textureProperty = new JsonParser()
					.parse(reader_1)
					.getAsJsonObject()
					.get("properties")
					.getAsJsonArray().get(0).getAsJsonObject();
			String texture = textureProperty.get("value").getAsString();
			String signature = textureProperty.get("signature").getAsString();
			this.value = texture;
			this.signature = signature;
	    } catch (IOException e) {
	        throw new IllegalStateException(e);
	    }
	    return new Skin(value, signature);
	});

	public Skin(String value, String signature) {
		this.value = value;
		this.signature = signature;
	}

//	public Skin getSkin() {
//		return new Skin(value, signature);
//	}
	
	public String getValue() {
		return this.value;
	}

	public String getSignature() {
		return this.signature;
	}
	
	@Override
	public String toString() {
		return "Value: " 
				+ this.value + 
				" Signature: " 
				+ this.signature;
	}
	
}
