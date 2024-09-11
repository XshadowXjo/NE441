package fr.esisar;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import javax.swing.JFrame;

public class ClientChenillard {

	public static void main(String[] args) throws InterruptedException, IOException {
		ClientChenillard test = new ClientChenillard();
		int portSrc = Integer.parseInt(args[0]);
		int flag = Integer.parseInt(args[1]);
		test.execute(portSrc, flag);
	}

	private void execute(int portSrc, int flag) throws InterruptedException, IOException {
		
		DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(portSrc));
        int portSrv = 3000;
        InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", portSrv);
		
        byte[] bufE = new String(""+portSrc).getBytes();
        DatagramPacket dpE = new DatagramPacket(bufE, bufE.length, adrDest);
        socket.send(dpE);
    	System.out.println("Envoi d'un paquet UDP avec le n° de port : " + portSrc);
    	
        JFrame frame = new JFrame("Chenillard");
        frame.setSize(300,300);
                
		
		for (int k=0; k<3; k++) {
        	frame.getContentPane().setBackground(Color.GREEN);
            frame.setVisible(true);
	    	
	    	// Attente de la reponse 
	        byte[] bufR = new byte[2048];
	        DatagramPacket dpR = new DatagramPacket(bufR, bufR.length);
	        socket.receive(dpR);
	        String reponse = new String(bufR, dpR.getOffset(), dpR.getLength());
	        System.out.println("Le serveur  a répondu "+reponse); 
	        
	    	frame.getContentPane().setBackground(Color.RED);
	        frame.setVisible(true);
	        Thread.sleep(1000);
	        
	        byte[] bufE = new String("switch").getBytes();
	        DatagramPacket dpE = new DatagramPacket(bufE, bufE.length, adrDest);
	        socket.send(dpE);
	    	System.out.println("Envoi d'un paquet UDP avec switch");
        }
		
		frame.dispose();

        // Fermeture de la socket
        socket.close();
        System.out.println("Arret du serveur.");
	}

}
