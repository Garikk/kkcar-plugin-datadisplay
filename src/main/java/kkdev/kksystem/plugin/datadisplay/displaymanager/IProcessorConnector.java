/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.displaymanager;

import kkdev.kksystem.base.classes.odb2.PinOdb2Data;


/**
 *
 * @author blinov_is
 */
public interface IProcessorConnector {
    
    public void Activate(String PageName);
    public void Deactivate(String PageName);
    public void ProcessPIN(PinOdb2Data PMessage);
            
}
