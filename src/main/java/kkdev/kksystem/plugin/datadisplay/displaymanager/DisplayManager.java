/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import java.util.HashMap;
import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.classes.odb2.PinOdb2Command;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.plugins.simple.managers.*;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.base.constants.SystemConsts;
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
public class DisplayManager extends PluginManagerDataProcessor   {

    boolean InfoPagesNowActive = false;
    HashMap<String, DataProcessor> Processors;
    String CurrentProcessor;
    boolean MultiContextMode;
    public PluginManagerODB ODBProcessor;
    KKPlugin LocalConnector;
    
    public final String DP_WAIT = "WAIT";
    public final String DP_MAIN = "ODB_MAIN";
    public final String DP_ERROR = "ERROR";
    public final String DP_CE_ERROR = "CE_READER";

    public void InitDisplayManager(KKPlugin Conn) {
        ODBProcessor=new PluginManagerODB();
        this.connector = Conn;
        ODBProcessor.connector=Conn;
        LocalConnector=Conn;

        for (String UICtx:PluginSettings.MainConfiguration.UIContexts)
        {
            this.currentFeature.put(UICtx,PluginSettings.MainConfiguration.FeatureID);
        }
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
                        DP.Processor = new ODBCEManager(LocalConnector.GetUtils());
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
                break;
            case PluginConsts.KK_PLUGIN_BASE_LED_COMMAND:
                ProcessLcdCommand((PinLedCommand)Msg.PinData);
                break;
        }
    }

    ///////////////////
    //RECEIVE ODB Data
    ///////////////////
    public void ProcessOdbCommand(PinOdb2Command Data) {

    }

    public void ProcessOdbData(PinOdb2Data Data) {
        //       System.out.println("ODB ANS" +Data.featureID + " " + this.currentFeature);
       // if (Data.featureID.equals(this.currentFeature) | Data.featureID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID)) {
            Processors.values().stream().forEach((DP) -> {
                DP.Processor.ProcessODBPIN(Data);
            });
      //  }
    }
    ///////////////////
    //RECEIVE Control Data
    ///////////////////

    private void ProcessControlData(PinControlData Data) {
       
        if (Data.featureID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID)) {
            if (Data.UIContextID.equals(SystemConsts.KK_BASE_UICONTEXT_DEFAULT_MULTI)){
                   Data.featureID = this.currentFeature.get(PluginSettings.MainConfiguration.PrimaryUIContext);
                }
            else{
                Data.featureID = this.currentFeature.get(Data.UIContextID);
            }
            
        }
        //
        //System.out.println("[DD] CTL " + Data.contextID);
        if (Data.featureID.equals(this.currentFeature.get(Data.UIContextID))) {
            Processors.get(CurrentProcessor).Processor.ProcessControlPIN(Data);
        }
    }

    public void ProcessLcdData(PinLedData Data) {

        switch (Data.ledDataType) {
            case DISPLAY_KKSYS_DISPLAY_STATE:
                break;
        }
    }
    ///////////////////
    //RECEIVE Led Commands
    ///////////////////
    public void ProcessLcdCommand(PinLedCommand Cmd) {

        switch (Cmd.command) {
            case DISPLAY_KKSYS_GETACTIVEPAGE:
                //PinLedData PLD=new PinLedData();
                //PLD.ledDataType=DisplayConstants.KK_DISPLAY_DATA.DISPLAY_KKSYS_ACTIVE_PAGE;
                //PLD.targetPage=Processors.get(CurrentProcessor).Processor.GetActivePage();
                //PLD.featureID=currentFeature.get(Cmd.ChangeFeatureID);
                //this.DISPLAY_SendPluginMessageData(currentFeature,PLD);
                break;
        }
    }
}
