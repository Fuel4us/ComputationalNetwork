/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Diogo Monteiro - (1140302)
 */
public class MessageList {
    private List<Message> messageList;

    public MessageList() {
        this.messageList= new ArrayList<>();
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
    
    public void addMessage(Message message){
        if(!messageList.contains(message)){
            this.messageList.add(message);
        }
    }
    
    public void removeMessage(Message message){
        if(this.messageList.contains(message)){
            this.messageList.remove(message);
        }
    }
    
    public void removeMessage(int messagePosition){
        if(this.messageList.size()>messagePosition&&messagePosition>-1){
            this.messageList.remove(messagePosition);
        }
    }
    
    public void clear(){
        this.messageList.clear();
    }
    
    public void remessage(){
        for (int i = 0; i < messageList.size(); i++) {
            String str = messageList.get(i).getMessageContent();
            if(!Character.isDigit(str.charAt(0))){
            messageList.get(i).setMessageContent(i+1+" - "+str);
            }
            
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.messageList);
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
        final MessageList other = (MessageList) obj;
        if (!Objects.equals(this.messageList, other.messageList)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        for (Message message : messageList) {
            str+=message.getMessageContent()+"\n";
        }
        return str;
    }
    
    
    
    
}
