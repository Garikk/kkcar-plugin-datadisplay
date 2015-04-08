/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import java.util.HashMap;
import java.util.Hashtable;
import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_COMMAND;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_DATA;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.configuration.DisplayPage;
import kkdev.kksystem.plugin.datadisplay.configuration.SettingsManager;

/**
 *
 * @author blinov_is
 */
public abstract class DisplayManager {
   
    static KKPlugin Connector;
    static HashMap<String,DataProcessor> Processors;
      
    public  static void  InitDisplayManager(KKPlugin Conn)
    {
        Connector=Conn;
        //
        System.out.println("[DataDisplay][INIT] Data processor initialising");
        SettingsManager.InitSettings();
        //
        System.out.println("[DataDisplay][PROC] Init Data processors");
        ConnectProcessors();
    }
    
    public static void Start()
    {
        //Init Pages
        for (DisplayPage DP:SettingsManager.MainConfiguration.Pages)
        {
            InitDisplayPage(DP);   
        }
        //Send test Data
        
    }
  
    public static void debug_SendWelcomeText(String PageID, String text)
    {
        String[] Txt=new String[1];
        Txt[0]=text;
        //
        PinLedData PD=new PinLedData();
        PD.DataType=DisplayConstants.KK_DISPLAY_DATA.DISPLAY_KKSYS_TEXT_SIMPLE_OUT;
        PD.TargetPage=PageID;
        PD.Direct_DisplayText=Txt;
        //
        
       Connector.SendPluginMessageData(KK_DISPLAY_DATA.DISPLAY_KKSYS_TEXT_SIMPLE_OUT,PD); 
    }
    
    private static void ConnectProcessors()
    {
        Processors=new HashMap<>();
        
        for (DataProcessor DP:SettingsManager.MainConfiguration.Processors)
        {
            
        }
    }
    
    private static void InitDisplayPage(DisplayPage Page)
    {
        //
        String[] Data_S=new String[1];
        Data_S[0]=Page.PageName;
        //
        //Init main page
       Connector.SendPluginMessageCommand(KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_INIT, Data_S, null, null);
       // Set page to active
       if (Page.ActivateOnLoad)
        Connector.SendPluginMessageCommand(KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_ACTIVATE, Data_S, null, null);
       // Send Hello world
       //debug_SendWelcomeText(PageID,"Hello World!");
    }
    ///////////////////
    ///////////////////
    public static void RecivePin(PluginMessage Msg)
    {
        switch (Msg.PinName)
        { case PluginConsts.KK_PLUGIN_BASE_LED_COMMAND:
                PinLedCommand CMD;
                CMD=(PinLedCommand)Msg.PinData;
                ProcessCommand(CMD);
                break;
            case PluginConsts.KK_PLUGIN_BASE_LED_DATA:
                PinLedData DAT;
                DAT=(PinLedData)Msg.PinData;
                ProcessData(DAT);
                break;
        }
    }
    ///////////////////
    ///////////////////
    public static void ProcessCommand(PinLedCommand Command)
    {
        
    }
    public static void ProcessData(PinLedData Data)
    {
        
        switch (Data.DataType)
        {
            case DISPLAY_KKSYS_DISPLAY_STATE:
               // UpdateDisplayState(Data.DisplayState);
                break;
        }
    }
}
