/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_ELM327_BASIC_ODB2;


/**
 *
 * @author blinov_is
 *
 * Creating default config
 *
 *
 */
public abstract class kk_DefaultConfig {
    public static void MakeDefaultConfig() {
       
        try {
            DataDisplayConfig DefConf=GetDefaultconfig();
            
            Gson gson=new Gson();
            
            String Res=gson.toJson(DefConf);
            
            FileWriter fw;
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + "/"+SettingsManager.DATADISPLAY_CONF);
            fw.write(Res);
            fw.flush();
            fw.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(kk_DefaultConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
    }

    private static DataDisplayConfig GetDefaultconfig() {
       DataDisplayConfig DefConfig;
       DefConfig=new DataDisplayConfig();
        
       DefConfig.Pages=new DisplayPage[3];
       
       
       DefConfig.Pages[0]=new DisplayPage("MAIN");
       DefConfig.Pages[1]=new DisplayPage("DETAIL");
       DefConfig.Pages[2]=new DisplayPage("WAIT");
       DefConfig.Pages[2].ActivateOnLoad=true;          //this is default page
       
       DataProcessor DP;
       
       DP = new DataProcessor();
       DP.ProcessorType=PROC_ELM327_BASIC_ODB2;
       DP.TargetPages=new String[3];
       DP.TargetPages[0]="MAIN";
       DP.TargetPages[1]="DETAIL";
       DP.TargetPages[2]="WAIT";
       
       DefConfig.Processors=new DataProcessor[1];
       DefConfig.Processors[0]=DP;
       
       return DefConfig;
    }
    
}
