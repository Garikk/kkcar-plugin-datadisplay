/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.classes.led.DisplayConstants.KK_DISPLAY_COMMAND;
import kkdev.kksystem.base.classes.led.PinLedCommand;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_DEF_PIN_LED_COMMAND;
import kkdev.kksystem.plugin.datadisplay.DataProcessorInfo;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;

/**
 *
 * @author blinov_is
 */
public class DisplayManager {
    
    boolean LEDConnected=false;
    KKPlugin Connector;
    
    DisplayManager(KKPlugin Conn)
    {
        Connector=Conn;
    }
    
    

    public void ConnectDisplay()
    {
        PluginMessage Msg=new PluginMessage();
        Msg.SenderUID=DataProcessorInfo.GetPluginInfo().PluginUUID;
        Msg.PinName=KK_PLUGIN_BASE_PLUGIN_DEF_PIN_LED_COMMAND;
        //
        PinLedCommand PData=new PinLedCommand();
        PData.Command=KK_DISPLAY_COMMAND.DISPLAY_KKSYS_GETINFO;
        //
        Msg.PinData=PData;
        //
        Connector.ExecutePin(Msg);
    
    }
    
    public void CreatePage()
    {
        
    }
    
    public void ChangePage()
    {
    
    }
    
    public void RefreshPage()
    {
        
    }
    
    public void RecivePin(PluginMessage Msg)
    {
        
    
    }
    
}
