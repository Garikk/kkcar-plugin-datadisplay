/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.processors.odb;

import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.display.tools.infopage.PageMaker;
import kkdev.kksystem.base.classes.odb2.ODB2Data;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.odb2.tools.odbdecoder.ODBDecoder;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBDataDisplay implements IProcessorConnector {

    PageMaker PMaker;
    ODBDecoder ODBDataDecoder;

    public ODBDataDisplay() {
        PMaker = new PageMaker(Global.DM.CurrentFeature, Global.DM.Connector, ExecInfoPageCommand);
    }

    private PageMaker.IPageMakerExecCommand ExecInfoPageCommand = new PageMaker.IPageMakerExecCommand() {

        @Override
        public void ExecCommand(String PageCMD) {
            InfoPageExecuteCommand(PageCMD);
        }
    };

    private void InfoPageExecuteCommand(String PageCMD) {
        String[] CMD = PageCMD.split(" ");
        switch (CMD[0]) {
            case "CHPROCESSOR":
                Global.DM.ChangeDataProcessor(CMD[1]);
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
    public void Activate() {
        /*
        switch (PageName) {
            case PAGE_MAIN:
                Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATAPACKET.ODB_PIDDATA, ODBDataDecoder.SimpleData.GetSimpleDiagRequest(), null);
                break;
            case PAGE_DETAIL:
                Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATAPACKET.ODB_PIDDATA, ODBDataDecoder.ExtendedData.GetExtendedDiagRequest(), null);
                break;
            case PAGE_WAIT:
                break;
            case PAGE_CEREADER:
                Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO, ODBConstants.KK_ODB_DATAPACKET.ODB_CE_ERRORS, null, null);
                break;
        }
                */
    }

    @Override
    public void Deactivate() {
        /*
        switch (PageName) {
            case PAGE_MAIN:
                Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO_STOP, ODBConstants.KK_ODB_DATAPACKET.ODB_PIDDATA, ODBDataDecoder.SimpleData.GetSimpleDiagRequest(), null);
                break;
            case PAGE_DETAIL:
                Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_GETINFO_STOP, ODBConstants.KK_ODB_DATAPACKET.ODB_PIDDATA, ODBDataDecoder.ExtendedData.GetExtendedDiagRequest(), null);
                break;
            case PAGE_WAIT:
                break;
            case PAGE_CEREADER:
                break;

        }
                */
    }

    @Override
    public void ProcessODBPIN(PinOdb2Data PMessage) {
        switch (PMessage.DataType) {
            case ODB_DIAG_CE_ERRORS:
                break;
            case ODB_DIAG_DATA:
                FillAndSendDiagData(PMessage.ODBData);
                break;
            case ODB_BASE_CONNECTOR:
                break;
        }
    }

    @Override
    public void ProcessControlPIN(PinControlData ControlData) {
        switch (ControlData.ControlID) {
            case PinControlData.DEF_BTN_UP:
                PMaker.SelectPrevPage();
                break;
            case PinControlData.DEF_BTN_DOWN:
                PMaker.SelectNextPage();
                break;
            case PinControlData.DEF_BTN_ENTER:
                PMaker.ExecCommand();
                break;
            case PinControlData.DEF_BTN_BACK:
                break;

        }
    }

    public static void ConnectODBSource() {
        Global.DM.ODB_SendPluginMessageCommand(KK_BASE_FEATURES_ODB_DIAG_UID, ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_ADAPTER_CONNECT, ODBConstants.KK_ODB_DATAPACKET.ODB_OTHERCMD, null, null);
    }

    private void FillAndSendDiagData(ODB2Data DiagData) {
        String[] RetKeys;
        String[] RetValues;
        //if (MonSimple!=null)
        //  else

    }

    private void CreateCEErrorsPage() {

    }

    private int[] GetReadIntervals(int[] PIDs) {
        //Set 500msec read interval for all PIDs
        int[] Ret = new int[4];
        for (int i = 0; i <= 3; i++) {
            Ret[i] = 500;
        }
        return Ret;
    }

    

}
