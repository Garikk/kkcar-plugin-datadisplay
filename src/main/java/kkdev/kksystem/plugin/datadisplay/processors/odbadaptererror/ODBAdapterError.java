/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.processors.odbadaptererror;

import kkdev.kksystem.plugin.datadisplay.processors.odb.*;
import kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.plugin.datadisplay.displaymanager.DisplayManager;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBAdapterError implements IProcessorConnector {

    DisplayManager Connector;
    public final String PAGE_ERROR = "ERROR";

    @Override
    public void Activate(String PageName) {
        switch (PageName) {
            case PAGE_ERROR:
                break;

        }
    }

    @Override
    public void Deactivate(String PageName) {
       
    }

    @Override
    public void ProcessPIN(PinOdb2Data PMessage) {
        
    }
    
   

}
