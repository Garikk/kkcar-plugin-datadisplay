/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.odb;

import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.base.classes.controls.PinDataControl;
import static kkdev.kksystem.base.classes.display.pages.PageConsts.KK_DISPLAY_PAGES_SIMPLEMENU_TXT_C1RX_PREFIX;
import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.IMenuMakerItemSelected;
import kkdev.kksystem.base.classes.odb2.ODBConstants;
import static kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_DATATYPE.ODB_DIAG_CE_ERRORS;
import kkdev.kksystem.base.classes.odb2.PinDataOdb2;
import kkdev.kksystem.base.classes.odb2.tools.odbdecoder.ODBDecoder;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.configuration.PluginSettings;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;
import kkdev.kksystem.base.interfaces.IControllerUtils;

/**
 *
 * @author blinov_is
 */
public class ODBCEManager implements IProcessorConnector {

    MenuMaker MMaker;
    ODBDecoder ODBDataDecoder;

    public ODBCEManager(IControllerUtils Utils) {
        ODBDataDecoder = new ODBDecoder();
        MMaker = new MenuMaker(Utils,Global.DM.currentFeature.get(PluginSettings.MainConfiguration.PrimaryUIContext),PluginSettings.MainConfiguration.PrimaryUIContext, KK_DISPLAY_PAGES_SIMPLEMENU_TXT_C1RX_PREFIX, Global.DM.getPluginConnector(), MenuItemExec,true);

    }

    private IMenuMakerItemSelected MenuItemExec = new IMenuMakerItemSelected() {

        @Override
        public void selectedItem(String ItemCMD) {
           // System.out.println("[DD] Exec Menu CMD " + ItemCMD);
            ExecMenuCommand(ItemCMD);
           // System.out.println("[DD] OVR " + ItemCMD);
        }

        @Override
        public void stepBack(String BackCMD) {
            //Not Used by now
        }
          // NotifyConsts.NOTIFY_METHOD[] NM=new NotifyConsts.NOTIFY_METHOD[1];
           //      NM[0]=NotifyConsts.NOTIFY_METHOD.VOICE;
             // Global.PM.NOTIFY_SendNotifyMessage( Global.PM.currentFeature.get(SystemConsts.KK_BASE_UICONTEXT_DEFAULT), NotifyConsts.NOTIFY_TYPE.SYSTEM_INFO,NM, ItemText);

        @Override
        public void activeMenuElement(String ItemText, String ItemCMD) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    @Override
    public void Activate(String TargetPage) {
        MMaker.showMenu();
    }

    @Override
    public void Deactivate() {
        //not need deactivate
    }

    @Override
    public void ProcessODBPIN(PinDataOdb2 PMessage) {
        if (PMessage.Odb2DataType != ODB_DIAG_CE_ERRORS) {
            return;
        }

        List<String> Codes = new ArrayList<>();

        for (Integer Key : PMessage.ODBData.getCEError().keySet()) {
            for (Byte Val : PMessage.ODBData.getCEError().get(Key)) {
                Codes.add(ODBDataDecoder.getTroubleCodePrefix(Key) + Key.toString() + Val.toString());
            }
        }

        MKMenuItem[] MenuItems = new MKMenuItem[Codes.size() + 1];
        int i = 0;
        for (String Code : Codes) {
            MenuItems[i] = new MKMenuItem();
            MenuItems[i].displayName = Code;
            MenuItems[i].itemCommand = "";
            i++;
        }
        if (i>1)
        {
            MenuItems[i] = new MKMenuItem();
            MenuItems[i].displayName = "Clear errors";
            MenuItems[i].itemCommand = "CE CLEARERRORS";
        }
        else
        {
            MenuItems[i] = new MKMenuItem();
            MenuItems[i].displayName = "No errors";
            MenuItems[i].itemCommand = "CHPROCESSOR ODB_MAIN";
        }
        //
        MMaker.addMenuItems(MenuItems);
        //
        MMaker.showMenu();
    }

    @Override
    public void ProcessControlPIN(PinDataControl ControlData) {
           //System.out.println("[CTR] " + ControlData.controlID);
        //if (!ControlData.controlID.equals(DEF_BTN_BACK)) {
            MMaker.processControlCommand(ControlData.controlID);
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
                    Global.DM.ODBProcessor.ODB_SendPluginMessageCommand(Global.DM.currentFeature.get(PluginSettings.MainConfiguration.PrimaryUIContext), ODBConstants.KK_ODB_COMMANDTYPE.ODB_KKSYS_CAR_EXEC_COMMAND, ODBConstants.KK_ODB_DATACOMMANDINFO.ODB_CMD_CLEAR_CE_DATA, null, null);
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
        return MMaker.getActivePage();
    }
}
