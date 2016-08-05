/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import kkdev.kksystem.base.classes.plugins.PluginConfiguration;

/**
 *
 * @author blinov_is
 */
public class DataDisplayConfig extends PluginConfiguration {
    public String FeatureID;
    public String[] UIContexts;
    public DataProcessor[] Processors;
    
    public String PrimaryUIContext;
    public String SecondaryUIContext;
    
    
}
