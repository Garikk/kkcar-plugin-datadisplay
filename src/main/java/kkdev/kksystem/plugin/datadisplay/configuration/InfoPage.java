/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import java.util.Map;
import kkdev.kksystem.base.classes.display.pages.framesKeySet;
import kkdev.kksystem.base.classes.display.tools.infopage.MKPageItem;

/**
 *
 * @author blinov_is
 */
public class InfoPage {
    public String PageName;
    public String PageCMD;
    
    public framesKeySet UIFrames;
    public int[] DiagPIDs;

    public InfoPage(String Name)
    {
        PageName=Name;
    }
    
    public MKPageItem GetPageItem()
    {
        MKPageItem Ret;
        Ret=new MKPageItem();
        
        Ret.pageName=PageName;
        Ret.pageFrames=UIFrames;
        Ret.pageCommand=PageCMD;
        
        return Ret;
    }
            
 }
