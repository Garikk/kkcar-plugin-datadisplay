/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;


import java.util.Hashtable;
import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.PID_05_COLIANT_TEMP;
import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.PID_0C_ENGINE_RPM;
import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.PID_0D_VEHICLE_SPEED;
import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.PID_42_CONTROL_MODULE_VOLTAGE;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2_DEBUG;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2_DISPLAY;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2_CEREADER;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2_ERROR;


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
       
       DefConfig.FeatureID=KK_BASE_FEATURES_ODB_DIAG_UID;
       /*
       DefConfig.Pages=new InfoPage[5];
       
       
       DefConfig.Pages[0]=new InfoPage("MAIN");
       DefConfig.Pages[1]=new InfoPage("DETAIL");
       DefConfig.Pages[2]=new InfoPage("CE_READER");
       DefConfig.Pages[3]=new InfoPage("WAIT");
       DefConfig.Pages[4]=new InfoPage("ERROR");
       
       DefConfig.Pages[0].PageCMD="CHPROCESSOR CE_READER";
       DefConfig.Pages[1].PageCMD="CHPROCESSOR CE_READER";
       DefConfig.Pages[2].PageCMD="CHPROCESSOR MAINPAGE";
       DefConfig.Pages[3].PageCMD="";
       DefConfig.Pages[4].PageCMD="EXEC RECONNECT";
       DefConfig.Pages[0].IsDefaultPage=false;         
       DefConfig.Pages[1].IsDefaultPage=false;         
       DefConfig.Pages[2].IsDefaultPage=true;          
       DefConfig.Pages[3].IsDefaultPage=false;         
       DefConfig.Pages[4].IsDefaultPage=false;         
       */
       DataProcessor DP;
       
       //DP = new DataProcessor();
       //DP.ProcessorName="ODB_DBG";
       //DP.ProcessorType=PROC_BASIC_ODB2_DEBUG;//PROC_BASIC_ODB2;
       //DP.Pages=new Pages[]
       

       DefConfig.Processors=new DataProcessor[3];
       
       DP = new DataProcessor();
       DP.ProcessorName="ODB_MAIN";
       DP.ProcessorType=PROC_BASIC_ODB2_DISPLAY;
       DP.Pages=new InfoPage[2];
       DP.Pages[0]=new InfoPage("MAIN");
       DP.Pages[0].PageCMD="CHPROCESSOR CE_READER";
       DP.Pages[1]=new InfoPage("DETAIL");
       DP.Pages[1].PageCMD="CHPROCESSOR CE_READER";
       //
       DefConfig.Processors[0]=DP;
       //
       DP = new DataProcessor();
       DP.ProcessorName="CE_READER";
       DP.ProcessorType=PROC_BASIC_ODB2_CEREADER;
       DP.Pages=new InfoPage[1];
       DP.Pages[0]=new InfoPage("CE_READER");
       DP.Pages[0].PageCMD="CHPROCESSOR ODB_MAIN";
       //
       DefConfig.Processors[1]=DP;
       //
       
       DP = new DataProcessor();
       DP.ProcessorName="ERROR";
       DP.ProcessorType=PROC_BASIC_ODB2_ERROR;
       DP.Pages=new InfoPage[1];
       DP.Pages[0]=new InfoPage("CE_READER");
       DP.Pages[0].PageCMD="CHPROCESSOR ODB_MAIN";
       //
       DefConfig.Processors[2]=DP;
       //
       DP = new DataProcessor();
       DP.ProcessorName="WAIT";
       DP.ProcessorType=PROC_BASIC_ODB2_CEREADER;
       DP.Pages=new InfoPage[1];
       DP.Pages[0]=new InfoPage("WAIT");
       DP.Pages[0].PageCMD="";
       //
       DefConfig.Processors[3]=DP;
       return DefConfig;
    }
    
}
