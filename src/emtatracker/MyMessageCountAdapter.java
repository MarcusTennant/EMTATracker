/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emtatracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

/**
 *
 * @author mms5303
 */
public class MyMessageCountAdapter extends MessageCountAdapter{
    String output;
    public MyMessageCountAdapter(String f)
    {
        output = f;
    }
    
    public void MessageAdded(MessageCountEvent ev)
    {
        FileWriter fr = null;
        try {
            Message[] msg = ev.getMessages();
            File f = new File(output);
            if(!f.exists())
            {
                f.createNewFile();
            }
            fr = new FileWriter(f);
            for(Message message: msg)
            {
                String location = (String)message.getContent();
                fr.write(location);
                
            }
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(MyMessageCountAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MyMessageCountAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(MyMessageCountAdapter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
