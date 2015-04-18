/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.datadisplay.configuration;

import kkdev.kksystem.plugin.datadisplay.displaymanager.IProcessorConnector;

/**
 *
 * @author blinov_is
 */
public class DataProcessor {
    public enum DATADISPLAY_DATAPROCESSORS
    {
        PROC_ELM327_BASIC_ODB2
    }
    
    public String[] TargetPages;
    public DATADISPLAY_DATAPROCESSORS ProcessorType;
    public IProcessorConnector Processor;
}
