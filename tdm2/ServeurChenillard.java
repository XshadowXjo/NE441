package fr.esisar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ServeurChenillard {

	public static void main(String[] args) throws IOException, InterruptedException {
		ServeurChenillard test = new ServeurChenillard();
		test.execute();
	}
	
	public void execute() throws IOException, InterruptedException {
		DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(3000));
        
        List<InetSocketAddress> adresses = new ArrayList<>();
        boolean attente = true;
        while(attente) {
        	byte[] bufR = new byte[2048];
            DatagramPacket dpR = new DatagramPacket(bufR, bufR.length);
            socket.receive(dpR);
            String reponse = new String(bufR, dpR.getOffset(), dpR.getLength());
            System.out.println("Nouveau client sur le port source "+dpR.getPort()+ "avec le flag " + reponse + "connecté");
            
            InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", dpR.getPort());
            adresses.add(adrDest);
            
            if(reponse.equals("1"))
            	attente = false;
        }
        for (int k=0; k<5; k++) {
	        for (InetSocketAddress adr: adresses) {
	        	byte[] bufE = new String("red").getBytes();
	            DatagramPacket dpE = new DatagramPacket(bufE, bufE.length, adr);
	            socket.send(dpE);
	        	System.out.println("Envoi d'un paquet UDP avec l'ordre red");
	        	Thread.sleep(1000);
	        }
        }
     // Fermeture de la socket
        socket.close();
        System.out.println("Arret du serveur.");

	}

}

