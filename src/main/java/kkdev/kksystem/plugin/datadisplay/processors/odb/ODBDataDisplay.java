/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.processors.odb;

import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import kkdev.kksystem.plugin.datadisplay.KKPlugin;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBDataDisplay implements IProcessorConnector {
    KKPlugin Connector;
    boolean ODBFound=false;
    boolean DisplayFound=false;

    @Override
    public void Init(KKPlugin PConnector) {
        Connector=PConnector;
    }
    @Override
    public void Connect() {
       //
        //Connector.
       //
    }

    @Override
    public void Disconnect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ExecPIN(PluginMessage PMessage) {
       
    }
    


}
