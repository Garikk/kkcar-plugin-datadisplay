/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay;

import kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_TYPE;
import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.constants.PluginConsts;

/**
 *
 * @author blinov_is
 */
public final class DataProcessorInfo  {
    public static PluginInfo GetPluginInfo()
    {
        PluginInfo Ret=new PluginInfo();
        Ret.PluginUUID="b5b50412-c02a-4674-a112-ddc5805ea4e5";
        Ret.PluginName="KKDataDisplay";
        Ret.PluginDescription="Data processor for display";
        Ret.PluginType = KK_PLUGIN_TYPE.PLUGIN_PROCESSOR;
        Ret.PluginVersion=1;
        Ret.Enabled=true;
        Ret.ReceivePins = GetMyReceivePinInfo();
        Ret.TransmitPins = GetMyTransmitPinInfo();
        return Ret;
    }
    
    
    private static String[] GetMyReceivePinInfo(){
    
        String[] Ret=new String[1];
    
        //Ret[0]=PluginConsts.KK_PLUGIN_PIN_IN_DEF_SELFTEST;
        Ret[0]=PluginConsts.KK_PLUGIN_PIN_S_DEF_SELFTEST;
        
        return Ret;
    }
    private static String[] GetMyTransmitPinInfo(){
    
        String[] Ret=new String[1];
    
        //Ret[0]=PluginConsts.KK_PLUGIN_PIN_IN_DEF_SELFTEST;
        Ret[0]=PluginConsts.KK_PLUGIN_PIN_S_DEF_SELFTEST;
        
        return Ret;
    }
    
}
