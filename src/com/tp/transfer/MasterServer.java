/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.transfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hpcslag
 */
public class MasterServer {
    public static ConcurrentHashMap<String, Socket> actionMapping = new ConcurrentHashMap<String,Socket>();  
    
    public static int onlineCount = 0;
    public void OpenServer(){
        try{
            ServerSocket server = new ServerSocket(3333);
            System.out.println("Server Running in localhost:3333");
            
            //Listen Command , can bordcast
            Thread Commander = new Thread(new Commander());
            Commander.start();
            
            while (true) {
                Socket s = server.accept();
                actionMapping.put(s.getRemoteSocketAddress().toString(),s);
                
                Thread Server = new Thread(new ServerRunner(s));
                Server.start();
            }
            
        }catch(Exception e){
            System.out.println("Server port could be used!");
        }
    }
    
    public static void main(String[] args) {
        MasterServer m = new MasterServer();
        m.OpenServer();
    }
}

class ServerRunner extends Thread{
    Socket s;
    ServerRunner(Socket s){
        this.s = s;
    }
    
    @Override
    public void run(){
        MasterServer.onlineCount ++;
        try{
            System.out.println("\n" + s.getRemoteSocketAddress() + "　Device Connected!");
            Thread MessageListener = new Thread(new MessageListener(s));
            
            MessageListener.start();
            
        }catch(Exception e){
            
        }
        
    }
}

class Commander implements Runnable{
    Socket s;
    
    Commander(){
    }
    
    @Override
    public void run() {
        try{
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("---------------------------");
                System.out.print("司令官，請問發送什麼命令? ");
                String command = scanner.nextLine();
                System.out.print("\n");
                //handler
                switch (command) {
                    case "bordcast":
                        System.out.print("\nWhat message you wont to bordcast? ");
                        String bordcast_msg = scanner.nextLine();
                        bordcast(bordcast_msg);
                        break;
                    case "getonline":
                        getOnlinePeople();
                        break;
                    case "kikout":
                        kikout();
                        break;
                    case "sendPrivate":
                        sendPrivateMessage();
                        break;
                    default:
                        System.out.println("司令官は、コマンドはありませんああ、使用することができます！");
                }
                
                System.out.println("---------------------------");
            }
        }catch(Exception e){
            System.out.println("Writting Failed! / User or targer early offline" + e);
        }
    }
    
    public void bordcast(String message) throws IOException{
        System.out.println("發送廣播訊息 - " + message);
        for(Entry<String, Socket> entry : MasterServer.actionMapping.entrySet()) {
            String key = entry.getKey();
            Socket s = entry.getValue();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
            out.println(message);
        }
    }

    public void getOnlinePeople(){
        System.out.println("目前上線人數: " + MasterServer.onlineCount + "\n");
    }

    public void sendPrivateMessage()throws IOException{
        String id = "";
        String message = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nSend Private Message To Who? ");
        id = scanner.nextLine();
        System.out.print("\nWhat message you wont to send? ");
        message = scanner.nextLine();
        
        if(MasterServer.actionMapping.containsKey(id)){
            System.out.println("向 " + id + " 發送訊息 - " + message);
            Socket client = (Socket)MasterServer.actionMapping.get(id);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
            out.println(message);
        }else{
            System.out.println("User Not Found! It Could Early Disconnected");
        }
    }

    public void kikout()throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nYou want to kik who? ");
        String id = scanner.nextLine();
        if(MasterServer.actionMapping.containsKey(id)){
            Socket client = (Socket)MasterServer.actionMapping.get(id);
            client.close();
            System.out.println(id + " has been Kiked! (Ban)");
        }else{
            System.out.println("User Not Found! It Could Early Disconnected");
        }
    }
    
}

class MessageListener implements Runnable{
    Socket s;
    
    MessageListener(Socket s){
        this.s = s;
    }
    
    @Override
    public void run() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

            while (true) {
                if(br.ready()){
                    String message = br.readLine();
                    System.out.println("\n" + s.getRemoteSocketAddress() + " 傳遞的訊息是：" + message);
                    filter(message);
                }
            }
        }catch(Exception e){
            System.err.println("\n" + s.getRemoteSocketAddress() + " Disconnected!");
            MasterServer.onlineCount --;
        }
    }
    
    public void filter(String message) throws IOException{
        PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
        out.println("伺服器正確收到您的訊息。");
    }

    public void openFileTransferStream(String path){
        //TODO Send File to user
    }

    public void askFilesIsSynchronized(){
        //TODO Create folder for server use.
        //Checking update version, and if they don't have update, then send file to node server.
    }
    
}

class ServerFolderController{
    public static String path;
    public static void setFolderPath(String path){
        ServerFolderController.path = path;
    }

    private void createFolderIfNotExists(){
        //
    } 
}