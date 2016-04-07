/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;


import static kkdev.kksystem.base.classes.odb2.ODB2_SAE_J1979_PID_MODE_1.*;
import kkdev.kksystem.base.classes.odb2.tools.odbdecoder.ODBSimpleData;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_GFX1;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_GFX2;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.*;



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
       DefConfig.UIContexts=new String[3]; //2 displays in multimedia head + Debug display
       DefConfig.UIContexts[0]=KK_BASE_UICONTEXT_DEFAULT;
       DefConfig.UIContexts[1]=KK_BASE_UICONTEXT_GFX1;
       DefConfig.UIContexts[2]=KK_BASE_UICONTEXT_GFX2;
       
       DefConfig.UseDoubleDisplays=true;
       DefConfig.PrimaryUIContext=KK_BASE_UICONTEXT_GFX1;
       DefConfig.SecondaryUIContext=KK_BASE_UICONTEXT_GFX1;
       
   
       DataProcessor DP;
       DefConfig.Processors=new DataProcessor[4];
       
       DP = new DataProcessor();
       DP.ProcessorName="ODB_MAIN";
       DP.ProcessorType=PROC_BASIC_ODB2_DISPLAY;
       DP.Pages=new InfoPage[2];
       DP.Pages[0]=new InfoPage("MAIN");
       DP.Pages[0].PageCMD="CHPROCESSOR CE_READER";
       DP.Pages[0].DiagPIDs=ODBSimpleData.GetSimpleDiagRequest();
     
       DP.Pages[1]=new InfoPage("DETAIL");
       DP.Pages[1].PageCMD="CHPROCESSOR CE_READER";
       DP.Pages[1].DiagPIDs=ODBSimpleData.GetExtendedDiagRequest();

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
       DP.ProcessorType=PROC_BASIC_ODB2_WAIT;
       DP.Pages=new InfoPage[1];
       DP.Pages[0]=new InfoPage("WAIT");
       DP.Pages[0].PageCMD="";
       //
       DefConfig.Processors[3]=DP;
       return DefConfig;
    }
    
}
