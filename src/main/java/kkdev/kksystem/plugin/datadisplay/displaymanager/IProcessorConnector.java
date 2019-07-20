/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import kkdev.kksystem.base.classes.controls.PinDataControl;
import kkdev.kksystem.base.classes.odb2.PinDataOdb2;


/**
 *
 * @author blinov_is
 */
public interface IProcessorConnector {
    
    void Activate(String TargetPage);
    void Deactivate();
    void ProcessODBPIN(PinDataOdb2 PMessage);
    void ProcessControlPIN(PinDataControl ControlData);
    String GetActivePage();
            
}
