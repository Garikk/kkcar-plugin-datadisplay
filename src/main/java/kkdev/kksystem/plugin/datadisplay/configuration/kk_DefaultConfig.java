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
        DP.DynamicElements = false;
        DP.Features = new String[1];
        DP.Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.UIContexts = new String[1];
        DP.UIContexts[0] = SystemConsts.KK_BASE_UICONTEXT_DEFAULT;

        DP.PageName = "KKDIAG_DIAG";
        DP.UIContexts = new String[0];
        DP.IsDefaultPage = false;
        DP.IsMultifeaturePage = true;
        DP.UIFramesPack = FramePack[0];
        //
        Utils.DISPLAY_AddUIDisplayPage(DP);
        //
        DP = new DisplayPage();
        DP.DynamicElements = false;
        DP.Features = new String[1];
        DP.Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.UIContexts = new String[1];
        DP.UIContexts[0] = SystemConsts.KK_BASE_UICONTEXT_DEFAULT;

        DP.PageName = "KKDIAG_DIAG_2";
        DP.UIContexts = new String[0];
        DP.IsDefaultPage = false;
        DP.IsMultifeaturePage = true;
        DP.UIFramesPack = FramePack[1];
        //
        Utils.DISPLAY_AddUIDisplayPage(DP);
        //
        DP = new DisplayPage();
        DP.DynamicElements = false;
        DP.Features = new String[1];
        DP.Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.UIContexts = new String[1];
        DP.UIContexts[0] = SystemConsts.KK_BASE_UICONTEXT_DEFAULT;

        DP.PageName = "KKDIAG_WAIT";
        DP.IsDefaultPage = false;
        DP.IsMultifeaturePage = true;
        DP.UIFramesPack = FramePack[2];
        //

        Utils.DISPLAY_AddUIDisplayPage(DP);
        //
        DP = new DisplayPage();
        DP.DynamicElements = false;
        DP.Features = new String[1];
        DP.Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.UIContexts = new String[1];
        DP.UIContexts[0] = SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.PageName = "KKDIAG_ERROR";
        DP.IsDefaultPage = false;
        DP.IsMultifeaturePage = true;
        DP.UIFramesPack = FramePack[3];
        //
        Utils.DISPLAY_AddUIDisplayPage(DP);
        //
        //   DP = new DisplayPage();
        //DP.DynamicElements = false;
        //DP.Features = new String[1];
        //DP.Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        //DP.PageName = "KKDIAG_CE_READER";
        //DP.IsDefaultPage = false;
        //DP.IsMultifeaturePage = true;
        //DP.UIFramesPack = FramePack[1];
        //
        //Global.DM.BaseConnector.SystemUtilities().DISPLAY_AddUIDisplayPage(DP);

    }

    private static UIFramePack[] GetFramePack() {
        UIFramePack[] Ret = new UIFramePack[7];
        Ret[0] = new UIFramePack();
        Ret[0].Name = "Diag display pages";
        Ret[0].PackID = "";
        Ret[0].Data = new UIFrameData[4];
        Ret[0].Data[0] = new UIFrameData();
        Ret[0].Data[1] = new UIFrameData();
        Ret[0].Data[2] = new UIFrameData();
        Ret[0].Data[3] = new UIFrameData();

        Ret[0].Data[0].FrameData = "Speed [SPD]\r\nTemp [TMP]\r\nTIME: [KK_PL_TIME] |";
        Ret[0].Data[0].FontSize = 2;
        Ret[0].Data[1].FrameData = "Speed [SPD]\r\nTemp [TMP]\r\nTIME: [KK_PL_TIME] /";
        Ret[0].Data[1].FontSize = 2;
        Ret[0].Data[2].FrameData = "Speed [SPD]\r\nTemp [TMP]\r\nTIME: [KK_PL_TIME] -";
        Ret[0].Data[2].FontSize = 2;
        Ret[0].Data[3].FrameData = "Speed [SPD]\r\nTemp [TMP]\r\nTIME: [KK_PL_TIME] \\";
        Ret[0].Data[3].FontSize = 2;
        //
        Ret[1] = new UIFramePack();
        Ret[1].Name = "Default Diag Display 2x8 DETAILS PAGE";
        Ret[1].PackID = "";
        Ret[1].Data = new UIFrameData[1];
        Ret[1].Data[0] = new UIFrameData();
        Ret[1].Data[0].FrameData = "Temp: [TMP]\r\nVoltage:[VOLTAGE]\r\nSpeed: [SPD]\r\nRPM: [RPM]";
        Ret[1].Data[0].FontSize = 2;

        //
        Ret[2] = new UIFramePack();
        Ret[2].Name = "Default Diag Display 2x8 WAIT";
        Ret[2].PackID = "";
        Ret[2].Data = new UIFrameData[1];
        Ret[2].Data[0] = new UIFrameData();
        Ret[2].Data[0].FrameData = "......WAIT......\r\n......WAIT......";
        Ret[2].Data[0].FontSize = 2;
        //
        Ret[3] = new UIFramePack();
        Ret[3].Name = "Default Diag Display 2x8 Adapter error";
        Ret[3].PackID = "";
        Ret[3].Data = new UIFrameData[1];
        Ret[3].Data[0] = new UIFrameData();
        Ret[3].Data[0].FrameData = "Err: [ODB_ADAPTER_STATE]\r\n[ODB_ADAPTER_ERROR]";
        Ret[3].Data[0].FontSize = 2;
        //
        return Ret;

    }

}
