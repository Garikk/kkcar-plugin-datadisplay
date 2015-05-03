/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.processors.odb;

import kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.plugin.datadisplay.displaymanager.DisplayManager;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBDataDisplay implements IProcessorConnector {

    DisplayManager Connector;
    public final String PAGE_MAIN = "MAIN";
    public final String PAGE_DETAIL = "DETAIL";
    public final String PAGE_WAIT = "WAIT";
    public final String PAGE_ERROR = "ERROR";

    @Override
    public void Activate(String PageName) {
        switch (PageName) {
            case PAGE_MAIN:
                DisplayManager.ODB_SendPluginMessageCommand(ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATAPACKET.ODB_SIMPLEDATA,null,null);
                break;
            case PAGE_DETAIL:
                DisplayManager.ODB_SendPluginMessageCommand(ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATAPACKET.ODB_EXTENDEDDATA,GetExtendedPIDs(),GetReadIntervals(GetExtendedPIDs()));
                break;
            case PAGE_WAIT:
                break;

        }
    }

    @Override
    public void Deactivate(String PageName) {
        switch (PageName) {
            case PAGE_MAIN:
                DisplayManager.ODB_SendPluginMessageCommand(ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO_STOP, ODBConstants.KK_ODB_DATAPACKET.ODB_SIMPLEDATA,null,null);
                break;
            case PAGE_DETAIL:
                DisplayManager.ODB_SendPluginMessageCommand(ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO_STOP, ODBConstants.KK_ODB_DATAPACKET.ODB_EXTENDEDDATA,GetExtendedPIDs(),null);
                break;
            case PAGE_WAIT:
                break;

        }
    }

    @Override
    public void ProcessPIN(PinOdb2Data PMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private int[] GetExtendedPIDs()
    {
        //TODO: Make load this values from config
        int[] Ret=new int[4];
        Ret[0]=ODB2_SAE_J1979_PID_MODE_1.PID_05_COLIANT_TEMP;
        Ret[1]=ODB2_SAE_J1979_PID_MODE_1.PID_42_CONTROL_MODULE_VOLTAGE;
        Ret[2]=ODB2_SAE_J1979_PID_MODE_1.PID_0D_VEHICLE_SPEED;
        Ret[3]=ODB2_SAE_J1979_PID_MODE_1.PID_0C_ENGINE_RPM;
        return Ret;
    }
    private int[] GetReadIntervals(int[] PIDs)
    {
        //Set 500msec read interval for all PIDs
        int[] Ret=new int[4];
        for (int i=0;i<=3;i++)
        {
            Ret[i]=500;
        }
        return Ret;
    }

}
