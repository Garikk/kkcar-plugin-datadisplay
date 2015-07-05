/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import java.util.HashMap;
import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.classes.odb2.PinOdb2Command;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerDataProcessor;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.odb.ODBAdapterError;
import kkdev.kksystem.plugin.datadisplay.odb.ODBCEManager;
import kkdev.kksystem.plugin.datadisplay.odb.ODBDataDisplay;

/**
 *
 * @author blinov_is
 */
public class DisplayManager extends PluginManagerDataProcessor {
    
    boolean InfoPagesNowActive=false;
    HashMap<String, DataProcessor> Processors;
    String CurrentProcessor;

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
            if (DP.ProcessorType == DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2_DISPLAY) {
                DP.Processor = new ODBDataDisplay(DP);
            }
            else if (DP.ProcessorType == DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2_CEREADER) {
                DP.Processor = new ODBCEManager();
            }
            else if (DP.ProcessorType == DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2_ERROR) {
                DP.Processor = new ODBAdapterError(DP);
            }
            Processors.put(DP.ProcessorName, DP);
        }
    }

    public void Start() {

    }

    public void ChangeDataProcessor(String DataProcessor)
    {
        Processors.get(CurrentProcessor).Processor.Deactivate();
        CurrentProcessor=DataProcessor;
        Processors.get(CurrentProcessor).Processor.Activate();
    }
    ////

    ///////////////////
    ///////////////////
    public void RecivePin(PluginMessage Msg) {
        switch (Msg.PinName) {
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
            Processors.values().stream().forEach((DP) -> {
                DP.Processor.ProcessODBPIN(Data);
            });
    }
    ///////////////////
    //RECEIVE Control Data
    ///////////////////

    private void ProcessControlData(PinControlData Data) {
        Processors.get(CurrentProcessor).Processor.ProcessControlPIN(Data);
    }
    
    
    public void ProcessLcdData(PinLedData Data) {

        switch (Data.DataType) {
            case DISPLAY_KKSYS_DISPLAY_STATE:
                // UpdateDisplayState(Data.DisplayState);
                break;
        }
    }
}