package org.usfirst.frc.team1124.robot.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfigIO {
	// saves and imports from and to "/home/lvuser/config/robot.cfg"
	// i need to edit this file to make a new commit
	private static File file;
	private final static String filePath = "/home/lvuser/config/robot.cfg";
	static Map<String, String> config;
	static BufferedReader br1;
	static ArrayList<String> rawConfig;
	static ArrayList<String> fullConfig;
	//I HATE FISHLER

	public ConfigIO(){
		rawConfig = new ArrayList<String>();
		fullConfig = new ArrayList<String>();
		file = new File(filePath);
		config = new HashMap<String, String>();

		try{
			file.createNewFile();
		}catch(IOException e1){
			e1.printStackTrace();
		}

		reloadConfig();
		//for(int v = 0; v < config.size(); v++)
		//System.out.println(config.keySet().toArray()[v] + " = " + config.get(config.keySet().toArray()[v]));
	}

	/** Get the string value from the config file.*/
	public String getStringVal(String key){
		return config.get(key);
	}

	/** Get the integer value from the config file.*/
	public int getIntVal(String key){
		return Integer.parseInt(config.get(key));
	}

	private void reloadConfig(){
		try{ 
			br1 = new BufferedReader(new FileReader(filePath)); 
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}

		config.clear();
		rawConfig.clear();
		fullConfig.clear();

		try{
			String line = br1.readLine();

			while(line != null){
				fullConfig.add(line);
				if(line.charAt(0) != '#'){
					String value1 = "";
					String value2 = "";
					rawConfig.add(line);

					boolean encounteredSpace = false;

					for(int l = 0; l < line.length() && !encounteredSpace; l++){
						if(line.charAt(l) != ' '){
							value1 += line.charAt(l);
						}else {
							value2 = line.substring(l + 1);
							encounteredSpace = true;
						}
					}

					if(encounteredSpace){
						config.put(value1, value2);
					}
				}

				line = br1.readLine();
			}
		}catch(IOException e){ 
			e.printStackTrace(); 
		}finally{ 
			try{ 
				br1.close(); 
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
	}

	/** */
	public ArrayList<String> getConfigText(){
		reloadConfig();
		return rawConfig;
	}

	/** Writes keys and values to config map. If the key already exists it overrides the existing value. */
	public void changeKeyVal(String key, String value){
		reloadConfig();
		config.put(key, value);

		PrintWriter wr0 = null;

		try{
			wr0 = new PrintWriter(filePath, "UTF-8");
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		for(int c = 0; c < fullConfig.size(); c++){
			boolean foundKey = false; 
			String fCLine = "";
			for(int i = 0; i <  config.size() && !foundKey; i++){
				String tempKey = (String) config.keySet().toArray()[i];
				String tempVal = config.get(tempKey);
				
				fCLine = fullConfig.get(c);
				String fCKey = "";
				for(int s = 0; s < fCLine.length() && fCLine.charAt(0) != '#' && fCLine.charAt(s) != ' '; s++){
					if(fCLine.charAt(s) != ' ')
						fCKey += fCLine.charAt(s);
				}
				
				if(fCKey.equals(tempKey)){
					wr0.println(tempKey + " " + tempVal);
					foundKey = true;
				}
			}
			if(!foundKey)
				wr0.println(fCLine);
		}

		wr0.close();
	}
}
