/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;

/**
 *
 * @author Diogo Monteiro - (1140302)
 */
public class Wall {
    private String wallName;
    private MessageList messageList;

    public Wall(String wallName) {
        this.wallName=wallName;
        this.messageList= new MessageList();
    }

    public String getWallName() {
        return wallName;
    }

    public void setWallName(String wallName) {
        this.wallName = wallName;
    }

    public MessageList getMessageList() {
        return messageList;
    }

    public void setMessageList(MessageList messageList) {
        this.messageList = messageList;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.wallName);
        hash = 37 * hash + Objects.hashCode(this.messageList);
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
        final Wall other = (Wall) obj;
        if (!Objects.equals(this.wallName, other.wallName)) {
            return false;
        }
        if (!Objects.equals(this.messageList, other.messageList)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return wallName;
    }
    
    
    
    
}
