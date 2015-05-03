/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;

/**
 *
 * @author sayma_000
 * 
 * 
 */
public abstract class ODBManager {
    
    static boolean ODBConnected=false;
    static boolean ODBError=false;
    
    public static void ConnectODBSource()
    {
        DisplayManager.ODB_SendPluginMessageCommand(ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_ADAPTER_CONNECT, ODBConstants.KK_ODB_DATAPACKET.ODB_OTHERCMD, null,null);
    }
    
    public static void DisconnectODBSource()
    {
    
    }
    public static void ReceiveODBSourceInfo(PinOdb2Data Data)
    {
        if (!ODBConnected & Data.AdapterInfo.OdbAdapterConnected)
            DisplayManager.ChangeDisplayPage("MAIN");
        else if (Data.AdapterInfo.OdbAdapterError)
            DisplayManager.ChangeDisplayPage("ERROR");
            
        //
        ODBConnected=Data.AdapterInfo.OdbAdapterConnected;
        
    }
    
    //
    public static void ChangePage(String PrevPageID, String NewPageID)
    {
        DisplayManager.Processors.values().stream().forEach((DP) -> {
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
