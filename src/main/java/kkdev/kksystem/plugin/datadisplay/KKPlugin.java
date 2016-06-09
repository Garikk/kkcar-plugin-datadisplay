/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay;

import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.KKPluginBase;
import kkdev.kksystem.base.interfaces.IKKControllerUtils;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.configuration.kk_DefaultConfig;
import kkdev.kksystem.plugin.datadisplay.displaymanager.DisplayManager;

/**
 *
 * @author blinov_is
 */
public final class KKPlugin extends KKPluginBase {

    public IKKControllerUtils SysUtils;

    public KKPlugin() {
        super(new DataProcessorInfo());
        Global.DM = new DisplayManager();
    }

    @Override
    public void pluginInit(IPluginBaseInterface BaseConnector, String GlobalConfUID) {
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
    public PluginMessage executePin(PluginMessage Pin) {
        Global.DM.RecivePin(Pin);
        super.executePin(Pin);
        //
        return null;
    }

    public IKKControllerUtils GetUtils() {
        return SysUtils;
    }
}
