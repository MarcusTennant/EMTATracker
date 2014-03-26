/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emtatracker;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;

/**
 *
 * @author Marcus Tennant
 */
public class EMTATrackerCLI {
    Properties propsIMAP;
    Properties propsSMTP; //I don't think the newest design has any use for smtp/outgoing mail but I'll leave it in for now
    Session sessionIMAP;
    Session sessionSMTP;
    Store store;
    Folder inbox;
    String password = "";
    String email = "";
    boolean enabled;
    Thread listening;
    
    public EMTATrackerCLI() {
        //initComponents();
        initializeMailListener();
        
        if(!enabled)
        {
            enabled = true;
            listenStart();
        }
    }
    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EMTATrackerCLI();
            }
        });     
    }

    public void initializeMailListener() 
    {
    try {
            propsIMAP = System.getProperties();
            propsIMAP.setProperty("mail.store.protocol", "imaps");
            sessionIMAP = Session.getDefaultInstance(propsIMAP, null);
            
            propsSMTP = System.getProperties();
            propsSMTP.put("mail.smtps.host","smtp.gmail.com");
            propsSMTP.put("mail.smtps.auth","true");
            sessionSMTP = Session.getInstance(propsSMTP, null);
            store = sessionIMAP.getStore("imaps");
        } catch (MessagingException ex) {
            Logger.getLogger(EMTATrackerCLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listenStart() {
        
        try {
        String email = "REDACTED";
        String password = "REDACTED";
        String Number = "REDACTED";
        
        if (!store.isConnected()) {
            store.connect("imap.gmail.com", email, password);
        }
        System.out.println("debug1");
        inbox = store.getFolder("INBOX");
        if (inbox.exists()) {
                            
                while(true)
                {
                    try {
                        inbox.open(Folder.READ_WRITE);
                        Thread.sleep(3000);
                        Message[] msg = inbox.search(   new FlagTerm(new Flags(Flags.Flag.SEEN), false),
                                                        inbox.search(new FromTerm(new InternetAddress(Number))));
                        for(Message message: msg)
                        {
                            System.out.println( "From: " +message.getFrom().toString()+"\n"+
                                                "Text: "+message.getContent());
                        }
                        
                        if(msg.length>0)
                        {
                            commit((String)msg[0].getContent());
                        }
                        inbox.close(false);
                    } catch (IOException ex) {
                        Logger.getLogger(EMTATrackerCLI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MessagingException ex) {
                        Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        } catch (MessagingException ex) {
            Logger.getLogger(EMTATrackerCLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void commit(String message){
        try{
            String file = "data.html";
            File f = new File(file);
            if(!f.exists())
            {
                    f.createNewFile();
            }
            FileReader fr = new FileReader(f);
            int temp = 0;
            String s = "";
            while(temp!=-1)
            {
                temp = fr.read();
                s = s+(char)temp;
            }
            s = s.substring(0, s.length()-1);
            fr.close();
            s = message+"\n"+s;
            FileWriter fw = new FileWriter(f);
            fw.write(s);
            fw.flush();
        } catch (IOException ex) {
            Logger.getLogger(EMTATrackerCLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

