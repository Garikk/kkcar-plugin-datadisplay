/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import java.util.Map;

/**
 *
 * @author blinov_is
 */
public class InfoPage {
    public String PageName;
    public boolean IsDefaultPage;
    public String PageGroup;//WARNING!! Only one Group supported by now!!!
    
    public Map<String,Integer> Parameters;

    public InfoPage(String Name)
    {
        PageName=Name;
    }
            
 }
