/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emtatracker;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.MessagingException;

/**
 *
 * @author Matt Smith and Marcus Tennant
 */
public class ListenThread implements Runnable{

    Folder inbox;
    public ListenThread(Folder f)
    {
        inbox = f;
    }
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(5000);
                inbox.getMessageCount();
                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd.exe"); //Marcus: running on linux and things still work, not sure what this whole class does
            } catch (IOException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
