package com.rumisystem.RSV_WaterFall_Plugin_;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.moandjiezana.toml.Toml;

public class CONFIG_SYSTEM{
	private static File WORK_DIR;
	private static HashMap<String, Toml> CONFIG_DATA = new HashMap<>();
	private static String[] CONFIG_FILES = {"CONFIG", "SERVER_NAME"};
	private static String[] CONFIG_FILES_DATA;

	/**
	 * 設定ファイルのチェック
	 * */
	public void CHECK(){
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
			CONFIG_FILE_DATA.append("\n");
			CONFIG_FILE_DATA.append("#チャットに関する設定\n");
			CONFIG_FILE_DATA.append("[CHAT]\n");
			CONFIG_FILE_DATA.append("ENABLE=true #有効無効\n");
			CONFIG_FILE_DATA.append("TEXT=\"<{USER_NAME}>{TEXT}\" #チャットの文章を変えれます、[]でも何でもどうぞ\n");
			CONFIG_FILE_DATA.append("\n");
			CONFIG_FILE_DATA.append("#メッセージ\n");
			CONFIG_FILE_DATA.append("[MESSAGE]\n");
			CONFIG_FILE_DATA.append("JOIN=\"{USER_NAME}さんが参加しました\"\n");
			CONFIG_FILE_DATA.append("LEFT=\"{USER_NAME}さんが退出しました\"\n");
			CONFIG_FILE_DATA.append("MOVE=\"{USER_NAME}さんが{NEXT_SERVER}へ移動しました\"\n");

			//設定ファイルの中身
			StringBuilder SERVER_NAME_FILE_DATA = new StringBuilder();
			SERVER_NAME_FILE_DATA.append("#鯖の名前を設定できるやつ\n");
			SERVER_NAME_FILE_DATA.append("\n");
			SERVER_NAME_FILE_DATA.append("#鯖の内部名=置き換え先\n");

			//設定ファイルの一覧
			CONFIG_FILES_DATA = new String[]{CONFIG_FILE_DATA.toString(), SERVER_NAME_FILE_DATA.toString()};

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

				Path FILE_PATH = Paths.get(WORK_DIR.getPath() + "/" + FILE_NAME + ".toml");
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

	public HashMap<String, Toml> LOADING(){
		RSV_WaterFall_Plugin PLUGIN_INSTACE = RSV_WaterFall_Plugin.getInstance();
		if(CONFIG_DATA.size() == 0){
			for(String FILE_NAME:CONFIG_FILES){
				//Tomlファイルのパスを指定
				String TOML_FILE_PATH = WORK_DIR + "/" + FILE_NAME + ".toml";

				//Tomlファイルを解析
				Toml TOML_DATA = new Toml().read(new File(TOML_FILE_PATH));

				//解析結果を入れる
				CONFIG_DATA.put(FILE_NAME, TOML_DATA);
			}

			return CONFIG_DATA;
		}else {
			return CONFIG_DATA;
		}
	}
}
