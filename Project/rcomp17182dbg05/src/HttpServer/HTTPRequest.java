/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diogo Monteiro - (1140302)
 */
public class HTTPRequest extends Thread {

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    HTTPmessage httpRequest;
    HTTPmessage httpResponse;
    String folder;

    public HTTPRequest(Socket socket, String folder) {
        this.socket = socket;
        this.folder = folder;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(HTTPRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            httpRequest = new HTTPmessage(dataInputStream);
            httpResponse = new HTTPmessage();
            httpResponse.setResponseStatus("200 Ok");

            switch (httpRequest.getMethod()) {
                case "GET":
                    if (httpRequest.getURI().startsWith("/walls/")) {
                        String[] info = httpRequest.getURI().split("/");
                        String wallName = java.net.URLDecoder.decode(info[info.length - 1], "UTF-8");
                        HTTPServer.setOrCreateWall(wallName);

                        httpResponse.setContentFromString(HTTPServer.getWallList().getWallInUse().getMessageList().toString(), "text/html");
                        httpResponse.setResponseStatus("200 Ok");
                    } else {
                        String path = folder + "/";
                        if (httpRequest.getURI().equals("/")) {
                            path = path + "index.html";
                        } else {
                            path = path + httpRequest.getURI();
                        }

                        if (!httpResponse.setContentFromFile(path)) {
                            httpResponse.setContentFromString("<html><body><h1>404 File not found</h1></body></html>", "text/html");
                            httpResponse.setResponseStatus("404 Not Found");
                        }
                    }
                    break;
                case "POST":
                    if (httpRequest.getURI().startsWith("/walls/")) {
                        String message = httpRequest.getContentAsString();
                        String[] cmd = httpRequest.getURI().split("/");
                        
                        String wallName = java.net.URLDecoder.decode(cmd[2], "UTF-8");
                        HTTPServer.addMessage(message, wallName);

                        httpResponse.setResponseStatus("200 Ok");
                    }
                    break;
                case "DELETE":
                    String[] cmd = httpRequest.getURI().split("/");
                    if (cmd.length == 3) {
                        HTTPServer.removeWall(java.net.URLDecoder.decode(cmd[2], "UTF-8"));
                    } else if (cmd.length == 4) {
                        HTTPServer.removeMessage(Integer.parseInt(cmd[3]), java.net.URLDecoder.decode(cmd[2], "UTF-8"));
                    }
                    break;

            }
            httpResponse.send(dataOutputStream);
        } catch (IOException ex) {
            Logger.getLogger(HTTPRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(HTTPRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
