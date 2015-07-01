/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import java.util.HashMap;
import java.util.LinkedList;
import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_COMMAND;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_DATA;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import static kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_DATATYPE.ODB_BASE_CONNECTOR;
import kkdev.kksystem.base.classes.odb2.PinOdb2Command;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerDataProcessor;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.configuration.InfoPage;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.processors.odb.ODBDataDisplay;

/**
 *
 * @author blinov_is
 */
public class DisplayManager extends PluginManagerDataProcessor {

    HashMap<String, DataProcessor> Processors;
    String CurrentPage;
    LinkedList<String> MainDisplayPages;
    int CurrentMainDisplayPage;

    public void InitDisplayManager(KKPlugin Conn) {
        this.Connector = Conn;
        //
        PluginSettings.InitSettings();
        //
        this.CurrentFeature=PluginSettings.MainConfiguration.FeatureID;
        //
        InitDataProcessors();
    }

    private void InitDataProcessors() {
        Processors = new HashMap<>();

        for (DataProcessor DP : PluginSettings.MainConfiguration.Processors) {
            if (DP.ProcessorType == DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2) {
                DP.Processor = new ODBDataDisplay();
            }
        }
    }

    public void Start() {
        MainDisplayPages=new LinkedList<>();
        //Init Pages
        for (InfoPage DP : PluginSettings.MainConfiguration.Pages) {
            InitDisplayPage(DP);
            //
            //WARNING!! Only one Group supported by now!!!
            if (DP.PageGroup!=null)
                MainDisplayPages.add(DP.PageName);
            
            //CHANGE THIS !!!!!
            for (int i=0;i<MainDisplayPages.size();i++)
            {
                if (MainDisplayPages.get(i)=="MAIN")
                    CurrentMainDisplayPage=i;
            }
            //CHANGE THIS !!!!!
        }
        //Connect ODB Adapter
        ODBManager.ConnectODBSource();
    }

    private void InitDisplayPage(InfoPage Page) {
        //Local
        if (Page.IsDefaultPage) {
            CurrentPage = Page.PageName;
        }
        //Send init to Display plugin
        //Init main page
        DISPLAY_InitPage(this.CurrentFeature,Page.PageName);
        // Set page to active
        if (Page.IsDefaultPage) {
            CurrentPage = Page.PageName;
             DISPLAY_ActivatePage(this.CurrentFeature,Page.PageName);
        }

    }

    public void ShowMainPages()
    {
        MainDisplayPages.get(CurrentMainDisplayPage);
    }
    private void ChangePageNext()
    {
        if (CurrentMainDisplayPage==MainDisplayPages.size())
            CurrentMainDisplayPage=0;
        else
            CurrentMainDisplayPage++;
        
        ChangeDisplayPage(MainDisplayPages.get(CurrentMainDisplayPage));
    }
    private void ChangePageBack()
    {
        if (CurrentMainDisplayPage==0)
            CurrentMainDisplayPage=MainDisplayPages.size();
        else
            CurrentMainDisplayPage--;
        
        ChangeDisplayPage(MainDisplayPages.get(CurrentMainDisplayPage));
    }
    ///
    ///
    ///
    public void ChangeDisplayPage(String NewPage) {
        if (CurrentPage.equals(NewPage)) {
            return;
        }
        //
        ODBManager.ChangePage(CurrentPage, NewPage);
        CurrentPage = NewPage;

        DISPLAY_ActivatePage(CurrentFeature,CurrentPage);
    }

    public void SendPageData(String[] Keys, String[] Values)
    {
        PinLedData PLD=new PinLedData();
        PLD.TargetPage=CurrentPage;
        PLD.DataType=KK_DISPLAY_DATA.DISPLAY_KKSYS_TEXT_UPDATE_FRAME;
        PLD.OnFrame_DataKeys=Keys;
        PLD.OnFrame_DataValues=Values;
        
        this.DISPLAY_SendPluginMessageData(CurrentFeature, PLD);
    }
    
    ////

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
                ChangePageBack();
                break;
            case PinControlData.DEF_BTN_DOWN:
                ChangePageNext();
                break;
            case PinControlData.DEF_BTN_ENTER:
                //CHANGE THIS TO NORMAL!!
                if (CurrentPage=="DETAIL" | CurrentPage=="MAIN")
                    ChangeDisplayPage("CE_READER");
                break;
                //CHANGE THIS TO NORMAL!!!
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