
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

        MarcoServidor myMarco = new MarcoServidor();
        
        myMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        System.out.println(" I am the Server ");


	}

}

//declering the frame 
class MarcoServidor extends JFrame implements Runnable{
  
  //creating variables
  private JTextArea areaTexto;
  private Image myIcon; 
  
  //constructor
  public MarcoServidor() {
      
      //setting dimensions of the frame
      setBounds (1200, 300, 300, 380);
      
      //setting a background color
      setBackground(Color.orange);
      
      //setting a title
      setTitle("- Server - ");
  
      
      //creating a Toolkit object
      Toolkit myFrame = Toolkit.getDefaultToolkit();
      
      //Creating a panel
      JPanel myLamina = new JPanel();
      
          //locating the lamina using BoderLayout.
          myLamina.setLayout(new BorderLayout());
          
          //creating a textArea
           areaTexto = new JTextArea();
           
           myLamina.add(areaTexto, BorderLayout.CENTER);
      
          //adding the lamina
          add(myLamina);
          
          //making visible
          setVisible(true);
          
          //adding Icon
          Image myIcono = myFrame.getImage("src/Icono/chat_Icono.jpg");
          setIconImage(myIcono);
          
          //instantiate the thread class
          Thread myHilo = new Thread (this);
          //start thread execution
          myHilo.start();
      
  }

  
  
  public void run() {

      
      
      try {
          
          //socket to send the information back to the client
          ServerSocket myServidor = new ServerSocket(9999);
          
          //declarin variables
          String nick, ip, mensaje;
          
          
          //-----------ArrayList to store the IP address--------------------
              
              ArrayList <String> listaIp = new ArrayList<String>();
          
     //--------------------------------------------
          
          //variable to store the information will be sent
          paquete_envio1 paquete_recibido;
          
          while(true){
          
                  //opening the socket to accept the conexion
              Socket mySocket = myServidor.accept();
          
              //getting the information coming from the client to the server
              ObjectInputStream  paquete_datos_recibiendo = new ObjectInputStream(mySocket.getInputStream());
              
                  //Storing the information recieved  
                  paquete_recibido =(paquete_envio1)paquete_datos_recibiendo.readObject();
              

//storing the information recieved in variables
                      nick = paquete_recibido.getNick();
                      
                      ip = paquete_recibido.getIp();
                      
                      mensaje = paquete_recibido.getMensaje();
  
                      
///-----if the client is  online then ------------------) 
                      
              if(!mensaje.equals(" Online")) {
                  
//print the information into the textArea
                      
                      areaTexto.append("\n" + nick + ": - " + mensaje + " - for: " + ip);
                          
                      
                      
                      Socket envia_Destinatario = new Socket(ip, 9090);
                      
                      //enviar el paquete de informacion
                      ObjectOutputStream paquete_Reenvio = new ObjectOutputStream(envia_Destinatario.getOutputStream());
                          
                              //tipe the information 
                              paquete_Reenvio.writeObject(paquete_recibido);
                              
                              //close the ObjectStream
                              paquete_Reenvio.close();
                              
                              //close the ObjectStream
                              envia_Destinatario.close();
                              
                              //close the socket
                              mySocket.close();
                          
              }else {
                          

///--------------when the client is not online-------
                      
                      //to obtain de IP addresses;
                      InetAddress localizacion = mySocket.getInetAddress();
                      
                      //convert in string a objet InetAddress
                      String ip_Remota = localizacion.getHostAddress();
                      
                      System.out.println(" online: " + ip_Remota); 
                              
//adding the IP address to the comboBox
                              listaIp.add(ip_Remota);
                              
                              paquete_recibido.setIPs(listaIp); 
                              
                              //foreach
                         for(String z: listaIp) {
                                  
                                  System.out.println("ArrayList... " + z);
                              
//this socket will send the correct IP to all the clients onlien
                                  Socket envia_Destinatario = new Socket(z, 9090);
                                  
//sending the information
                                  ObjectOutputStream paquete_Reenvio = new ObjectOutputStream(envia_Destinatario.getOutputStream());
                                      
//typing the information sent
                                          paquete_Reenvio.writeObject(paquete_recibido);
                                          
//close the objectStream
                                          paquete_Reenvio.close();
                                          
//close the flow
                                          envia_Destinatario.close();
                                          
//close the socket
                                          mySocket.close();
                                  
                              }
                  }
                  
  //-----------------------------------------
          }
          
      } catch (IOException e1) {
          
          e1.printStackTrace();
      }catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
  }
  
}

