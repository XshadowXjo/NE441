package fr.esisar;

import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSommeTCP extends Thread{
	
	private HashMap<Integer, Integer> MontantParPort = new HashMap<>();
	private int port;
	
	public ClientSommeTCP(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) throws IOException {
		ClientSommeTCP client = new ClientSommeTCP(21000);
		client.execute();
	}
	
	public void execute() throws IOException {
				
		System.out.println("Demarrage du client ...");

        //Creation de la socket
        Socket socket = new Socket();

        // Connexion au serveur
        InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", port);
        socket.connect(adrDest);        

        // Envoi de la requete
        byte[] bufE = new String("COMBIEN").getBytes();
        OutputStream os = socket.getOutputStream();
        os.write(bufE);
        

        // Attente de la reponse 
        byte[] bufR = new byte[2048];
        InputStream is = socket.getInputStream();
        int lenBufR = is.read(bufR);
        if (lenBufR!=-1)
        {
            String reponse = new String(bufR, 0 , lenBufR );
            int montant = Integer.parseInt(reponse.substring(reponse.indexOf("=")+1, reponse.indexOf("E")));
            MontantParPort.put(port, montant);
            System.out.println("Montant ajouté sur le port "+ port +" = "+montant);
        }
        // Fermeture de la socket
        socket.close();
    }
}
	
