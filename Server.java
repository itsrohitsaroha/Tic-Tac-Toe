import java.net.*;
import java.io.*;
import java.util.Random;

class Server implements Runnable
{
 boolean flag;
 Thread connectionThread;
 ServerSocket port;


 Server(int x) throws Exception
 {
  //open a port
  port = new ServerSocket(x); 
  //success
  //limit accept block time to 5000 milliseconds 
  port.setSoTimeout(60000); 

  connectionThread = new Thread(this);
  connectionThread.start();
 }//Server(int)

 public void run()//connectionThread
 {
  acceptConnections();
  shutDown();
 }

 void acceptConnections()
 {
  Socket clnt;
  flag = true;

  while(flag)
  {
   try
   {
     //accept a client connection
     //1. Blocks the program control until 
     //1.1. A client requests a connection
     //1.2. timeout
     //2. On request, form a connection
     //3. Return a Socket object that represents connection with the client 
 
     System.out.println("Server waiting for client connection request ...");
     clnt = port.accept();
     System.out.println("Server formed a client connection");
     new ProcessConnection(clnt);
   }
   catch(Exception ex)
   {}
  }//while

 }//acceptConnections

 void shutDown() 
 {
   try
   {
    port.close();
   }
   catch(Exception ex)
   {}
 }

 public static void main(String args[])
 {
  try
  {
   new Server(9876);
   //...job ahead 
  }
  catch(Exception ex)
  {
   System.out.println(ex); 
  }
 }//main

}//Server


class ProcessConnection extends Thread
{
 Socket clnt;
 static String baseDir;
 static
 {
  baseDir = "I:\\players";
  File f= new File(baseDir);
  if(! f.exists())
    f.mkdirs();
 }
 static String getString()
 {
  try
  {
   System.in.skip(System.in.available()); 
   byte arr[] = new byte[100];
   int n = System.in.read(arr);
   String s = new String(arr,0, n-2);
   return s;
  }
  catch(Exception ex)
  {
   return "NA"; 
  } 
 }//getString

 ProcessConnection(Socket s)
 {
   clnt = s;
   String baseDir;
   
   start();//activate the thread
 }

 public void run()
 {
  try
  {
   //fetch the i/o streams
   DataInputStream din = new DataInputStream( clnt.getInputStream() );
   DataOutputStream dout = new DataOutputStream( clnt.getOutputStream() );

  // boolean ch=1 ;
/*
   int arr[], opt;
   int points = 0;*/
   String player,player1,player2;

   player1 = din.readUTF();
   String fname = baseDir + "/" + player1 + ".txt";
   RandomAccessFile raf1 = new RandomAccessFile(fname ,"rw");
   long fileLength = raf1.length();
   raf1.seek(fileLength);
   player2 = din.readUTF();
   fname = baseDir + "/" + player2 + ".txt";
   RandomAccessFile raf2 = new RandomAccessFile(fname ,"rw");
   fileLength = raf2.length();
   raf2.seek(fileLength);
  // player = din.readUTF();
  
   
   
   /* System.out.print(player2+":-") ;
    player = getString();
    raf1.writeChars(player2+":-"+player+"\n");
    raf2.writeChars(player2+":-"+player+"\n");
    dout.writeUTF(player);  */

   do
   {

   /* player = din.readUTF();
    raf1.writeChars(player1+":-"+player+"\n");
    raf2.writeChars(player1+":-"+player+"\n");
    System.out.println(player1+":-" + player + "\n");
    System.out.print(player2+":-");
    player = getString();
    raf1.writeChars(player2+":-"+player+"\n");
    raf2.writeChars(player2+":-"+player+"\n");
    dout.writeUTF(player);*/
    player1=raf1.readLine();
    player2=raf2.readLine();
    if(player1=="bye" || player2=="bye")
    {
 	raf1.close();
        raf2.close();
	clnt.close();
    }
   }while(true);

  /* dout.writeUTF("Dear " + player + ", Bye Bye");
   dout.writeUTF("Score : " + points);    */

   //raf.seek(0);
  // raf.writeInt(points);
   
  // clnt.close();

  }//try
  catch(IOException ex)
  {
   ex.printStackTrace();
   System.out.println(ex);
  }

 }//run

}//ProcessConnection
