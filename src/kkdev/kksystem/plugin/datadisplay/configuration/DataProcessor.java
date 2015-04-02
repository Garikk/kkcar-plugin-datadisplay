/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

/**
 *
 * @author blinov_is
 */
public class DataProcessor {
    public enum DATADISPLAY_DATAPROCESSORS
    {
        PROC_ELM327_BASIC_ODB2
    }
    
    String[] TargetPages;
    DATADISPLAY_DATAPROCESSORS ProcessorType;
}
