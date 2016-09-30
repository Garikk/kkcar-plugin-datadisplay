/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay;

import kkdev.kksystem.base.classes.plugins.PluginConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.KKPluginBase;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.configuration.kk_DefaultConfig;
import kkdev.kksystem.plugin.datadisplay.displaymanager.DisplayManager;
import kkdev.kksystem.base.interfaces.IPluginBaseConnection;
import kkdev.kksystem.base.interfaces.IControllerUtils;

/**
 *
 * @author blinov_is
 */
public final class KKPlugin extends KKPluginBase {

    public IControllerUtils SysUtils;

    public KKPlugin() {
        super(new DataProcessorInfo());
        Global.DM = new DisplayManager();
    }

    @Override
    public void pluginInit(IPluginBaseConnection BaseConnector, String GlobalConfUID) {
        super.pluginInit(BaseConnector, GlobalConfUID); //To change body of generated methods, choose Tools | Templates.
        SysUtils = BaseConnector.systemUtilities();
        //
        PluginSettings.InitSettings(GlobalConfUID, this.pluginInfo.getPluginInfo().PluginUUID);
        kk_DefaultConfig.addDefaultSystemUIPages(SysUtils);
    }

    @Override
    public void pluginStart() {
        Global.DM.InitDisplayManager(this);
        Global.DM.Start();
        super.pluginStart();
    }

    @Override
    public void executePin(PluginMessage Pin) {
        Global.DM.RecivePin(Pin);
        super.executePin(Pin);
        //
    }

    public IControllerUtils GetUtils() {
        return SysUtils;
    }
     @Override
    public PluginConfiguration getPluginSettings() {
       return PluginSettings.MainConfiguration;
    }
}
