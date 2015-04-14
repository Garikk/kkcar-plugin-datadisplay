/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay;

import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.classes.PluginMessage;
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
    
    public void SendPluginMessageCommand(KK_DISPLAY_COMMAND Command, String[] DataStr, int[] DataInt, boolean[] DataBool)
    {
         PluginMessage Msg=new PluginMessage();
        Msg.SenderUID=MyUID;
        Msg.PinName=KK_PLUGIN_BASE_LED_COMMAND;
        //
        PinLedCommand PData=new PinLedCommand();
        PData.Command=Command;
        PData.BOOL=DataBool;
        PData.INT=DataInt;
        PData.STRING=DataStr;
        
        Msg.PinData=PData;
        //
        Connector.ExecutePinCommand(Msg);
    }

     public void SendPluginMessageData(KK_DISPLAY_DATA Command, PinLedData PData)
    {
         PluginMessage Msg=new PluginMessage();
        Msg.SenderUID=MyUID;
        Msg.PinName=KK_PLUGIN_BASE_LED_DATA;
        //
        Msg.PinData=PData;
        //
        Connector.ExecutePinCommand(Msg);
    }

   
    
}

