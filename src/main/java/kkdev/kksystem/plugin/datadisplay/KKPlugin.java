/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay;

import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_COMMAND;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_DATA;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_LED_COMMAND;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_LED_DATA;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import kkdev.kksystem.plugin.datadisplay.displaymanager.DisplayManager;

/**     
 *
 * @author blinov_is
 */
public final class KKPlugin implements IPluginKKConnector   {

    IPluginBaseInterface Connector;
    String MyUID;
   
    public KKPlugin()
            {
                MyUID=GetPluginInfo().PluginUUID;
            }
    
    @Override
    public PluginInfo GetPluginInfo() {
         return DataProcessorInfo.GetPluginInfo();
    }

    @Override
    public void PluginInit(IPluginBaseInterface BaseConnector) {
       Connector=BaseConnector;
    }

    @Override
    public void PluginStart() {

        DisplayManager.InitDisplayManager(this);
       DisplayManager.Start();

    }

    @Override
    public void PluginStop() {
        //TODO make destruct
        //DDisplay=null;
    }

    @Override
    public PluginMessage ExecutePin(PluginMessage Pin) {
        DisplayManager.RecivePin(Pin);
        //
        return null;
    }
    
    public void TransmitPinMessage(PluginMessage Pin)
    {
        Pin.SenderUID=MyUID;
        Connector.ExecutePinCommand(Pin);
    }
    
   

   
    
}

