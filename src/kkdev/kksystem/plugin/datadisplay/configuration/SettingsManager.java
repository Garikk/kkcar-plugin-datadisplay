/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.constants.SystemConsts;

/**
 *
 * @author blinov_is
 */
public abstract class SettingsManager {
    public static final String DATADISPLAY_CONF="kk_plugin_datadisplay.json";

    public static DataDisplayConfig MainConfiguration;
    public static void InitSettings()
    {
          LoadConfig();
        
        if (MainConfiguration==null)
        {
            kk_DefaultConfig.MakeDefaultConfig();
            LoadConfig();
        }
        if (MainConfiguration==null)
        {
            System.out.println("[DataDisplay] Config load error");
            return;
        }
       //

        //
    }
      private static void LoadConfig() 
    {
       try {
           Gson gson=new Gson();
           BufferedReader br = new BufferedReader(  
                 new FileReader(SystemConsts.KK_BASE_CONFPATH + "/"+DATADISPLAY_CONF));  

           MainConfiguration = (DataDisplayConfig)gson.fromJson(br, DataDisplayConfig.class);
           
           
       } catch (FileNotFoundException ex) {
           return;
       }
        
        
    }

        
}
