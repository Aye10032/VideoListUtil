package com.aye10032.config;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import javax.print.attribute.standard.Finishings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.aye10032.background.ProjectInit.initConfig;

/**
 * @program: VideoListUtil
 * @description: 存取配置文件
 * @author: Aye10032
 * @create: 2021-01-21 23:01
 **/
public class ConfigIO {
    private static final String CONFIG_PATH = "config";

    public static void saveConfig(ConfigSet configSet){
        Gson gson = new Gson();
        String json_string = gson.toJson(configSet);
        System.out.println(json_string);

        try {
            FileUtils.writeStringToFile(new File(CONFIG_PATH), json_string, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigSet loadConfig(){
        String json_string = "";

        File config_file = new File(CONFIG_PATH);
        if (!config_file.exists()){
            initConfig();
        }

        try {
            json_string = FileUtils.readFileToString(config_file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        return gson.fromJson(json_string, ConfigSet.class);
    }

}
