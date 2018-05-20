/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Diogo Monteiro - (1140302)
 */
public class Message implements Serializable {

    
    private String messageContent;
    
    
    
    public Message(String messageContent){
        
        this.messageContent= messageContent;
        
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.messageContent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (!Objects.equals(this.messageContent, other.messageContent)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Message{" + "messageContent=" + messageContent + '}';
    }
    
    
}
