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
    public static final String DATADISPLAY_CONF="kk_plugin_datadisplay.json";
    public static DataDisplayConfig MainConfiguration;
  
    public static void InitSettings()
    {
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
