/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import java.util.HashMap;
import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.classes.odb2.PinOdb2Command;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerDataProcessor;
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.odb.ODBAdapterError;
import kkdev.kksystem.plugin.datadisplay.odb.ODBAdapterWait;
import kkdev.kksystem.plugin.datadisplay.odb.ODBCEManager;
import kkdev.kksystem.plugin.datadisplay.odb.ODBDataDisplay;

/**
 *
 * @author blinov_is
 */
public class DisplayManager extends PluginManagerDataProcessor {

    boolean InfoPagesNowActive = false;
    HashMap<String, DataProcessor> Processors;
    String CurrentProcessor;

    public final String DP_WAIT = "WAIT";
    public final String DP_MAIN = "ODB_MAIN";
    public final String DP_ERROR = "ERROR";
    public final String DP_CE_ERROR = "CE_READER";

    public void InitDisplayManager(KKPlugin Conn) {
        this.Connector = Conn;

        //
        this.CurrentFeature = PluginSettings.MainConfiguration.FeatureID;
        //
        InitDataProcessors();
    }

    private void InitDataProcessors() {
        Processors = new HashMap<>();

        for (DataProcessor DP : PluginSettings.MainConfiguration.Processors) {
            if (null != DP.ProcessorType) {
                switch (DP.ProcessorType) {
                    case PROC_BASIC_ODB2_DISPLAY:
                        DP.Processor = new ODBDataDisplay(DP);
                        break;
                    case PROC_BASIC_ODB2_CEREADER:
                        DP.Processor = new ODBCEManager();
                        break;
                    case PROC_BASIC_ODB2_ERROR:
                        DP.Processor = new ODBAdapterError(DP);
                        break;
                    case PROC_BASIC_ODB2_WAIT:
                        DP.Processor = new ODBAdapterWait();
                        break;
                    default:
                        break;
                }
            }
            Processors.put(DP.ProcessorName, DP);
        }
    }

    public void Start() {
        ChangeDataProcessor(DP_WAIT, DP_MAIN);
    }

    public void ChangeDataProcessor(String DataProcessor, String TargetDataProcessor) {
        if (CurrentProcessor != null) {
            Processors.get(CurrentProcessor).Processor.Deactivate();
        }
        CurrentProcessor = DataProcessor;
        Processors.get(CurrentProcessor).Processor.Activate(TargetDataProcessor);
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
                ProcessControlData((PinControlData) Msg.PinData);
        }
    }

    ///////////////////
    //RECEIVE ODB Data
    ///////////////////
    public void ProcessOdbCommand(PinOdb2Command Data) {

    }

    public void ProcessOdbData(PinOdb2Data Data) {
        if (Data.FeatureID.equals(this.CurrentFeature)) {
            Processors.values().stream().forEach((DP) -> {
                DP.Processor.ProcessODBPIN(Data);
            });
        }
    }
    ///////////////////
    //RECEIVE Control Data
    ///////////////////

    private void ProcessControlData(PinControlData Data) {
        if (Data.FeatureID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID)) {
            Data.FeatureID = this.CurrentFeature;
        }
        //

        if (Data.FeatureID.equals(this.CurrentFeature)) {
            Processors.get(CurrentProcessor).Processor.ProcessControlPIN(Data);
        }
    }

    public void ProcessLcdData(PinLedData Data) {

        switch (Data.DataType) {
            case DISPLAY_KKSYS_DISPLAY_STATE:
                break;
        }
    }
}
