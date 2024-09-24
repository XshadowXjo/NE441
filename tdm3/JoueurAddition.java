package fr.esisar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class JoueurAddition {

	public static void main(String[] args) throws IOException {
		JoueurAddition j1 = new JoueurAddition();
		j1.execute();
	}
	
	public void execute() throws IOException {
		
		
		String sepOps = "[=?]";
		String sepNbr = "[+]";
		int result=0;
		String memory = new String();
		
		
        System.out.println("Demarrage du client ...");

        //Creation de la socket
        Socket socket = new Socket();

        // Connexion au serveur 
        InetSocketAddress adrDest = new InetSocketAddress("127.0.0.1", 7500);
        socket.connect(adrDest);     
        byte[] bufR = new byte[2048];
        
        while(socket.isConnected()) {
        	
            InputStream is = socket.getInputStream();
            int lenBufR = is.read(bufR);
            if (lenBufR!=-1)
            {
                String message = new String(bufR, 0 , lenBufR);
                System.out.println("Message recu : "+message);
                
               if (message.startsWith("Erreur")) {
            	   break;
               }
               else {
            	   
           		   memory = memory + message;

            	   while(memory.contains("?")) {
            		   int index = memory.indexOf('?');
            		   String substring= memory.substring(0, index);
            		   memory = memory.substring(index+1, memory.length());
            		   //On sépare les opérations
                       String[] myOps = substring.split(sepOps);
                       for (String s : myOps) {
                       	System.out.println("op : "+s);
                       	//Puis les operandes
                       	
                           String[] myNbr = s.split(sepNbr);
                           	for (String operande : myNbr) {
                               	System.out.println("nbr : "+operande);
                               	if(!operande.isBlank()) {
                               		result += Integer.parseInt(operande);
                               	}
                               }
                       }
                    // Envoi de la reponse
       	            byte[] bufE = new String(result+";").getBytes();
       	            OutputStream os = socket.getOutputStream();
       	            os.write(bufE);
       	            System.out.println("Message envoye: "+ result );
       	            result = 0;
            	   }
               }
            	
	            
            	
            }
            }

        // Fermeture de la socket
        socket.close();
        System.out.println("Arret du client .");
    }
}

