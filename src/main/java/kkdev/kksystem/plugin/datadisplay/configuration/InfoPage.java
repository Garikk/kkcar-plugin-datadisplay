/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import java.util.Map;
import kkdev.kksystem.base.classes.display.UIFramesKeySet;
import kkdev.kksystem.base.classes.display.tools.infopage.MKPageItem;

/**
 *
 * @author blinov_is
 */
public class InfoPage {
    public String PageName;
    public String PageCMD;
    
    public UIFramesKeySet Parameters;

    public InfoPage(String Name)
    {
        PageName=Name;
    }
    
    public MKPageItem GetPageItem()
    {
        MKPageItem Ret;
        Ret=new MKPageItem();
        
        Ret.PageName=PageName;
        Ret.UIFrames=Parameters;
        Ret.PageCommand=PageCMD;
        
        return Ret;
    }
            
 }
