/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;


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
    public static DataDisplayConfig MakeDefaultConfig() {
       DataDisplayConfig DefConfig;
       DefConfig=new DataDisplayConfig();
        
       DefConfig.Pages=new DisplayPage[4];
       
       
       DefConfig.Pages[0]=new DisplayPage("MAIN");
       DefConfig.Pages[1]=new DisplayPage("DETAIL");
       DefConfig.Pages[2]=new DisplayPage("WAIT");
       DefConfig.Pages[3]=new DisplayPage("ERROR");
       DefConfig.Pages[0].IsDefaultPage=false;          //this is default page
       DefConfig.Pages[1].IsDefaultPage=false;          //this is default page
       DefConfig.Pages[2].IsDefaultPage=true;          //this is default page
       DefConfig.Pages[3].IsDefaultPage=false;          //this is default page
       
       DataProcessor DP;
       
       DP = new DataProcessor();
       DP.ProcessorType=PROC_ELM327_BASIC_ODB2;
       DP.TargetPages=new String[4];
       DP.TargetPages[0]="MAIN";
       DP.TargetPages[1]="DETAIL";
       DP.TargetPages[2]="WAIT";
       DP.TargetPages[3]="ERROR";
       DefConfig.Processors=new DataProcessor[1];
       DefConfig.Processors[0]=DP;
       
       return DefConfig;
    }
    
}
