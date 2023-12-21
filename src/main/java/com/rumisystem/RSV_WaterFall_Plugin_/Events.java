package com.rumisystem.RSV_WaterFall_Plugin_;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class Events implements Listener {
	private CONFIG_SYSTEM CS;
	public Events(){
		CS = RSV_WaterFall_Plugin.getInstance().CS;
	}

	//鯖に参加
	@EventHandler
	public void onJoin(PostLoginEvent E) {
		try {
			//全員に参加メッセージを送信
			for (ProxiedPlayer PLAYER : ProxyServer.getInstance().getPlayers()) {
				//PLAYER.setTabHeader(new TextComponent("これはヘッダー"), new TextComponent("こっちはフッター、タブリストって以外と色々出来る"));

				//チャットメッセージの作成
				TextComponent CHAT_MESSAGE = new TextComponent(
						CS.LOADING().get("CONFIG").getString("MESSAGE.JOIN")
								.replace("{USER_NAME}", E.getPlayer().getDisplayName())
				);

				//メッセージの色を設定する
				CHAT_MESSAGE.setColor(ChatColor.YELLOW);

				//プレイヤーにメッセージを送信
				PLAYER.sendMessage(ChatMessageType.CHAT, CHAT_MESSAGE);
			}
		}catch (Exception EX){
			EX.printStackTrace();
		}
	}

	//鯖から退出
	@EventHandler
	public void onProxyLeave(PlayerDisconnectEvent E) {
		try {
			//全員に退出メッセージを送信
			for (ProxiedPlayer PLAYER : ProxyServer.getInstance().getPlayers()) {
				//チャットメッセージの作成
				TextComponent CHAT_MESSAGE = new TextComponent(
						CS.LOADING().get("CONFIG").getString("MESSAGE.LEFT")
								.replace("{USER_NAME}", E.getPlayer().getDisplayName())
				);

				//メッセージの色を設定する
				CHAT_MESSAGE.setColor(ChatColor.YELLOW);

				//プレイヤーにメッセージを送信
				PLAYER.sendMessage(ChatMessageType.CHAT, CHAT_MESSAGE);
			}
		}catch (Exception EX){
			EX.printStackTrace();
		}
	}

	//鯖間を移動
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent E) {
		try {
			//鯖名(内部名)
			String SERVER_NAME = E.getPlayer().getServer().getInfo().getName().toString();

			//鯖名が設定されているか
			if(CS.LOADING().get("SERVER_NAME").contains("JP_JA." + SERVER_NAME)){
				//その設定はNullではないか
				if(CS.LOADING().get("SERVER_NAME").getString(SERVER_NAME) != null){
					//鯖名に設定先を代入
					SERVER_NAME = CS.LOADING().get("SERVER_NAME").getString(SERVER_NAME).toString();
				}
			}

			//全員に移動メッセージを送信
			for (ProxiedPlayer PLAYER : ProxyServer.getInstance().getPlayers()) {
				//チャットメッセージの作成
				TextComponent CHAT_MESSAGE = new TextComponent(
						CS.LOADING().get("CONFIG").getString("MESSAGE.MOVE")
								.replace("{USER_NAME}", E.getPlayer().getDisplayName())
								.replace("{NEXT_SERVER}", SERVER_NAME)
				);

				//メッセージの色を設定する
				CHAT_MESSAGE.setColor(ChatColor.YELLOW);

				//プレイヤーにメッセージを送信
				PLAYER.sendMessage(ChatMessageType.CHAT, CHAT_MESSAGE);
			}
		}catch (Exception EX){
			EX.printStackTrace();
		}
	}

	//チャット
	@EventHandler
	public void onChat(ChatEvent E) {
		try {
			//チャット機能が有効化
			if(CS.LOADING().get("CONFIG").getBoolean("CHAT.ENABLE")){
				//これはコマンド？？
				if (!E.isCommand()) {//コマンドじゃないよ！
					//チャットを鯖に送らないための処理
					E.setCancelled(true);

					//プレイヤー
					ProxiedPlayer SENDER_PLAYER = (ProxiedPlayer) E.getSender();

					//全プレイヤーにチャットを送信
					for (ProxiedPlayer PLAYER : ProxyServer.getInstance().getPlayers()) {
						//チャットメッセージの作成
						TextComponent CHAT_MESSAGE = new TextComponent(CS.LOADING().get("CONFIG").getString("CHAT.TEXT")
								.replace("{USER_NAME}", E.getSender().toString())
								.replace("{TEXT}", E.getMessage().toString())
						);

						//メッセージの色を設定する
						CHAT_MESSAGE.setColor(ChatColor.WHITE);

						//プレイヤーにメッセージを送信
						PLAYER.sendMessage(SENDER_PLAYER.getUniqueId(), CHAT_MESSAGE);
					}
				}
			}
		} catch (Exception EX) {
			EX.printStackTrace();
		}
	}
}
