/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerBase;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import kkdev.kksystem.plugin.datadisplay.Global;

/**
 *
 * @author sayma_000
 * 
 * 
 */
public abstract class ODBManager extends PluginManagerBase {
    
    static boolean ODBConnected=false;
    static boolean ODBError=false;
    
    public static void ConnectODBSource()
    {
        Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID,ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_ADAPTER_CONNECT, ODBConstants.KK_ODB_DATAPACKET.ODB_OTHERCMD, null,null);
    }
    
    public static void DisconnectODBSource()
    {
    
    }
    public static void ReceiveODBSourceInfo(PinOdb2Data Data)
    {
        /*
        if (!ODBConnected & Data.AdapterInfo.OdbAdapterConnected)
            Global.DM.ChangeDisplayPage("MAIN");
        else if (Data.AdapterInfo.OdbAdapterError)
            Global.DM.ChangeDisplayPage("ERROR");
            
        //
        ODBConnected=Data.AdapterInfo.OdbAdapterConnected;
        */
    }
    
    //
    public static void ChangePage(String PrevPageID, String NewPageID)
    {
         Global.DM.Processors.values().stream().forEach((DP) -> {
            for (String Page:DP.TargetPages)
            {
                if (Page.equals(PrevPageID))
                    DP.Processor.Deactivate(PrevPageID);
                
                if (Page.equals(NewPageID))
                    DP.Processor.Activate(NewPageID);
            }
        });
    }
    
    
}
