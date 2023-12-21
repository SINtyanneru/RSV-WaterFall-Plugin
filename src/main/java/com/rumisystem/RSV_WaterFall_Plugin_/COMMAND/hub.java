package com.rumisystem.RSV_WaterFall_Plugin_.COMMAND;

import com.rumisystem.RSV_WaterFall_Plugin_.CONFIG_SYSTEM;
import com.rumisystem.RSV_WaterFall_Plugin_.RSV_WaterFall_Plugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class hub extends Command {
	private CONFIG_SYSTEM CS;

	public hub(){
		//コマンドとパーミッション
		super("hub");

		//設定を読み込む
		CS = RSV_WaterFall_Plugin.getInstance().CS;
	}

	public void execute(CommandSender sender, String[] arg){
		//送信者はプレイヤーか
		if ((sender instanceof ProxiedPlayer)) {
			//プレイヤーを変数にばーん
			ProxiedPlayer PLAYER = (ProxiedPlayer)sender;
			//プレイヤーを送り込む
			PLAYER.connect(ProxyServer.getInstance().getServerInfo(CS.LOADING().get("CONFIG").getString("HUB.SERVER")));

			//移動するメッセージをだす
			if(!CS.LOADING().get("CONFIG").getString("HUB.MSG").equals("NONE")){//メッセージの設定がNONEじゃないなら送信する
				PLAYER.sendMessage(new TextComponent(CS.LOADING().get("CONFIG").getString("HUB.MSG")));
			}
		}
	}
}
