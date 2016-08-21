/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.transfer;

import java.net.*; 
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author hpcslag
 */
public class ClientNode {
    public static void main(String[] args) {
        String ip = "localhost";
        int port = 3333;
        int timeout = 50 * 1000;
        
	try(Socket s = new Socket()){
            InetSocketAddress addr = new InetSocketAddress(ip, port);
            s.connect(addr,timeout);
            s.setSoTimeout(timeout);
            
            Scanner scanner = scanner = new Scanner(System.in);
            DataInputStream MASTER_INPUT = new DataInputStream(s.getInputStream());
            DataOutputStream CLIENT_OUTPUT = new DataOutputStream( s.getOutputStream()); 
                
            String command = scanner.nextLine();
            System.out.println("You Sad: "+command);
            CLIENT_OUTPUT.writeInt(command.length()); //write length
            CLIENT_OUTPUT.writeBytes(command); // UTF is a string encoding

            CLIENT_OUTPUT.flush();

            int nb = MASTER_INPUT.readInt();
            byte[] digit = new byte[nb];
            //Step 2 read byte
            for(int i = 0; i < nb; i++)
                  digit[i] = MASTER_INPUT.readByte();

            String st = new String(digit);
            System.out.println("Received: "+ st);
                
            
            
        }catch(IOException e){
            System.out.println("Socket Start failed!");
            System.out.println(e);
        }
    }
}
