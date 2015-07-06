/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.odb;

import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2ConnectorInfo;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBAdapterWait implements IProcessorConnector {

    final String PAGE_WAIT="WAIT";
    
    @Override
    public void Activate() {
       Global.DM.DISPLAY_ActivatePage(Global.DM.CurrentFeature,PAGE_WAIT);
       Global.DM.ODB_SendPluginMessageCommand(Global.DM.CurrentFeature, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_ADAPTER_CONNECT, ODBConstants.KK_ODB_DATAPACKET.ODB_OTHERCMD, null, null);
    }

    @Override
    public void Deactivate() {
    
    }

    @Override
    public void ProcessODBPIN(PinOdb2Data PMessage) {
        if (PMessage.DataType==ODBConstants.KK_ODB_DATATYPE.ODB_BASE_CONNECTOR)
        {
            if (PMessage.AdapterInfo.OdbAdapterState==PinOdb2ConnectorInfo.ODB_State.ODB_CONNECTOR_READY)
            {
                Global.DM.ChangeDataProcessor(Global.DM.DP_MAIN);
            }
            else
            {
                Global.DM.ChangeDataProcessor(Global.DM.DP_ERROR);
            }
        }
    }

    @Override
    public void ProcessControlPIN(PinControlData ControlData) {

    }

}
