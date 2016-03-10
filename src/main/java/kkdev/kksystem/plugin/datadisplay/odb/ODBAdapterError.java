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
import static kkdev.kksystem.base.classes.odb2.ODBConstants.KK_ODB_DATATYPE.ODB_BASE_CONNECTOR;
import kkdev.kksystem.base.classes.odb2.PinOdb2ConnectorInfo;
import kkdev.kksystem.base.classes.odb2.PinOdb2ConnectorInfo.ODB_State;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;
import kkdev.kksystem.plugin.datadisplay.Global;
import kkdev.kksystem.plugin.datadisplay.configuration.DataProcessor;
import kkdev.kksystem.plugin.datadisplay.configuration.InfoPage;
import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class ODBAdapterError implements IProcessorConnector {

    PageMaker PMaker;
    Map<String,InfoPage> InfoPages;
    DataProcessor DP;
    String ActivePage;
    
    final String PAGE_ERROR="ERROR";
    final String UIFRAME_ERROR="[ODB_ADAPTER_ERROR]";
    final String UIFRAME_STATE="[ODB_ADAPTER_STATE]";
    
    public ODBAdapterError(DataProcessor DPInfo) {
        PMaker = new PageMaker(Global.DM.CurrentFeature, Global.DM.Connector, ExecInfoPageCommand);
        DP=DPInfo;
        //
        MKPageItem[] MyPages;
        MyPages=new MKPageItem[DP.Pages.length];
        InfoPages=new HashMap<>();
        
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
           ActivePage=PageName;
        }

    };
    @Override
     public void Activate(String TargetPage) {
       PMaker.ShowInfoPage();
    }

    @Override
    public void Deactivate() {
        //nothing
    }

    @Override
    public void ProcessODBPIN(PinOdb2Data PMessage) {
        if (PMessage.Odb2DataType == ODB_BASE_CONNECTOR) {
            FillUIFrames(PMessage);
        }

    }

    @Override
    public void ProcessControlPIN(PinControlData ControlData) {
       // System.out.println("CTRL " + ControlData.ControlID);
        PMaker.ProcessControlCommand(ControlData.ControlID);
    }
    
    private void FillUIFrames(PinOdb2Data PMessage)
    {
        if (PMessage.AdapterInfo.OdbAdapterState!=ODB_State.ODB_CONNECTOR_ERROR)
            return;
        
        UIFramesKeySet Ret;

            Ret=new UIFramesKeySet();
            Ret.AddKeySet(UIFRAME_ERROR, PMessage.AdapterInfo.OdbAdapterDescripton);
            Ret.AddKeySet(UIFRAME_STATE,  PMessage.AdapterInfo.OdbAdapterState.toString());
            PMaker.UpdatePageFrames(PAGE_ERROR, Ret);
    }
    
    
    
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
    public String GetActivePage() {
        return ActivePage;
    }
}
