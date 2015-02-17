/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import java.util.HashMap;
import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.classes.led.DisplayConstants;
import kkdev.kksystem.base.classes.led.DisplayConstants.KK_DISPLAY_COMMAND;
import kkdev.kksystem.base.classes.led.DisplayConstants.KK_DISPLAY_DATA;
import kkdev.kksystem.base.classes.led.DisplayInfo;
import kkdev.kksystem.base.classes.led.PinLedCommand;
import kkdev.kksystem.base.classes.led.PinLedData;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;

/**
 *
 * @author blinov_is
 */
public class DisplayManager {
   
    KKPlugin Connector;
    HashMap<String,DisplayInfo> Displays;
    
    public DisplayManager(KKPlugin Conn)
    {
        Connector=Conn;
        Displays=new HashMap<>();
        debug_SendWelcomeText("HELLO WORLD");
    }
    
    public void ConnectDisplays()
    {
        //Start init process, send request for displays
       Connector.SendPluginMessageCommand(KK_DISPLAY_COMMAND.DISPLAY_KKSYS_GETINFO,null,null,null);
       //
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
    public void debug_SendWelcomeText(String text)
    {
        String[] Txt=new String[1];
        Txt[0]=text;
        //
        PinLedData PD=new PinLedData();
        PD.DataType=DisplayConstants.KK_DISPLAY_DATA.DISPLAY_KKSYS_TEXT;
        PD.TargetPage="MAIN";
        PD.DisplayText=Txt;
        //
        
       Connector.SendPluginMessageData(KK_DISPLAY_DATA.DISPLAY_KKSYS_TEXT,PD); 
    }
    ///////////////////
    ///////////////////
    public void RecivePin(PluginMessage Msg)
    {
        switch (Msg.PinName)
        { case PluginConsts.KK_PLUGIN_BASE_PLUGIN_DEF_PIN_LED_COMMAND:
                PinLedCommand CMD;
                CMD=(PinLedCommand)Msg.PinData;
                ProcessCommand(CMD);
                break;
            case PluginConsts.KK_PLUGIN_BASE_PLUGIN_DEF_PIN_LED_DATA:
                PinLedData DAT;
                DAT=(PinLedData)Msg.PinData;
                ProcessData(DAT);
                break;
        }
    }
    ///////////////////
    ///////////////////
    public void ProcessCommand(PinLedCommand Command)
    {
        
    }
    public void ProcessData(PinLedData Data)
    {
        
        switch (Data.DataType)
        {
            case DISPLAY_KKSYS_DISPLAY_STATE:
                UpdateDisplayState(Data.DisplayState);
                break;
        }
    }
    
    private void UpdateDisplayState(DisplayInfo[] State)
    {
        for (DisplayInfo DI:State)
            if (!Displays.containsKey(DI.DisplayID))
                Displays.put(DI.DisplayID, DI);
            else
                Displays.replace(DI.DisplayID, DI);
        
        
    }
}
