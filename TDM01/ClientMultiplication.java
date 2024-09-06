package fr.esisar.ne441;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


public class ClientMultiplication {
	public static void main(String[] args) throws IOException {
		System.out.println("Demarrage du client ...");

        //Creation de la socket
        DatagramSocket socket = new DatagramSocket();

        // Creation et envoi du message
        InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", 11000);
        byte[] bufE;

        for(int i=0; i<10; i++) {
        	System.out.println("===========================");
        	System.out.println("Debut de la partie "+i);
        	
        	bufE = new String("JOUER").getBytes();
            DatagramPacket dpE = new DatagramPacket(bufE, bufE.length, adrDest);
            socket.send(dpE);
        	System.out.println("Envoi d'un paquet UDP avec JOUER");

        	
        	// Attente de la reponse 
	        byte[] bufR = new byte[2048];
	        DatagramPacket dpR = new DatagramPacket(bufR, bufR.length);
	        socket.receive(dpR);
	        String reponse = new String(bufR, dpR.getOffset(), dpR.getLength());
	        int a = Integer.parseInt(reponse.substring(0,1));
	        socket.receive(dpR);
	        reponse = new String(bufR, dpR.getOffset(), dpR.getLength());
	        int b = Integer.parseInt(reponse.substring(0,1));
	        System.out.println("Le serveur  a répondu "+ a + " et "+b);
	        
        	int result = a*b;
        	String finalResult= ""+result;
	        bufE = finalResult.getBytes();
        	dpE = new DatagramPacket(bufE, bufE.length, adrDest);
            socket.send(dpE);
            
        	// Attente de la reponse 
            bufR = new byte[2048];
            dpR = new DatagramPacket(bufR, bufR.length);
	        socket.receive(dpR);
	        reponse = new String(bufR, dpR.getOffset(), dpR.getLength());
	        System.out.println("Le serveur  a répondu "+reponse);
            
        }
        

        // Fermeture de la socket
        socket.close();
        System.out.println("Arret du client .");
	}

}

