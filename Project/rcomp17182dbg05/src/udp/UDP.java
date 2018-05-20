/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author João Guedes 1140264
 */
public class UDP {

    public static void main(String args[]) throws Exception {
        
        InetAddress adress = InetAddress.getLocalHost();
        byte[] data = new byte[300];
        String wallName;
        String message;

        switch (args.length) {
            case 0:
                System.out.println("No parameters met");
                System.exit(1);
            case 1:
                wallName = args[0];
                break;
            default:
                adress = InetAddress.getByName(args[0]);
                wallName = args[1];
                break;
        }

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, adress, 32015);
            Scanner scan = new Scanner(System.in);
            
            while (true) {
                System.out.println("Message: (Write 0 to exit)");
                message = scan.nextLine();
                
                if (message.equals("0")) {
                    break;
                }
                
                String cmd = "POST /walls/" + wallName + "/" + message;
                data = cmd.getBytes();
                
                datagramPacket.setData(data);
                datagramPacket.setLength(data.length);
                socket.send(datagramPacket);
                socket.receive(datagramPacket);
            }
        }

    }

}
