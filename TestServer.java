/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiple;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Divyani
 */
public class TestServer extends javax.swing.JFrame 
{
      // Process to connect to database
    public Connection cn;
    public Statement st;
    public static String con="";
    //con="";

    /**
     * Creates new form TestServer
     */
    public TestServer() {
        initComponents();
         try
        {
            //Name of the driver
            Class.forName("com.mysql.jdbc.Driver");
            //Database Url from jdbc proprties
            cn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/is?zeroDateTimeBehavior=convertToNull","root","root");
            st=(Statement) cn.createStatement();
            JOptionPane.showMessageDialog(null,"Connected");
             String sql="select * from login where status='1'";
        try {
            ResultSet rs=st.executeQuery(sql);
             while (rs.next()) {
    String val = rs.getString(2);
    //value += val + " ";
    online.setText(val+"\n");
  }
            
        } catch (SQLException ex) {
            // Exception
              JOptionPane.showMessageDialog(null,"Exception Occured\nPlease Check!!!");
            //Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        }catch(Exception e)
        {
             JOptionPane.showMessageDialog(null,"Unable to connect to Database");
            
        }
        
    }
    
    
  //   String[] emails = {"hellodivyani@gmail.com","komal@gmail.com","shailesh@gmail.com","alice@gmail.com","bob@gmail.com"};
 //password is abc
    
    int port;
    ServerSocket server=null;
    Socket client=null;
    //Executor provides services to manage termination and methods that can produce a Future for tracking progress.
    
    ExecutorService pool = null;
    int clientcount=0;
    
    public static void main(String[] args) throws IOException {
          /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestServer().setVisible(true);
                    
            }
        });
       
        
       
        
        TestServer serverobj=new TestServer(5000);
        serverobj.startServer();
        
    }
    
    TestServer(int port){
      //  doOperation();
        this.port=port;
        // Here 5 means at a time only 5 clients can be connected 
        // if 6th client tries to connect will enter in waiting state
        
        pool = Executors.newFixedThreadPool(5);
    }
  public void startServer() throws IOException  
  {
     // logcat.setEditable(false);
      //logcat.append("Server Booted\n");
      // logcat.setEditable(false);

            server=new ServerSocket(5000);
       
        // As soon as frame is loaded check for users online
        
      
         
        
    
       // logcat.setText("Server Booted\n");
       // System.out.println("Server Booted");
        //System.out.println("Any client can stop the server by sending -1");
        while(true)
        {
            // here creates multiple clients
            // this keeps  the server waiting till the client gets connected
            
            client=server.accept();
            // To identify the client used client count
            
            clientcount++;
            TestServer.ServerThread runnable;
            runnable = new TestServer.ServerThread(client,clientcount,this);
            pool.execute(runnable);
            //Here Always check who all are online
       String sql="select * from login where status='1'";
        try {
            ResultSet rs=st.executeQuery(sql);
             while (rs.next()) {
    String val = rs.getString(2);
    //value += val + " ";
    online.setText(val+"\n");
  }
            
        } catch (SQLException ex) {
            // Exception
              JOptionPane.showMessageDialog(null,"Exception Occured\nPlease Check!!!");
            //Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
  }
    private void doOperation() {
         logcat.setEditable(false);

        
         String sql="select * from login where status='1'";
        try {
            ResultSet rs=st.executeQuery(sql);
             while (rs.next()) {
    String val = rs.getString(2);
    //value += val + " ";
    online.setText(val+"\n");
  }
            
        } catch (SQLException ex) {
            // Exception
              JOptionPane.showMessageDialog(null,"Exception Occured\nPlease Check!!!");
            //Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }   
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
  
    public static class ServerThread implements Runnable {
        
        //Runnable helping to create multiple thread
        
        TestServer server=null;
        Socket client=null;
        BufferedReader cin;
        PrintStream cout;
        Scanner sc=new Scanner(System.in);
        int id;
        String s;
        //public String ok;
        TestServer ob=new TestServer();
        ServerThread(Socket client, int count ,TestServer server ) throws IOException {
            
            this.client=client;
            this.server=server;
            this.id=count;
         
            
           System.out.println("Connection "+id+"established with client "+client);
           // logcat.setText("");
        //  logcat.append("");
           con=con+"Connection "+id+"established with client "+client;
         ob.logcat.setText(con);
           //Different for diffrent client
            cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
            cout=new PrintStream(client.getOutputStream());
        
        }
        
     
        @Override
        public void run() {
            int x=1;
            int flag=1;
            // TestServer ob1=new TestServer();
          String c1="";
         try{
         while(true){
               s=cin.readLine();
  			 
			System. out.print("Client("+id+") :"+s+"\n");
			System.out.print("Server : ");
                    //    con=con+"\"Client(\"+id+\") :\"+s+\"\\n\"";
			c1=con+"\"Client(\"+id+\") :\"+s+\"\\n\"";
                        //s=stdin.readLine();
                      
                          //  ob.logcat.setText(con);
                          
                            s=sc.nextLine();
                        if (s.equalsIgnoreCase("bye"))
                        {
                            cout.println("BYE");
                            x=0;
                            System.out.println("Connection ended by server");
                            break;
                        }
			cout.println(s);
		}
		
            
                cin.close();
                client.close();
		cout.close();
                if(x==0)
                {
                    con=con+"Server Cleaning up"+"\n";
			System.out.println( "Server cleaning up." );
			System.exit(0);
                }
         } 
         catch(IOException ex)
         {
             con=con+"Error : "+ex+"\n";
             System.out.println("Error : "+ex);
         }
            
 		
        }
    }
  
    public void setMsg(String msg)
    {
        logcat.setText(msg);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logcat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        online = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("     MAIL SERVER");

        jLabel2.setText("SERVER LOGCAT");

        jLabel3.setText("LIST OF CLIENTS ONLINE");

        logcat.setColumns(20);
        logcat.setRows(5);
        jScrollPane1.setViewportView(logcat);

        online.setColumns(20);
        online.setRows(5);
        online.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onlineMouseClicked(evt);
            }
        });
        online.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                onlineComponentShown(evt);
            }
        });
        jScrollPane2.setViewportView(online);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(97, 97, 97))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(296, 296, 296))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void onlineMouseClicked(java.awt.event.MouseEvent evt) {                                    
        // TODO add your handling code here:
        String sql="select * from login where status='1'";
        try {
            ResultSet rs=st.executeQuery(sql);
             while (rs.next()) {
    String val = rs.getString(2);
    //value += val + " ";
    online.setText(val+"\n");
  }
            
        } catch (SQLException ex) {
            // Exception
              JOptionPane.showMessageDialog(null,"Exception Occured\nPlease Check!!!");
            //Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
    }                                   

    private void onlineComponentShown(java.awt.event.ComponentEvent evt) {                                      
        // TODO add your handling code here:
         // Here JFrame Window Must Open
         String sql="select * from login where status='1'";
        try {
            ResultSet rs=st.executeQuery(sql);
             while (rs.next()) {
    String val = rs.getString(2);
    //value += val + " ";
    online.setText(val+"\n");
  }
            
        } catch (SQLException ex) {
            // Exception
              JOptionPane.showMessageDialog(null,"Exception Occured\nPlease Check!!!");
            //Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                     

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea logcat;
    private javax.swing.JTextArea online;
    // End of variables declaration                   
}
