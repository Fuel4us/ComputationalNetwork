/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpServer;

import Model.Message;
import Model.Wall;
import Model.AppModel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Pedro Lima  - (1140572)
 */
public class HTTPServer {
    private static final String DEFAULT_FOLDER = "http";
    private static final int PORT = 32015;
    private static AppModel wallList;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket datagramPacket;
    private static byte[] data;
    
    
    
    
    public static void main(String args[]) throws Exception{
        wallList=new AppModel();
        
        try{
            serverSocket= new ServerSocket(PORT);
            System.out.println("Server opened sucessfully on Port: "+PORT);
        } catch (IOException ex) {
            System.out.println("Server opening failed");
            Logger.getLogger(HTTPServer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        data = new byte[300];
        
            datagramSocket = new DatagramSocket(PORT);
            datagramPacket = new DatagramPacket(data, data.length);
        
        
        while(true){
            try{
                data = new byte[300];
                datagramSocket.setSoTimeout(1);
                datagramPacket.setData(data);
                datagramPacket.setLength(data.length);
                datagramSocket.receive(datagramPacket);
                runRequest();
                
            } catch (SocketTimeoutException ex) {
                
            } 
            
            try{
                serverSocket.setSoTimeout(1);
                socket = serverSocket.accept();
                HTTPRequest request = new HTTPRequest(socket, DEFAULT_FOLDER);
                request.start();
                
            } catch (SocketTimeoutException ex) {
                
            } 
        }
        
    }
    
    public static synchronized void runRequest() throws IOException{
        String cmd = new String(data).trim();
        String[] info = cmd.split("/");
        if(info.length>3){
            setOrCreateWall(info[2]);
            wallList.getWallInUse().getMessageList().addMessage(new Message(info[3]));
            String information = wallList.getWallInUse().getMessageList().toString();
            datagramPacket.setData(information.getBytes());
            datagramPacket.setLength(information.getBytes().length);
            datagramSocket.send(datagramPacket);
            
            
            
        }
    }
    
    public static void setOrCreateWall(String wallName){
        Wall wall = wallList.getWallByName(wallName);
       
        if(wall!=null){
            wallList.setWallInUse(wall);
        }else{
            wall = new Wall(wallName);
            wallList.addWall(wall);
            wallList.setWallInUse(wall);
        }
    }

    public static AppModel getWallList() {
        return wallList;
    }
    
    
    public static synchronized void removeWall(String wallName){
        Wall wall = wallList.getWallByName(wallName);
        if(wall!=null){
            wallList.removeWall(wall);
            wallList.setWallInUse(wall);
        }
        
    }
    
    public static synchronized void addMessage(String message, String wallName){
        setOrCreateWall(wallName);
        wallList.getWallInUse().getMessageList().addMessage(new Message(message));
        wallList.getWallInUse().getMessageList().remessage();
    }
    
    public static synchronized void removeMessage(int messagePosition, String wallName){
        setOrCreateWall(wallName);
        wallList.getWallInUse().getMessageList().removeMessage(messagePosition-1);
    }
}
