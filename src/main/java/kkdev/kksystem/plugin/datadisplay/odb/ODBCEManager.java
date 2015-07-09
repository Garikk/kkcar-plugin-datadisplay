/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.odb;

import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.IMenuMakerItemSelected;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import static kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_DATATYPE.ODB_DIAG_CE_ERRORS;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBCEManager implements IProcessorConnector {

    MenuMaker MMaker;

    public ODBCEManager() {
        MMaker = new MenuMaker(Global.DM.CurrentFeature, "CE_READER", Global.DM.Connector, MenuItemExec);

    }

    private IMenuMakerItemSelected MenuItemExec = new IMenuMakerItemSelected() {

        @Override
        public void SelectedItem(String ItemCMD) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    @Override
    public void Activate() {
        Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATAPACKET.ODB_CE_ERRORS, null, null);
        MMaker.ShowMenu();
    }

    @Override
    public void Deactivate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ProcessODBPIN(PinOdb2Data PMessage) {
        if (PMessage.DataType != ODB_DIAG_CE_ERRORS) {
            return;
        }
        
        List<String> Codes=new ArrayList<>();
        
        for (Integer Key:PMessage.ODBData.GetCEError().keySet())
        {
            for (Byte Val:PMessage.ODBData.GetCEError().get(Key))
            {
                Codes.add("ERR_" + Key.toString()+Val.toString());
            }
        }
        
        MKMenuItem[] MenuItems=new MKMenuItem[Codes.size()+1];
        int i=0;
        for (String Code:Codes)
        {
            MenuItems[i]=new MKMenuItem();
            MenuItems[i].DisplayName=Code;
            MenuItems[i].ItemCommand="";
            i++;
        }
            MenuItems[i]=new MKMenuItem();
            MenuItems[i].DisplayName="Clear errors";
            MenuItems[i].ItemCommand="";
        //
            MMaker.AddMenuItems(MenuItems);
            //
    }

    @Override
    public void ProcessControlPIN(PinControlData ControlData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
