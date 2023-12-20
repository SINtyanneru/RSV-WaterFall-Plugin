package com.rumisystem.RSV_WaterFall_Plugin_;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Events implements Listener {
	private CONFIG_SYSTEM CS;
	public Events(CONFIG_SYSTEM CS_){
		CS = CS_;
	}
	@EventHandler
	//チャット
	public void onChat(ChatEvent E) {
		try {
			//チャット機能が有効化
			if(CS.LOADING().getTable("CHAT").getBoolean("ENABLE")){
				//これはコマンド？？
				if (!E.isCommand()) {//コマンドじゃないよ！
					//チャットを鯖に送らないための処理
					E.setCancelled(true);
					////全プレイヤーにチャットを送信
					for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
						//チャットメッセージの作成
						TextComponent CHAT_MESSAGE = new TextComponent(CS.LOADING().getTable("CHAT").getString("TEXT")
								.replace("{USER_NAME}", E.getSender().toString())
								.replace("{TEXT}", E.getMessage().toString())
						);

						//メッセージの色を設定する
						CHAT_MESSAGE.setColor(ChatColor.WHITE);

						//プレイヤーにメッセージを送信
						player.sendMessage(ChatMessageType.CHAT, CHAT_MESSAGE);
					}
				}
			}
		} catch (Exception EX) {
			EX.printStackTrace();
		}
	}
}
