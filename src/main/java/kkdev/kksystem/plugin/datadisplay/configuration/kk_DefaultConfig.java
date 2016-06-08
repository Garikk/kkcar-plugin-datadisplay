/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import kkdev.kksystem.base.classes.display.pages.DisplayPage;
import static kkdev.kksystem.base.classes.display.pages.PageConsts.KK_DISPLAY_PAGES_SIMPLEMENU_TXT_C1RX_PREFIX;
import kkdev.kksystem.base.classes.display.pages.UIFrameData;
import kkdev.kksystem.base.classes.display.pages.UIFramePack;
import kkdev.kksystem.base.classes.odb2.tools.odbdecoder.ODBSimpleData;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_GFX1;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_GFX2;
import kkdev.kksystem.base.interfaces.IKKControllerUtils;
import kkdev.kksystem.plugin.datadisplay.Global;
import static kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor.DATADISPLAY_DATAPROCESSORS.*;

/**
 *
 * @author blinov_is
 *
 * Creating default config
 *
 *
 */
public class kk_DefaultConfig {

    public static DataDisplayConfig MakeDefaultConfig() {
        DataDisplayConfig DefConfig;
        DefConfig = new DataDisplayConfig();

        DefConfig.FeatureID = KK_BASE_FEATURES_ODB_DIAG_UID;
        DefConfig.UIContexts = new String[3]; //2 displays in multimedia head + Debug display
        DefConfig.UIContexts[0] = KK_BASE_UICONTEXT_DEFAULT;
        DefConfig.UIContexts[1] = KK_BASE_UICONTEXT_GFX1;
        DefConfig.UIContexts[2] = KK_BASE_UICONTEXT_GFX2;

        DefConfig.PrimaryUIContext = KK_BASE_UICONTEXT_DEFAULT;//KK_BASE_UICONTEXT_GFX1;
        DefConfig.SecondaryUIContext = KK_BASE_UICONTEXT_GFX1;

        DataProcessor DP;
        DefConfig.Processors = new DataProcessor[4];

        DP = new DataProcessor();
        DP.ProcessorName = "ODB_MAIN";
        DP.ProcessorType = PROC_BASIC_ODB2_DISPLAY;
        DP.Pages = new InfoPage[2];
        DP.Pages[0] = new InfoPage("KKDIAG_DIAG");
        DP.Pages[0].PageCMD = "CHPROCESSOR CE_READER";
        DP.Pages[0].DiagPIDs = ODBSimpleData.GetSimpleDiagRequest();

        DP.Pages[1] = new InfoPage("KKDIAG_DIAG_2");
        DP.Pages[1].PageCMD = "CHPROCESSOR CE_READER";
        DP.Pages[1].DiagPIDs = ODBSimpleData.GetExtendedDiagRequest();

        //
        DefConfig.Processors[0] = DP;
        //
        DP = new DataProcessor();
        DP.ProcessorName = "CE_READER";
        DP.ProcessorType = PROC_BASIC_ODB2_CEREADER;
        DP.Pages = new InfoPage[1];
        DP.Pages[0] = new InfoPage(KK_DISPLAY_PAGES_SIMPLEMENU_TXT_C1RX_PREFIX);
        DP.Pages[0].PageCMD = "CHPROCESSOR ODB_MAIN";
        //
        DefConfig.Processors[1] = DP;
        //

        DP = new DataProcessor();
        DP.ProcessorName = "ERROR";
        DP.ProcessorType = PROC_BASIC_ODB2_ERROR;
        DP.Pages = new InfoPage[1];
        DP.Pages[0] = new InfoPage("KKDIAG_ERROR");
        DP.Pages[0].PageCMD = "CHPROCESSOR ODB_MAIN";
        //
        DefConfig.Processors[2] = DP;
        //
        DP = new DataProcessor();
        DP.ProcessorName = "WAIT";
        DP.ProcessorType = PROC_BASIC_ODB2_WAIT;
        DP.Pages = new InfoPage[1];
        DP.Pages[0] = new InfoPage("KKDIAG_WAIT");
        DP.Pages[0].PageCMD = "";
        //
        DefConfig.Processors[3] = DP;
        return DefConfig;
    }

    public static void AddDefaultSystemUIPages(IKKControllerUtils Utils) {
        DisplayPage DP;
        UIFramePack[] FramePack;
        //
        FramePack = GetFramePack();
        //
        DP = new DisplayPage();
        DP.dynamicElements = true;
        DP.features = new String[1];
        DP.features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.contexts = new String[1];
        DP.contexts[0] = SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.pageName = "KKDIAG_DIAG";
        DP.contexts = new String[1];
        DP.contexts[0]=KK_BASE_UICONTEXT_DEFAULT;
        DP.isDefaultPage = false;
        DP.isMultifeaturePage = true;
        DP.framesPack = FramePack[0];
        //
        Utils.DISPLAY_AddUIDisplayPage(DP);
        //
        DP = new DisplayPage();
        DP.dynamicElements = false;
        DP.features = new String[1];
        DP.features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.contexts = new String[1];
        DP.contexts[0] = SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.pageName = "KKDIAG_DIAG_2";
        DP.contexts = new String[1];
        DP.contexts[0]=KK_BASE_UICONTEXT_DEFAULT;
        DP.isDefaultPage = false;
        DP.isMultifeaturePage = true;
        DP.framesPack = FramePack[1];
        //
        Utils.DISPLAY_AddUIDisplayPage(DP);
        //
        DP = new DisplayPage();
        DP.dynamicElements = false;
        DP.features = new String[1];
        DP.features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.contexts = new String[1];
        DP.contexts[0] = SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.pageName = "KKDIAG_WAIT";
        DP.isDefaultPage = false;
        DP.isMultifeaturePage = true;
        DP.framesPack = FramePack[2];
        //

        Utils.DISPLAY_AddUIDisplayPage(DP);
        //
        DP = new DisplayPage();
        DP.dynamicElements = false;
        DP.features = new String[1];
        DP.features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.contexts = new String[1];
        DP.contexts[0] = SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.pageName = "KKDIAG_ERROR";
        DP.isDefaultPage = false;
        DP.isMultifeaturePage = true;
        DP.framesPack = FramePack[3];
        //
        Utils.DISPLAY_AddUIDisplayPage(DP);
        //
        //   DP = new DisplayPage();
        //DP.dynamicElements = false;
        //DP.features = new String[1];
        //DP.features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        //DP.pageName = "KKDIAG_CE_READER";
        //DP.isDefaultPage = false;
        //DP.isMultifeaturePage = true;
        //DP.framesPack = FramePack[1];
        //
        //Global.DM.BaseConnector.SystemUtilities().DISPLAY_AddUIDisplayPage(DP);

    }

    private static UIFramePack[] GetFramePack() {
        UIFramePack[] Ret = new UIFramePack[7];
        Ret[0] = new UIFramePack();
        Ret[0].name = "Diag display pages";
        Ret[0].packID = "";
        Ret[0].data = new UIFrameData[4];
        Ret[0].data[0] = new UIFrameData();
        Ret[0].data[1] = new UIFrameData();
        Ret[0].data[2] = new UIFrameData();
        Ret[0].data[3] = new UIFrameData();

        Ret[0].data[0].frameData = "Speed [SPD]\r\nTemp [TMP]\r\nTIME: [KK_PL_TIME] |";
        Ret[0].data[0].fontSize = 2;
        Ret[0].data[1].frameData = "Speed [SPD]\r\nTemp [TMP]\r\nTIME: [KK_PL_TIME] /";
        Ret[0].data[1].fontSize = 2;
        Ret[0].data[2].frameData = "Speed [SPD]\r\nTemp [TMP]\r\nTIME: [KK_PL_TIME] -";
        Ret[0].data[2].fontSize = 2;
        Ret[0].data[3].frameData = "Speed [SPD]\r\nTemp [TMP]\r\nTIME: [KK_PL_TIME] \\";
        Ret[0].data[3].fontSize = 2;
        //
        Ret[1] = new UIFramePack();
        Ret[1].name = "Default Diag Display 2x8 DETAILS PAGE";
        Ret[1].packID = "";
        Ret[1].data = new UIFrameData[1];
        Ret[1].data[0] = new UIFrameData();
        Ret[1].data[0].frameData = "Temp: [TMP]\r\nVoltage:[VOLTAGE]\r\nSpeed: [SPD]\r\nRPM: [RPM]";
        Ret[1].data[0].fontSize = 2;

        //
        Ret[2] = new UIFramePack();
        Ret[2].name = "Default Diag Display 2x8 WAIT";
        Ret[2].packID = "";
        Ret[2].data = new UIFrameData[1];
        Ret[2].data[0] = new UIFrameData();
        Ret[2].data[0].frameData = "......WAIT......\r\n......WAIT......";
        Ret[2].data[0].fontSize = 2;
        //
        Ret[3] = new UIFramePack();
        Ret[3].name = "Default Diag Display 2x8 Adapter error";
        Ret[3].packID = "";
        Ret[3].data = new UIFrameData[1];
        Ret[3].data[0] = new UIFrameData();
        Ret[3].data[0].frameData = "Err: [ODB_ADAPTER_STATE]\r\n[ODB_ADAPTER_ERROR]";
        Ret[3].data[0].fontSize = 2;
        //
        return Ret;

    }

}
