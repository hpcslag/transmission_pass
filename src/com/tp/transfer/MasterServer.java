/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.transfer;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author hpcslag
 */
public class MasterServer {

    public static void main(String[] args) {
        try {
            int serverPort = 3333;
            ServerSocket serv = new ServerSocket(3333);
            System.out.println("Now Server Start Listen In localhost:3333");

            while (true) {
                Socket clientSocket = serv.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server dead");
        }
    }
}
class Connection extends Thread{
    Socket client;
    DataInputStream input;
    DataOutputStream output;
            
    public Connection(Socket client) {
        try{
            this.client = client;
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
            this.start();
        }catch(IOException e){
            System.out.println("Connection: "+ e);
        }
    }
    public void run(){
        try{
            int messageLength = input.readInt();
            byte[] byteMessage = new byte[messageLength];
            for(int i =0;i<messageLength;i++){
                byteMessage[i] = input.readByte();
            }
            String message = new String(byteMessage);
            System.out.println ("receive from : " + 
				client.getInetAddress() + ":" +
				client.getPort() + " message - " + message);
            
            output.writeInt(message.length());
            output.writeBytes(message);
            
            output.flush();
            
        }catch(Exception e){
            
        }
    }
}