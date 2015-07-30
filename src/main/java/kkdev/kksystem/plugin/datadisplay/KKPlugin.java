/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay;

import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.KKPluginBase;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.displaymanager.DisplayManager;

/**
 *
 * @author blinov_is
 */
public final class KKPlugin  extends KKPluginBase{

    public KKPlugin() {
        super(new DataProcessorInfo());
        Global.DM = new DisplayManager();
    }

    @Override
    public void PluginInit(IPluginBaseInterface BaseConnector, String GlobalConfUID) {
        super.PluginInit(BaseConnector, GlobalConfUID); //To change body of generated methods, choose Tools | Templates.
        //
        PluginSettings.InitSettings(GlobalConfUID,  this.PluginInfo.GetPluginInfo().PluginUUID);
        
    }

     @Override
    public void PluginStart() {
         Global.DM.InitDisplayManager(this);
         Global.DM.Start();
         super.PluginStart();
    }

    @Override
    public PluginMessage ExecutePin(PluginMessage Pin) {
         Global.DM.RecivePin(Pin);
         super.ExecutePin(Pin);
        //
        return null;
    }
}
