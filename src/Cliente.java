
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.*;
import java.net.*;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;



public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	     //instantiate the frame
        MarcoCliente1 myMarco = new MarcoCliente1();
        
        myMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        System.out.println(" I am the Client ");


	}

}



//-------------------Window Event--------------------------//Window event class which check the IPs available online.

class MarcoCliente1 extends JFrame {
    
    //image variable declared
    private Image myIcon; 
    //Constructor
    public MarcoCliente1() {
        
        //creating a Toolkit object
        Toolkit myFrame = Toolkit.getDefaultToolkit();
        
        //Frame dimensions
        setBounds(600, 300, 400, 550);
        
        setTitle( " Scheduled Chat ");
        
        //instantiate LaminaMarcoCliente
        LaminaMarcoCliente1 myLamina = new LaminaMarcoCliente1();
        //adding the layer
        add(myLamina);
        
        //Visible
        setVisible(true);

        
        //window actions instantiate
        envio_online myWindow = new envio_online();
        
        addWindowListener(myWindow);
        
        //creating an image object.
        Image myIcono = myFrame.getImage("src/Icono/chat_Icono.jpg");
        //adding Icon
        setIconImage(myIcono);
    
    }
}

//-------------------Window Event--------------------------//Window event class which check the IPs available online.

class envio_online extends WindowAdapter{
    

    
    //execute a window event.
    public void windowOpened(WindowEvent e) {
        
        try {

            //creating a socket. 
            Socket mySocket = new Socket("192.168.1.244", 9999);
            
            //data-package will be sent to the server
            paquete_envio1 datos = new paquete_envio1();
            
            datos.setMensaje(" Online");
            
            //it make possible to send the data using the socket. The information flow to the server.

            ObjectOutputStream paquete_datos = new ObjectOutputStream(mySocket.getOutputStream());
            
            //write the data sent
            paquete_datos.writeObject(datos);
            //close the socket.
            mySocket.close();
            
            
        }catch(Exception e2) {
            
            System.out.println(" IPs could not be founded. ");
        }
        
    }
    
}

    
class LaminaMarcoCliente1 extends JPanel implements Runnable{
    
    //declaring different varialbes.
    private JLabel myNick;
    private JComboBox ip;
    private JTextField campo1;
    private JButton myBoton;
    private JTextArea campoChat;
    private JButton myBoton2;
    private ActionListener oyente; 
    private String nick_usuario;
    
    
    //constructor
    public LaminaMarcoCliente1() {
        
        
        JLabel n_nick = new JLabel("Nickname: ");
        //adding the label.
        add(n_nick);
        
        //set a background colour
        setBackground(Color.ORANGE);
        
        //set a colour to JLabel Nickname.
        n_nick.setForeground(Color.BLUE);
        
        //set a particular font to JLabel Nickname.
        n_nick.setFont(new Font("Arial", Font.BOLD,  17));

        
        //Panel asking for a nickname
        nick_usuario = JOptionPane.showInputDialog(" Nickname: ");
        
        myNick = new JLabel();
        
        //get the text written in the Panel 
        myNick.setText(nick_usuario);
        
        //adding nick textfield.
        add(myNick);
        
        //setting formats to nickname labels. 
        myNick.setForeground(Color.DARK_GRAY);
        myNick.setFont(new Font("Arial", Font.BOLD,  15));
        
            
        
        JLabel texto = new JLabel(" Online : ");
        //adding the label
        add(texto);
        
        //setting formats to text labels.
        texto.setForeground(Color.BLUE);
        texto.setFont(new Font("Arial", Font.BOLD,  17));
        
        
        
        //creating and adding ip JComboBox.
        ip = new JComboBox();
        
        //including in frame
        add(ip);
        
        //creacion del textArea 
        campoChat = new JTextArea(20,28);
        //adding the textArea 
        add(campoChat);
        
        //adding a text field 
        campo1 = new JTextField(20);
        //adding textField
            add(campo1);
            
            //adding or creating a button
            myBoton = new JButton ("Send");
            
            //adding schedule  button
            myBoton2 = new JButton("Schedule");
        
        //instantiate del buttons
        EnviarTexto1 myEvento = new EnviarTexto1();
        
        EnviarTexto1 myEvento2 = new EnviarTexto1();
        
        
            //actionlistenner added
            myBoton.addActionListener(myEvento);
            
            //actionlistenner added
            myBoton2.addActionListener(myEvento2);
            
            //adding button 
            add(myBoton);
            
            //setting format button1
            myBoton.setBackground(Color.cyan);
            myBoton.setFont(new Font("Arial", Font.BOLD,  17));
        
            //adding schedule boton
            add(myBoton2);
            
            //setting format  button2
            myBoton2.setBackground(Color.cyan);
            myBoton2.setFont(new Font("Arial", Font.BOLD,  17));
            //instantiate oyente
            oyente = new EnviarTexto1();
            
            //Thread start 
            Thread myHilo = new Thread(this);
            myHilo.start();
        
    }
    


    //Creating a inner class to develop button events
    private class EnviarTexto1 implements ActionListener{
        
        
        public void actionPerformed(ActionEvent e) {
            
            //Identify the button pressed
            Object botonPulsado = e.getSource();
            
            if(botonPulsado == myBoton2) {
    //----------------------------------------------button-2 schedule----------------------------------------   
     
                //necesario pasarle la direcion IP // anadir un puerto (9999)
                try {
                    
                    //sending by socket the IP address and openning a port
                    Socket mySocket = new Socket("192.168.1.244", 9999);
                            
                        
                    //instantiate paquete_envio
                    paquete_envio1 datos = new paquete_envio1();
                    
                        
                    //panel asking the interval 
                    String inter = JOptionPane.showInputDialog(" Enter the Interval " + "\n" + " The time will be measured  in seconds ");
                    
                    //it shows the interval entered // after click ok the schedule will start
                    JOptionPane.showMessageDialog(null, " The message will be sent every: " + inter + " Seconds");  
                    
                    //storing the interval in this variable.
                    int Intervalo = Integer.parseInt(inter);
                    
                    //converting the interval in seconds
                    Intervalo = (int) (Intervalo /0.001);
                    
                    //timer to schedule the message
                    Timer myTemporizador = new Timer(Intervalo, oyente);
                
                    
          //getting the information nick, ip and message
                        
                        //obtain nickname data
                        datos.setNick(myNick.getText());
                        
                        //obtaining the IP address
                        //toString convert IP into string 
                        datos.setIp( ip.getSelectedItem().toString());
                        
                        datos.setMensaje(campo1.getText());
                        

                        //sending the data using the socket

                        ObjectOutputStream paquete_datos = new ObjectOutputStream(mySocket.getOutputStream());
                                            
                        
                        paquete_datos.writeObject(datos);
                        
                        //Nos genera un beep cada vez q imprime la hora en la consola.
                        Toolkit.getDefaultToolkit().beep();
                        
                        //start the timer 
                        myTemporizador.start(); 
                        
                        //shows in the text area  (cliente_2)
                        campoChat.append(" \n " +  nick_usuario + ": " +  campo1.getText());
                        
                        //show the panel 
                        JOptionPane.showMessageDialog(null, " Press the button to stop the message ");
                        
                        //finish the execution
                        myTemporizador.stop();
                        
                        
                        mySocket.close();
                    
                }catch(UnknownHostException e1){
                    //follow the error if there is a error
                    e1.printStackTrace();



            } catch (IOException e1) {
                    
                    //getMessage para q me imprima un mensaje con el error
                    System.out.println(e1.getMessage());
                    //e1.printStackTrace();
                }
        
                
//---------------------------------------------------------    
                
//-----------pressing button send-------------------------------------------------------------            
            }else {
            
            
            //nos imprime en el txtArea el nick q el usuario ha elegido.
            
            try {
                
                Socket mySocket = new Socket("192.168.1.244", 9999);
                        
                    
                paquete_envio1 datos = new paquete_envio1();
                
                //Obtain the information from nick, ipi and message fields.
                    datos.setNick(myNick.getText());
                    
                    datos.setIp( ip.getSelectedItem().toString());
                    
                    datos.setMensaje(campo1.getText());
                    
                    //send the information through the socket
                    ObjectOutputStream paquete_datos = new ObjectOutputStream(mySocket.getOutputStream());
                    
                    //print into the textArea the messages
                    campoChat.append(" \n " + nick_usuario + ": " +  campo1.getText());
                    
                    //interpretate the message sent
                    paquete_datos.writeObject(datos);
                    
                    //close the socket
                    mySocket.close();
                
            }catch(UnknownHostException e1){
                //follow the error if there is a error
                e1.printStackTrace();



        } catch (IOException e1) {
                
                System.out.println("Problem 411 " );
                //getMessage print the error message 
                System.out.println(e1.getMessage());
                //e1.printStackTrace();
            }
    
            }//else parentesis
        
      
        }
    }
//---------------------------
//----------------------------------------------Get the client ready to receive message from the server--------------------------------------
    public void run() {
        
        try {
            
            //create socket and open a port for the client
            ServerSocket servidor_cliente = new ServerSocket (9090);
                
                //
                Socket cliente;
                
                //to store the information to send
                paquete_envio1 paquete_recibido_Servidor;
                
                
                while(true) {
                    
                    //accepting the connection 
                    cliente= servidor_cliente.accept();
                    
                    
              //getting the information from the server
                    ObjectInputStream flujo_Entrada = new ObjectInputStream(cliente.getInputStream());
                    
                //reading the information from the server
                        paquete_recibido_Servidor = (paquete_envio1) flujo_Entrada.readObject();
                        
                        
                    //this if checks if the IP address is already taken in the combo box
                        if(!paquete_recibido_Servidor.getMensaje().equals(" Online")) {
                
                        campoChat.append("\n" + paquete_recibido_Servidor.getNick() + " : " + paquete_recibido_Servidor.getMensaje() + " - ");
                        
                        }else {
                        
                            
                            //creating an array list to store the IP address
                            ArrayList <String> IPsMenu =new ArrayList<String>();
                            
             //storing the ip addresss information

                            IPsMenu = paquete_recibido_Servidor.getIPs();
                            
          //clean the combo box from IPs previously.

                            ip.removeAllItems();
                            
       //foreach to print the IP address into the combo Box
                            for(String z : IPsMenu) {
                                
                                ip.addItem(z);
                                
}
                            
                        }
                }
                
        }catch(Exception e) {
            
            System.out.println(e.getMessage());
        }
        
    }
        
    
}
//-Storing the information Nick, IP, and message to be sent ---------------------------------------

class paquete_envio1 implements Serializable {
    
    
    //declrering variables
    private String nick, ip, mensaje;
    
    //store the IPs are online
    private ArrayList <String> IPs;
    
        public String getNick() {
            return nick;
        }
    
        public void setNick(String nick) {
            this.nick = nick;
        }

        
            public String getIp() {
                return ip;
            }
        
            public void setIp(String ip) {
                this.ip = ip;
            }

        
            public String getMensaje() {
                    return mensaje;
                }
            
                public void setMensaje(String mensaje) {
                    this.mensaje = mensaje;
                }

                public ArrayList <String> getIPs() {
                    return IPs;
                }

                public void setIPs(ArrayList <String> iPs) {
                    IPs = iPs;
                }
    
        
    

}

