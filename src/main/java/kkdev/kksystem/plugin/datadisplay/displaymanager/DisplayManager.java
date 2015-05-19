/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import java.util.HashMap;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_COMMAND;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_COMMANDTYPE;
import kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_DATAPACKET;
import static kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_DATATYPE.ODB_BASE_CONNECTOR;
import kkdev.kksystem.base.classes.odb2.PinOdb2Command;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_LED_COMMAND;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_LED_DATA;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_ODB2_COMMAND;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.configuration.DisplayPage;
import kkdev.kksystem.plugin.datadisplay.configuration.SettingsManager;
import kkdev.kksystem.plugin.datadisplay.processors.odb.ODBDataDisplay;

/**
 *
 * @author blinov_is
 */
public abstract class DisplayManager {

    static KKPlugin Connector;
    static HashMap<String, DataProcessor> Processors;
    static String CurrentPage;

    public static void InitDisplayManager(KKPlugin Conn) {
        Connector = Conn;
        //
        System.out.println("[DataDisplay][INIT] Data processor initialising");
        SettingsManager.InitSettings();
        //
        System.out.println("[DataDisplay][PROC] Init Data processors");
        InitDataProcessors();
    }

    private static void InitDataProcessors() {
        Processors = new HashMap<>();

        for (DataProcessor DP : SettingsManager.MainConfiguration.Processors) {
            if (DP.ProcessorType == DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_ELM327_BASIC_ODB2) {
                DP.Processor = new ODBDataDisplay();
            }
        }
    }

    public static void Start() {
        //Init Pages
        for (DisplayPage DP : SettingsManager.MainConfiguration.Pages) {
            InitDisplayPage(DP);
        }
        //Connect ODB Adapter
        ODBManager.ConnectODBSource();
    }

    private static void InitDisplayPage(DisplayPage Page) {
        //Local
        if (Page.IsDefaultPage)
            CurrentPage=Page.PageName;
        //Send init to Display plugin
        String[] Data_S = new String[1];
        Data_S[0] = Page.PageName;
        //
        //Init main page
        DISPLAY_SendPluginMessageCommand(KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_INIT, Data_S, null, null);
        // Set page to active
        if (Page.IsDefaultPage) {
            CurrentPage=Page.PageName;
            DISPLAY_SendPluginMessageCommand(KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_ACTIVATE, Data_S, null, null);
        }
        // Send Hello world
        //debug_SendWelcomeText(PageID,"Hello World!");
    }
    ///
    ///
    ///
    public static void ChangeDisplayPage(String NewPage)
    {
        System.out.println("[ODB2][PAGE] Change to " +NewPage);
        if (CurrentPage.equals(NewPage))
            return;
        //
        ODBManager.ChangePage(CurrentPage, NewPage);
        CurrentPage=NewPage;
        
        String[] Data_S = new String[1];
        Data_S[0] = CurrentPage; 
        DISPLAY_SendPluginMessageCommand(KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_ACTIVATE, Data_S, null, null);
    }
    
    
    ////
    ///
    ///

    public static void ODB_SendPluginMessageCommand(KK_ODB_COMMANDTYPE Command, KK_ODB_DATAPACKET Request, int[] DataInt, int[] ReadInterval) {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_ODB2_COMMAND;
        //

        PinOdb2Command PData = new PinOdb2Command();
        PData.Command = Command;
        PData.CommandData = Request;
        //
        PData.RequestPIDs = DataInt;
        PData.DynamicRequestInterval = ReadInterval;
        //
        //PData.
        Msg.PinData = PData;
        //
        Connector.TransmitPinMessage(Msg);
    }

    public static void DISPLAY_SendPluginMessageCommand(KK_DISPLAY_COMMAND Command, String[] DataStr, int[] DataInt, boolean[] DataBool) {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_LED_COMMAND;
        //
        PinLedCommand PData = new PinLedCommand();
        PData.Command = Command;
        PData.BOOL = DataBool;
        PData.INT = DataInt;
        PData.STRING = DataStr;
        PData.PageID=DataStr[0];
        
        
        PData.FeatureUID=KK_BASE_FEATURES_ODB_DIAG_UID;
        Msg.PinData = PData;
        //
        Connector.TransmitPinMessage(Msg);
    }

    public static void DISPLAY_SendPluginMessageData(DisplayConstants.KK_DISPLAY_DATA Command, PinLedData PData) {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_LED_DATA;
        //
        PData.FeatureUID=KK_BASE_FEATURES_ODB_DIAG_UID;
        Msg.PinData = PData;
        //
        Connector.TransmitPinMessage(Msg);
    }

    ///////////////////
    ///////////////////
    public static void RecivePin(PluginMessage Msg) {
        switch (Msg.PinName) {
            case PluginConsts.KK_PLUGIN_BASE_LED_COMMAND:
                PinLedCommand CMDLed;
                CMDLed = (PinLedCommand) Msg.PinData;
                ProcessLcdCommand(CMDLed);
                break;
            case PluginConsts.KK_PLUGIN_BASE_LED_DATA:
                PinLedData DATLed;
                DATLed = (PinLedData) Msg.PinData;
                ProcessLcdData(DATLed);
                break;
            case PluginConsts.KK_PLUGIN_BASE_ODB2_COMMAND:
                PinOdb2Command ODBCmd;
                ODBCmd = (PinOdb2Command) Msg.PinData;
                ProcessOdbCommand(ODBCmd);
                break;
            case PluginConsts.KK_PLUGIN_BASE_ODB2_DATA:
                PinOdb2Data OdbDat;
                OdbDat = (PinOdb2Data) Msg.PinData;
                ProcessOdbData(OdbDat);
                break;
        }
    }

    ///////////////////
    //RECEIVE ODB Data
    ///////////////////
    public static void ProcessOdbCommand(PinOdb2Command Data) {
    }

    public static void ProcessOdbData(PinOdb2Data Data) {
        if (Data.DataType == ODB_BASE_CONNECTOR) {
            ODBManager.ReceiveODBSourceInfo(Data);
        } else {
            Processors.values().stream().forEach((DP) -> {
                DP.Processor.ProcessPIN(Data);
            });
        }

    }

    ///////////////////
    //RECEIVE LED Data
    ///////////////////
    public static void ProcessLcdCommand(PinLedCommand Command) {

    }

    public static void ProcessLcdData(PinLedData Data) {

        switch (Data.DataType) {
            case DISPLAY_KKSYS_DISPLAY_STATE:
                // UpdateDisplayState(Data.DisplayState);
                break;
        }
    }
}
