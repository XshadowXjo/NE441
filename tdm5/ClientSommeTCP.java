package fr.esisar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSommeTCP extends Thread{
	
	private int port;
	private long montant;
	
	public ClientSommeTCP(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		List<ClientSommeTCP> clients = new ArrayList<>();
		for(int k=21000; k<21016; k++) {
			ClientSommeTCP client = new ClientSommeTCP(k);
			clients.add(client);
			client.start();
		}
		long montantTotal = 0;
		long maxMontant = 0;
		for(ClientSommeTCP c: clients) {
			c.join();
			if(c.montant > maxMontant)
				maxMontant = c.montant;
			montantTotal +=c.montant;
		}
		System.out.println("montant total :"+ montantTotal);
	}
	
	public void run() {
				
		System.out.println("Demarrage du client ...");

        
        try {
        	//Creation de la socket
        	Socket socket = new Socket();
	         //Connexion au serveur
	        InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", port);
	        socket.connect(adrDest);        
	
	         //Envoi de la requete
	        byte[] bufE = new String("COMBIEN").getBytes();
	        OutputStream os = socket.getOutputStream();
	        os.write(bufE);
	        
	
	         //Attente de la reponse 
	        byte[] bufR = new byte[2048];
	        InputStream is = socket.getInputStream();
	        int lenBufR = is.read(bufR);
	        if (lenBufR!=-1)
	        {
	            String reponse = new String(bufR, 0 , lenBufR );
	            montant = Integer.parseInt(reponse.substring(reponse.indexOf("=")+1, reponse.indexOf("E")));
	            System.out.println("Montant ajouté sur le port "+ port +" = "+montant);
	        }
	         //Fermeture de la socket
	        socket.close();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        
    }
}
	
