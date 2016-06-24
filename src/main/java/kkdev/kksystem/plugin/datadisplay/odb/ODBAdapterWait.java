/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.odb;

import kkdev.kksystem.base.classes.controls.PinDataControl;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2ConnectorInfo;
import kkdev.kksystem.base.classes.odb2.PinDataOdb2;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBAdapterWait implements IProcessorConnector {

    final String PAGE_WAIT = "KKDIAG_WAIT";

    String CurrTargetProcessor;
    String ActivePage;

    @Override
    public void Activate(String TargetProc) {
        ActivePage = PAGE_WAIT;
    
        Global.DM.DISPLAY_ActivatePage(Global.DM.currentFeature.get(PluginSettings.MainConfiguration.PrimaryUIContext),PluginSettings.MainConfiguration.PrimaryUIContext, ActivePage);
        //
        CurrTargetProcessor = TargetProc;
        //
        if (CurrTargetProcessor.equals(Global.DM.DP_MAIN)) {
            //Global.DM.ODBProcessor.ODB_SendPluginMessageCommand(Global.DM.currentFeature.get(PluginSettings.MainConfiguration.PrimaryUIContext), ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_ADAPTER_CONNECT, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_CMD_OTHERCMD, null, null);
                Global.DM.ODBProcessor.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_ADAPTER_CONNECT, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_CMD_OTHERCMD, null, null);
        } else if (CurrTargetProcessor.equals(Global.DM.DP_CE_ERROR)) {
            //Global.DM.ODBProcessor.ODB_SendPluginMessageCommand(Global.DM.currentFeature.get(PluginSettings.MainConfiguration.PrimaryUIContext), ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_GETINFO_CE_ERRORS, null, null);
            Global.DM.ODBProcessor.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_GETINFO_CE_ERRORS, null, null);
        }
    }

    @Override
    public void Deactivate() {

    }

    @Override
    public void ProcessODBPIN(PinDataOdb2 PMessage) {
        if (CurrTargetProcessor.equals(Global.DM.DP_MAIN)) {
            if (PMessage.Odb2DataType == ODBConstants.KK_ODB_DATATYPE.ODB_BASE_CONNECTOR) {
                if (PMessage.AdapterInfo.odbAdapterState == PinOdb2ConnectorInfo.ODB_State.ODB_CONNECTOR_READY) {
                    Global.DM.ChangeDataProcessor(Global.DM.DP_MAIN, null);
                } else {
                    Global.DM.ChangeDataProcessor(Global.DM.DP_ERROR, null);
                }
            }
        } else if (CurrTargetProcessor.equals(Global.DM.DP_CE_ERROR)) {
            {
                if (PMessage.Odb2DataType == ODBConstants.KK_ODB_DATATYPE.ODB_DIAG_CE_ERRORS) {
                    if (PMessage.AdapterInfo.odbAdapterState == PinOdb2ConnectorInfo.ODB_State.ODB_CONNECTOR_READY) {
                        Global.DM.ChangeDataProcessor(Global.DM.DP_CE_ERROR, null);
                    } else {
                        Global.DM.ChangeDataProcessor(Global.DM.DP_ERROR, null);
                    }
                }

            }
        }
    }

    @Override
    public void ProcessControlPIN(PinDataControl ControlData) {
       // System.out.println("CTRL " + ControlData.ControlID);
    }

    @Override
    public String GetActivePage() {
        return ActivePage;
    }

}
