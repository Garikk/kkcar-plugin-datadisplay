/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import java.util.HashMap;
import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_COMMAND;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import static kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_DATATYPE.ODB_BASE_CONNECTOR;
import kkdev.kksystem.base.classes.odb2.PinOdb2Command;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerDataProcessor;
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.configuration.DisplayPage;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.processors.odb.ODBDataDisplay;

/**
 *
 * @author blinov_is
 */
public class DisplayManager extends PluginManagerDataProcessor {

    HashMap<String, DataProcessor> Processors;
    String CurrentPage;

    public void InitDisplayManager(KKPlugin Conn) {
        this.Connector = Conn;
        //
       // System.out.println("[DataDisplay][INIT] Data processor initialising");
        PluginSettings.InitSettings();
        //
       // System.out.println("[DataDisplay][PROC] Init Data processors");
        InitDataProcessors();
    }

    private void InitDataProcessors() {
        Processors = new HashMap<>();

        for (DataProcessor DP : PluginSettings.MainConfiguration.Processors) {
            if (DP.ProcessorType == DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_ELM327_BASIC_ODB2) {
                DP.Processor = new ODBDataDisplay();
            }
        }
    }

    public void Start() {
        //Init Pages
        for (DisplayPage DP : PluginSettings.MainConfiguration.Pages) {
            InitDisplayPage(DP);
        }
        //Connect ODB Adapter
        ODBManager.ConnectODBSource();
    }

    private void InitDisplayPage(DisplayPage Page) {
        //Local
        if (Page.IsDefaultPage) {
            CurrentPage = Page.PageName;
        }
        //Send init to Display plugin
        //Init main page
        DISPLAY_InitPage(KK_BASE_FEATURES_ODB_DIAG_UID,Page.PageName);
        // Set page to active
        if (Page.IsDefaultPage) {
            CurrentPage = Page.PageName;
             DISPLAY_ActivatePage(KK_BASE_FEATURES_ODB_DIAG_UID,Page.PageName);
        }

    }

    ///
    ///
    ///
    public void ChangeDisplayPage(String NewPage) {
        System.out.println("[ODB2][PAGE] Change to " + NewPage);
        if (CurrentPage.equals(NewPage)) {
            return;
        }
        //
        ODBManager.ChangePage(CurrentPage, NewPage);
        CurrentPage = NewPage;

        DISPLAY_SendPluginMessageCommand(KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_ACTIVATE,CurrentPage,null, null, null);
    }

    ////
    ///
    ///
    public void DISPLAY_SendPluginMessageCommand(KK_DISPLAY_COMMAND Command,String PageID, String[] DataStr, int[] DataInt, boolean[] DataBool) {
        DISPLAY_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, Command,PageID, DataStr, DataInt, DataBool);
    }

    ///////////////////
    ///////////////////
    public void RecivePin(PluginMessage Msg) {
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
            case PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA:
                ProcessControlData((PinControlData)Msg.PinData);
        }
    }

    ///////////////////
    //RECEIVE ODB Data
    ///////////////////
    public void ProcessOdbCommand(PinOdb2Command Data) {

    }

    public void ProcessOdbData(PinOdb2Data Data) {
        if (Data.DataType == ODB_BASE_CONNECTOR) {
            ODBManager.ReceiveODBSourceInfo(Data);
        } else {
            Processors.values().stream().forEach((DP) -> {
                DP.Processor.ProcessPIN(Data);
            });
        }

    }
    ///////////////////
    //RECEIVE Control Data
    ///////////////////

    private void ProcessControlData(PinControlData Data) {
        switch (Data.ControlID) {
            case PinControlData.DEF_BTN_UP:
                //      SysMenu.MenuSelectUp();
                break;
            case PinControlData.DEF_BTN_DOWN:
                //  SysMenu.MenuSelectDown();
                break;
            case PinControlData.DEF_BTN_ENTER:
                //   SysMenu.MenuExec();
                break;
            case PinControlData.DEF_BTN_BACK:
                break;

        }

    }

    ///////////////////
    //RECEIVE LED Data
    ///////////////////
    public void ProcessLcdCommand(PinLedCommand Command) {

    }

    public void ProcessLcdData(PinLedData Data) {

        switch (Data.DataType) {
            case DISPLAY_KKSYS_DISPLAY_STATE:
                // UpdateDisplayState(Data.DisplayState);
                break;
        }
    }
}