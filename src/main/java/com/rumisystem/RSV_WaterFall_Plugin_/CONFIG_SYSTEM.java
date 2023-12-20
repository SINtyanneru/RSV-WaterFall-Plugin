package com.rumisystem.RSV_WaterFall_Plugin_;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.moandjiezana.toml.Toml;

public class CONFIG_SYSTEM{
	private static File WORK_DIR;
	private static Toml CONFIG_DATA;

	/**
	 * 設定ファイルのチェック
	 * */
	public static void CHECK(){
		RSV_WaterFall_Plugin PLUGIN_INSTACE = RSV_WaterFall_Plugin.getInstance();
		try{
			//作業フォルダを取得
			WORK_DIR = PLUGIN_INSTACE.getDataFolder();

			//フォルダが存在するか
			if (!WORK_DIR.exists()) {
				WORK_DIR.mkdirs();
			}

			//設定ファイルの中身
			StringBuilder CONFIG_FILE_DATA = new StringBuilder();
			CONFIG_FILE_DATA.append("#RumiSerVer WaterFall Pluginの設定ファイル！\n");
			CONFIG_FILE_DATA.append("#チャットに関する設定\n");
			CONFIG_FILE_DATA.append("[CHAT]\n");
			CONFIG_FILE_DATA.append("ENABLE=true #有効無効\n");
			CONFIG_FILE_DATA.append("TEXT=\"<{USER_NAME}>{TEXT}\" #チャットの文章を変えれます、[]でも何でもどうぞ\n");

			//設定ファイルの一覧
			String[] CONFIG_FILES = {"CONFIG.toml"};
			String[] CONFIG_FILES_DATA = {CONFIG_FILE_DATA.toString()};

			//ファイルチェック
			for(int I_FILECHECK = 0; I_FILECHECK < CONFIG_FILES.length; I_FILECHECK++){
				String FILE_NAME = CONFIG_FILES[I_FILECHECK];
				String FILE_DATA = CONFIG_FILES_DATA[I_FILECHECK];

				//クルクル回すやつ
				Timer TIMER = new Timer();
				TIMER.scheduleAtFixedRate(new TimerTask() {
					int I = 0;
					@Override
					public void run() {
						switch (I){
							case 0:
								PLUGIN_INSTACE.getLogger().info("[|]Cheking:" + FILE_NAME);
								break;
							case 1:
								PLUGIN_INSTACE.getLogger().info("\033[1A[  /  ]Cheking:" + FILE_NAME);
								break;
							case 2:
								PLUGIN_INSTACE.getLogger().info("\033[1A[  -  ]Cheking:" + FILE_NAME);
								break;
							case 3:
								PLUGIN_INSTACE.getLogger().info("\033[1A[  \\  ]Cheking:" + FILE_NAME);
								break;
							case 4:
								PLUGIN_INSTACE.getLogger().info("\033[1A[  |  ]Cheking:" + FILE_NAME);
								I = 0;
								break;
						}
						I++;
					}
				}, 0, 500);

				Path FILE_PATH = Paths.get(WORK_DIR.getPath() + "/" + FILE_NAME);
				if(Files.exists(FILE_PATH)){
					//タイマーを殺す
					TIMER.cancel();

					//完了したと表示
					PLUGIN_INSTACE.getLogger().info("\033[1A[  OK  ]Cheking:" + FILE_NAME);
				}else {
					//タイマーを殺す
					TIMER.cancel();

					//完了したと表示
					PLUGIN_INSTACE.getLogger().info("\033[1A[  OK  ]Cheking:" + FILE_NAME);

					//ファイル作成開始
					PLUGIN_INSTACE.getLogger().info("[  ***  ]FILE Creating...");

					//ファイルに書き込むためのBufferedWriterを作成
					BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH.toString()));
					//ファイルに書き込む
					writer.write(FILE_DATA);
					// ファイルを閉じる
					writer.close();

					//完了報告
					PLUGIN_INSTACE.getLogger().info("[  OK  ]FILE Created");
				}
			}
		}catch (Exception EX){
			PLUGIN_INSTACE.getLogger().warning(EX.getMessage());
		}
	}

	public static Toml LOADING(){
		if(CONFIG_DATA == null){
			//Tomlファイルのパスを指定
			String TOML_FILE_PATH = WORK_DIR + "/CONFIG.toml";

			//Tomlファイルを解析
			Toml TOML_DATA = new Toml().read(new File(TOML_FILE_PATH));

			//解析結果を入れる
			CONFIG_DATA = TOML_DATA;

			return CONFIG_DATA;
		}else {
			return CONFIG_DATA;
		}
	}
}
