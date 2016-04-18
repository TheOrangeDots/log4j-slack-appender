package com.tod.utils.logging;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class SlackAppender extends AppenderSkeleton {

	private String url;
	private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	private String userName = null;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
	
	private String channel;
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	private String icon_emoji;
	public String getEmoji() {
		return icon_emoji;
	}

	public void setEmoji(String emoji) {
		this.icon_emoji = emoji;
	}
	
	public SlackAppender() {
	}

	public SlackAppender(boolean isActive) {
		super(isActive);
	}

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		try {
			LogLog.debug("Sending 'POST' request to URL: " + url);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/json");
	
			ScriptObjectMirror payload = getJSObject();
			
			if (this.userName != null) {
				payload.put("userName", escapeString(this.userName));
			}

			if (this.channel != null) {
				payload.put("channel", escapeString(this.channel));
			}

			if (this.icon_emoji != null) {
				payload.put("icon_emoji", escapeString(this.icon_emoji));
			}
			
			ScriptObjectMirror attachments = getJSArray();
			payload.put("attachments", attachments);
			
			ScriptObjectMirror attachment = getJSObject();
			attachments.put("0", attachment);
			
			//TODO make colors configurable
			Color color;
			switch (event.getLevel().toInt()) {
				case Level.FATAL_INT:
				case Level.ERROR_INT:
					color = Color.RED;
					break;
				case Level.WARN_INT:
					color = Color.ORANGE;
					break;
				case Level.INFO_INT:
					color = Color.BLACK;					
					break;
				case Level.DEBUG_INT:
					color = Color.BLUE;
					break;
				case Level.TRACE_INT:
					color = Color.GREEN;
					break;
				default:
					color = Color.GRAY;
					break;
			}
			
			attachment.put("color", "#" + Integer.toHexString(color.getRGB()).substring(2));
			attachment.put("pretext", escapeString(event.getMessage().toString()));
			attachment.put("title", escapeString(event.getLevel().toString()));
			
			String[] stack = event.getThrowableStrRep();
			if (stack != null && stack.length > 0) {
				attachment.put("text", escapeString(String.join("\n", stack)));
			}
			
			StringBuffer fallbackText = new StringBuffer();
			fallbackText.append(attachment.get("title"));
			fallbackText.append(attachment.get(" "));
			fallbackText.append(attachment.get("pretext"));
			if (attachment.containsKey("text")) {
				fallbackText.append("\n");
				fallbackText.append(attachment.get("text"));
			}
			
			attachment.put("fallback", fallbackText.toString());
			
			String payloadString = toJSONString(payload);
			LogLog.debug("Prequest payload: " + payloadString);
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(payloadString);
			wr.flush();
			wr.close();

			LogLog.debug("Response Code: " + con.getResponseCode());

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			LogLog.debug("Response: " + response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogLog.error("Error posting to Slack", e);
		}
	}
	
	private static ScriptObjectMirror getJSObject() throws ScriptException {
		return (ScriptObjectMirror) engine.eval("new Object()");
	}
	
	private static ScriptObjectMirror getJSArray() throws ScriptException {
		return (ScriptObjectMirror) engine.eval("new Array()");
	}
	
	private static String toJSONString(ScriptObjectMirror object) throws ScriptException {
		ScriptObjectMirror json = (ScriptObjectMirror) engine.eval("JSON");
		return json.callMember("stringify", object).toString();
	}
	
	private static String escapeString(String text) {
		if (text == null) {
			return null;
		}
		/*
		 * https://api.slack.com/docs/formatting
		 * 
		 * Replace the ampersand, &, with &amp;
		 * Replace the less-than sign, < with &lt;
		 * Replace the greater-than sign, > with &gt;
		 */
		return text.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}