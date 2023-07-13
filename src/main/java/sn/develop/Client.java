package sn.develop;

import dao.CommentaireImpl;
import dao.ICommentaire;
import dao.IMembre;
import dao.MembreImpl;
import entities.Commentaire;
import entities.Membre;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;


public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    public static int nbClient;
    public Client(Socket socket, String username){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }



    public Client() {

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }


    public void sendMessage(String username,Membre membre){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            boolean isChatActive = true;
            while(socket.isConnected() && isChatActive) {
                String messageToSend = scanner.nextLine();
                Commentaire commentaire = new Commentaire();
                ICommentaire iCommentaire = new CommentaireImpl();
                commentaire.setDateC(new Date());
                commentaire.setMessage(messageToSend);
                commentaire.setMembre(membre);
                int ok1 = iCommentaire.create(commentaire);
                if (ok1 == 1) {
                    if(commentaire.getMessage().trim().equalsIgnoreCase("/quit")){
                        System.out.println("Vous avez quitté le chat");
                        isChatActive = false;
                    }else{
                        System.out.println("Commentaire ajoute avec success dans la base");
                        bufferedWriter.write(membre.getUsername() + " : " + commentaire.getMessage());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }

                } else {
                    System.out.println("erreur d'insertion ???????? ");
                }
            }
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()){
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    }catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String toString(){
        return (username+"");
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrer votre nom d'utilisateur pour le chat");
        String username = scanner.nextLine();
        IMembre iMembre = new MembreImpl();
        Membre membre = iMembre.seConnecter(username);
        if(membre != null){
            System.out.println("Entrer votre message : ");
            Socket socket = new Socket("localhost", 1237);
            Client client = new Client(socket, membre.getUsername());
            client.listenForMessage();
            client.sendMessage(username,membre);
        }else{
            Membre membre1 = new Membre();
            membre1.setUsername(username);
            int ok = iMembre.create(membre1);
            if (ok == 1) {
                System.out.println("Nouveau membre ajoute avec success dans la base ");
                System.out.println("Entrer votre message : ");
                Socket socket = new Socket("localhost", 1237);
                Client client = new Client(socket, membre1.getUsername());
                client.listenForMessage();
                client.sendMessage(username,membre1);
            } else {
                System.out.println("erreur d'insertion ");
            }
        }




    }
}
