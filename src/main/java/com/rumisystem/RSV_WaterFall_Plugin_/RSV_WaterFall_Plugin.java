package com.rumisystem.RSV_WaterFall_Plugin_;

import net.md_5.bungee.api.plugin.Plugin;

public final class RSV_WaterFall_Plugin extends Plugin {
	private static RSV_WaterFall_Plugin INSTANCE;
	public CONFIG_SYSTEM CS; //外部からクセス出来る設定システムのインスタンス

	@Override
	public void onEnable() {
		INSTANCE = this;

		//プラグインの起動処理
		getLogger().info("+----------[RWP]----------+");
		getLogger().info("|RWP Enabled!!!           |");
		getLogger().info("|V1.0                     |");
		getLogger().info("+-------------------------+");

		CS = new CONFIG_SYSTEM();
		CS.CHECK();
		CS.LOADING();

		getProxy().getPluginManager().registerListener(this, new Events());

		//有効化？
		super.onEnable();
	}

	@Override
	public void onDisable() {
		//プラグインのシャットダウン処理
		getLogger().info("RWP Disabled... GoodBye");

		super.onDisable();
	}

	public static RSV_WaterFall_Plugin getInstance() {
		return INSTANCE;
	}
}
