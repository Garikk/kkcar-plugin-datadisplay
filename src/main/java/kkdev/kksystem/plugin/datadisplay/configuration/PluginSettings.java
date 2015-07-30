/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import kkdev.kksystem.base.classes.plugins.simple.SettingsManager;

/**
 *
 * @author blinov_is
 */
public abstract class PluginSettings  {
    
    private static SettingsManager Settings;
    public static String DATADISPLAY_CONF;
    public static DataDisplayConfig MainConfiguration;
  
    public static void InitSettings(String GlobalConfigUID,String MyUID)
    {
        DATADISPLAY_CONF=GlobalConfigUID+"_"+MyUID+".json";
        
        Settings=new SettingsManager(DATADISPLAY_CONF,DataDisplayConfig.class);
        
        MainConfiguration=(DataDisplayConfig)Settings.LoadConfig();
        
        System.out.println("[DataDisplay][CONFIG] Load config");
        
        if (MainConfiguration==null)
        {
            System.out.println("[DataDisplay][CONFIG] Error, create default config");
            Settings.SaveConfig(kk_DefaultConfig.MakeDefaultConfig());
            System.out.println("[DataDisplay][CONFIG] Load default config");
             MainConfiguration=(DataDisplayConfig)Settings.LoadConfig();
        }
        if (MainConfiguration==null)
        {
            System.out.println("[DataDisplay][CONFIG] Config load error, fatal");
            return;
        }

    }
}
