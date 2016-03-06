/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.odb;

import java.util.HashMap;
import java.util.Map;
import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.display.UIFramesKeySet;
import kkdev.kksystem.base.classes.display.tools.infopage.MKPageItem;
import kkdev.kksystem.base.classes.display.tools.infopage.PageMaker;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.odb2.tools.odbdecoder.ODBDecoder;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.configuration.InfoPage;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBDataDisplay implements IProcessorConnector {

    DataProcessor DP;
    PageMaker PMaker;
    ODBDecoder ODBDataDecoder;
    Map<String,InfoPage> InfoPages;
    String ActivePage;
    
    final String PAGE_MAIN="MAIN";
    final String PAGE_DETAIL="DETAIL";    
    final String P_MAIN_UIFRAME_ENG_TEMP="TMP";
    final String P_MAIN_UIFRAME_CAR_SPEED="SPD";
    final String P_DETAIL_UIFRAME_TEMP="TMP";
    final String P_DETAIL_UIFRAME_VOLTAGE="VOLTAGE";
    final String P_DETAIL_UIFRAME_RPM="RPM";
    final String P_DETAIL_UIFRAME_CAR_SPEED="SPD";
    


    public ODBDataDisplay(DataProcessor DPInfo) {
        PMaker = new PageMaker(Global.DM.CurrentFeature, Global.DM.Connector, ExecInfoPageCommand);
        DP=DPInfo;
        InfoPages=new HashMap<>();
        ODBDataDecoder=new ODBDecoder();
        //
        MKPageItem[] MyPages;
        MyPages=new MKPageItem[DP.Pages.length];
        int i=0;
        for (InfoPage IP:DP.Pages)
        {
            MyPages[i]=new MKPageItem();
            MyPages[i]=IP.GetPageItem();
            i++;
            InfoPages.put(IP.PageName, IP);
        }
        PMaker.AddPages(MyPages);
    }

    private PageMaker.IPageMakerExecCommand ExecInfoPageCommand = new PageMaker.IPageMakerExecCommand() {

        @Override
        public void ExecCommand(String PageCMD) {
            InfoPageExecuteCommand(PageCMD);
        }

        @Override
        public void PageSelected(String PageName) {
            if (ActivePage != null) {
                Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO_STOP, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_GETINFO_PIDDATA, InfoPages.get(ActivePage).DiagPIDs, null);
            }

            Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_GETINFO_PIDDATA, InfoPages.get(PageName).DiagPIDs, null);

            ActivePage = PageName;

        }
    };

    private void InfoPageExecuteCommand(String PageCMD) {
        String[] CMD = PageCMD.split(" ");
        switch (CMD[0]) {
            case "CHPROCESSOR":
                Global.DM.ChangeDataProcessor(Global.DM.DP_WAIT,CMD[1]);
                break;
            case "EXEC":
                ExecuteSpecialCommand(CMD[1]);
                break;
        }
    }

    private void ExecuteSpecialCommand(String CMD) {
        //not working by now
        if (CMD.equals("RECONNECT")) {
            return;
        }
        return;
    }

    @Override
    public void Activate(String TargetProc) {
        PMaker.ShowInfoPage();
    }

    @Override
    public void Deactivate() {
        if (ActivePage != null) {
            Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO_STOP, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_GETINFO_PIDDATA, InfoPages.get(ActivePage).DiagPIDs, null);
        }
    }

    @Override
    public void ProcessODBPIN(PinOdb2Data PMessage) {
        switch (PMessage.Odb2DataType) {
            case ODB_DIAG_CE_ERRORS:
                break;
            case ODB_DIAG_DATA:
                FillUIFrames(PMessage);
                break;
            case ODB_BASE_CONNECTOR:
                break;
        }
    }

    @Override
    public void ProcessControlPIN(PinControlData ControlData) {
              
        PMaker.ProcessControlCommand(ControlData.ControlID);
    }

  private void FillUIFrames(PinOdb2Data PMessage)
    {
        UIFramesKeySet Ret=new UIFramesKeySet();
        if (ActivePage.equals(PAGE_MAIN))
        {
            ODBDataDecoder.SimpleData.SetODBData(PMessage.ODBData);
            
            Ret.AddKeySet(P_MAIN_UIFRAME_ENG_TEMP, String.format("%.2f",ODBDataDecoder.SimpleData.DIAG_GetCarEngineCooliantTemp()));
            Ret.AddKeySet(P_MAIN_UIFRAME_CAR_SPEED, String.format("%.2f",ODBDataDecoder.SimpleData.DIAG_GetCarSpeed()));
            
            PMaker.UpdatePageFrames(ActivePage, Ret);
        }
        if (ActivePage.equals(PAGE_DETAIL))
        {
             ODBDataDecoder.SimpleData.SetODBData(PMessage.ODBData);
            Ret.AddKeySet(P_DETAIL_UIFRAME_TEMP, String.format("%.2f",ODBDataDecoder.SimpleData.DIAG_GetCarEngineCooliantTemp()));
            Ret.AddKeySet(P_DETAIL_UIFRAME_CAR_SPEED, String.format("%.2f",ODBDataDecoder.SimpleData.DIAG_GetCarSpeed()));
            Ret.AddKeySet(P_DETAIL_UIFRAME_VOLTAGE, String.format("%.2f", ODBDataDecoder.SimpleData.DIAG_GetCarBattVoltage()));
            Ret.AddKeySet(P_DETAIL_UIFRAME_RPM, String.format("%.2f",ODBDataDecoder.SimpleData.DIAG_GetCarEngineRPM()));
            PMaker.UpdatePageFrames(ActivePage, Ret);
        }
    }

    private int[] GetReadIntervals(int[] PIDs) {
        //Set 500msec read interval for all PIDs
        int[] Ret = new int[4];
        for (int i = 0; i <= 3; i++) {
            Ret[i] = 500;
        }
        return Ret;
    }

    @Override
    public String GetActivePage() {
        return ActivePage;
    }

   

    

}
