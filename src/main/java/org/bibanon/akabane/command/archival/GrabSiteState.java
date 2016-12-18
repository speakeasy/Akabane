/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command.archival;

/**
 *
 * @author speakeasy
 */
public enum GrabSiteState {
    STOPPED,
    CANCALLED,
    INIT,
    INIT_DONE,
    RUNNING,
    FINISHED_GRAB,
    INIT_IA,
    UPLOADING,
    FINISHED_UPLOADING
}
