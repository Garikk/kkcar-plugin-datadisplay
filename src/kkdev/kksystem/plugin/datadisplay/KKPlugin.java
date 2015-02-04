/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay;

import kkdev.kksystem.plugin.datadisplay.processors.odb.ODBDataDisplay;
import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import kkdev.kksystem.plugin.datadisplay.displaymanager.DisplayManager;

/**     
 *
 * @author blinov_is
 */
public class KKPlugin implements IPluginKKConnector   {

    IPluginBaseInterface Connector;
    DisplayManager DDisplay;
   
    @Override
    public PluginInfo GetPluginInfo() {
         return DataProcessorInfo.GetPluginInfo();
    }

    @Override
    public void PluginInit(IPluginBaseInterface BaseConnector) {
       Connector=BaseConnector;
    }

    @Override
    public void PluginStart() {

    }

    @Override
    public void PluginStop() {
        //TODO make destruct
        DDisplay=null;
    }

    @Override
    public PluginMessage ExecutePin(PluginMessage Pin) {
        if (DDisplay!=null)
            DDisplay.RecivePin(Pin);
        //
        return null;
    }

    

   
    
}

