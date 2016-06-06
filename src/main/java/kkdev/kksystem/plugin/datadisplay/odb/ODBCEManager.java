/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.odb;

import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.base.classes.controls.PinControlData;
import static kkdev.kksystem.base.classes.controls.PinControlData.DEF_BTN_BACK;
import static kkdev.kksystem.base.classes.display.pages.PageConsts.KK_DISPLAY_PAGES_SIMPLEMENU_TXT_C1RX_PREFIX;
import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.IMenuMakerItemSelected;
import kkdev.kksystem.base.classes.notify.NotifyConsts;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import static kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_DATATYPE.ODB_DIAG_CE_ERRORS;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.base.classes.odb2.tools.odbdecoder.ODBDecoder;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.base.interfaces.IKKControllerUtils;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBCEManager implements IProcessorConnector {

    MenuMaker MMaker;
    ODBDecoder ODBDataDecoder;

    public ODBCEManager(IKKControllerUtils Utils) {
        ODBDataDecoder = new ODBDecoder();
        MMaker = new MenuMaker(Utils,Global.DM.CurrentFeature.get(PluginSettings.MainConfiguration.PrimaryUIContext),PluginSettings.MainConfiguration.PrimaryUIContext, KK_DISPLAY_PAGES_SIMPLEMENU_TXT_C1RX_PREFIX, Global.DM.Connector, MenuItemExec,true);

    }

    private IMenuMakerItemSelected MenuItemExec = new IMenuMakerItemSelected() {

        @Override
        public void SelectedItem(String ItemCMD) {
           // System.out.println("[DD] Exec Menu CMD " + ItemCMD);
            ExecMenuCommand(ItemCMD);
           // System.out.println("[DD] OVR " + ItemCMD);
        }

        @Override
        public void StepBack(String BackCMD) {
            //Not Used by now
        }
          // NotifyConsts.NOTIFY_METHOD[] NM=new NotifyConsts.NOTIFY_METHOD[1];
           //      NM[0]=NotifyConsts.NOTIFY_METHOD.VOICE;
             // Global.PM.NOTIFY_SendNotifyMessage( Global.PM.CurrentFeature.get(SystemConsts.KK_BASE_UICONTEXT_DEFAULT), NotifyConsts.NOTIFY_TYPE.SYSTEM_INFO,NM, ItemText);

        @Override
        public void ActiveMenuElement(String ItemText, String ItemCMD) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    @Override
    public void Activate(String TargetPage) {
        MMaker.ShowMenu();
    }

    @Override
    public void Deactivate() {
        //not need deactivate
    }

    @Override
    public void ProcessODBPIN(PinOdb2Data PMessage) {
        if (PMessage.Odb2DataType != ODB_DIAG_CE_ERRORS) {
            return;
        }

        List<String> Codes = new ArrayList<>();

        for (Integer Key : PMessage.ODBData.GetCEError().keySet()) {
            for (Byte Val : PMessage.ODBData.GetCEError().get(Key)) {
                Codes.add(ODBDataDecoder.GetTroubleCodePrefix(Key) + Key.toString() + Val.toString());
            }
        }

        MKMenuItem[] MenuItems = new MKMenuItem[Codes.size() + 1];
        int i = 0;
        for (String Code : Codes) {
            MenuItems[i] = new MKMenuItem();
            MenuItems[i].DisplayName = Code;
            MenuItems[i].ItemCommand = "";
            i++;
        }
        if (i>1)
        {
            MenuItems[i] = new MKMenuItem();
            MenuItems[i].DisplayName = "Clear errors";
            MenuItems[i].ItemCommand = "CE CLEARERRORS";
        }
        else
        {
            MenuItems[i] = new MKMenuItem();
            MenuItems[i].DisplayName = "No errors";
            MenuItems[i].ItemCommand = "CHPROCESSOR ODB_MAIN";
        }
        //
        MMaker.AddMenuItems(MenuItems);
        //
        MMaker.ShowMenu();
    }

    @Override
    public void ProcessControlPIN(PinControlData ControlData) {
           //System.out.println("[CTR] " + ControlData.ControlID);
        //if (!ControlData.ControlID.equals(DEF_BTN_BACK)) {
            MMaker.ProcessControlCommand(ControlData.ControlID);
       // } else {
       //     Global.DM.ChangeDataProcessor(Global.DM.DP_MAIN, "");
        //}
    }

    private void ExecMenuCommand(String Command) {
     //   System.out.println("[DATADIS] " + Command);
        
        String[] CMD = Command.split(" ");
        switch (CMD[0]) {
            case "CE":
                if (CMD[1].equals("CLEARERRORS")) {
                    Global.DM.ODBProcessor.ODB_SendPluginMessageCommand(Global.DM.CurrentFeature.get(PluginSettings.MainConfiguration.PrimaryUIContext), ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_EXEC_COMMAND, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_CMD_CLEAR_CE_DATA, null, null);
                }
                break;
            case "CHPROCESSOR":
              //  System.out.println(Command);
                Global.DM.ChangeDataProcessor(Global.DM.DP_MAIN, null);
                break;
            case "CHPROCESSOR_WAITPAGE":
               // System.out.println(Command);
                Global.DM.ChangeDataProcessor(Global.DM.DP_WAIT, CMD[1]);
                break;
            case "LEAVE":
                 Global.DM.ChangeDataProcessor(Global.DM.DP_WAIT, "ODB_MAIN");
                break;
        }
    }

    @Override
    public String GetActivePage() {
        return MMaker.GetActivePage();
    }
}
