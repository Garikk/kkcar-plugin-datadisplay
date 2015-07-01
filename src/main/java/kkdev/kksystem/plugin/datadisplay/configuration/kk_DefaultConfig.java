/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;


import java.util.Hashtable;
import java.util.Map;
import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.PID_05_COLIANT_TEMP;
import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.PID_0C_ENGINE_RPM;
import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.PID_0D_VEHICLE_SPEED;
import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.PID_42_CONTROL_MODULE_VOLTAGE;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.PROC_BASIC_ODB2_DEBUG;


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
       
       DefConfig.Pages=new InfoPage[5];
       
       
       DefConfig.Pages[0]=new InfoPage("MAIN");
       DefConfig.Pages[1]=new InfoPage("DETAIL");
       DefConfig.Pages[2]=new InfoPage("CE_READER");
       DefConfig.Pages[3]=new InfoPage("WAIT");
       DefConfig.Pages[4]=new InfoPage("ERROR");
       
       DefConfig.Pages[0].PageGroup="DATA";//WARNING!! Only one Group supported by now!!!
       DefConfig.Pages[1].PageGroup="DATA";
       DefConfig.Pages[2].PageGroup=null;
       DefConfig.Pages[3].PageGroup=null;
       DefConfig.Pages[4].PageGroup=null;
       DefConfig.Pages[0].IsDefaultPage=false;         
       DefConfig.Pages[1].IsDefaultPage=false;         
       DefConfig.Pages[2].IsDefaultPage=true;          
       DefConfig.Pages[3].IsDefaultPage=false;         
       DefConfig.Pages[4].IsDefaultPage=false;         
       
       
       DefConfig.Pages[0].Parameters=new Hashtable<>();
       DefConfig.Pages[0].Parameters.put("SPD", PID_0D_VEHICLE_SPEED);
       DefConfig.Pages[0].Parameters.put("TMP", PID_05_COLIANT_TEMP);
       DefConfig.Pages[1].Parameters=new Hashtable<>();
       DefConfig.Pages[1].Parameters.put("SPD", PID_0D_VEHICLE_SPEED);
       DefConfig.Pages[1].Parameters.put("TMP", PID_05_COLIANT_TEMP);
       DefConfig.Pages[1].Parameters.put("VOLTAGE", PID_42_CONTROL_MODULE_VOLTAGE);
       DefConfig.Pages[1].Parameters.put("RPM", PID_0C_ENGINE_RPM);
       
       DataProcessor DP;
       
       DP = new DataProcessor();
       DP.ProcessorType=PROC_BASIC_ODB2_DEBUG;//PROC_BASIC_ODB2;
       DP.TargetPages=new String[4];
       DP.TargetPages[0]="MAIN";
       DP.TargetPages[1]="DETAIL";
       DP.TargetPages[2]="WAIT";
       DP.TargetPages[3]="ERROR";
       DP.TargetPages[4]="CE_READER";
       DefConfig.Processors=new DataProcessor[1];
       DefConfig.Processors[0]=DP;
       
       return DefConfig;
    }
    
}
