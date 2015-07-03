/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.processors.odb;

import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.IMenuMakerItemSelected;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBCEManager implements IProcessorConnector {
    
    MenuMaker MMaker;
    
    public ODBCEManager() {
        MMaker = new MenuMaker(Global.DM.CurrentFeature,"CE_READER", Global.DM.Connector,MenuItemExec);
    }
    
    private IMenuMakerItemSelected MenuItemExec=new IMenuMakerItemSelected(){

        @Override
        public void SelectedItem(String ItemCMD) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    @Override
    public void Activate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Deactivate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ProcessODBPIN(PinOdb2Data PMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ProcessControlPIN(PinControlData ControlData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
