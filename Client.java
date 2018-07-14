import java.io.*;
import java.net.*;

class Client
{
 Socket s;
 static String baseDir;
 static
 {
  baseDir = "I:\\players";
  File f= new File(baseDir);
  if(! f.exists())
    f.mkdirs();
 }
 Client(String ip, int port) throws Exception
 {
  //request a connection
  s = new Socket(ip, port);
 }

 static int getInt()
 {
  try
  {
   System.in.skip(System.in.available()); 
   byte arr[] = new byte[20];
   int n = System.in.read(arr);
   String s = new String(arr,0, n-2);
   return Integer.parseInt(s);
  }
  catch(Exception ex)
  {
   return 0; 
  } 
 }//getInt

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

 static char getChar()
 {
  try
  {
   System.in.skip(System.in.available()); 
   return (char) System.in.read();
  }
  catch(Exception ex)
  {
   return ' '; 
  } 
 }//getChar


 void interact() throws Exception
 {
  DataInputStream din = new DataInputStream(s.getInputStream());
  DataOutputStream dout = new DataOutputStream(s.getOutputStream());

   char ch ;
   int arr[], opt;
   int points = 0;
   String player,player1,player2, s;

   System.out.print("enter ur name:-");
   player1=getString();
   System.out.print("enter ur friend's name:-");
   player2=getString();
   dout.writeUTF(player1);
   dout.writeUTF(player2);
   String fname = baseDir + "/" + player1 + ".txt";
   RandomAccessFile raf1 = new RandomAccessFile(fname ,"rw");
   long fileLength = raf1.length();
   raf1.seek(fileLength);
   fname = baseDir + "/" + player2 + ".txt";
   RandomAccessFile raf2 = new RandomAccessFile(fname ,"rw");
   fileLength = raf2.length();
   raf2.seek(fileLength);
   do
   {
    System.out.print(player1+":-");
    player = getString();
    raf1.writeChars(player+"\n");
   // player=raf2.readLine();
    while((player = raf2.readLine()) != null) 
    {
      System.out.println(player);
    }
    if(player==null)
     System.out.println("...\n");
   }while(true);

  /* s = din.readUTF();
   System.out.println(s);   
   s = din.readUTF();
   System.out.println(s);*/

 }//interact
 
 void close() throws Exception
 {
   s.close();
 }
 public static void main(String args[])
 {
  try
  {
   Client c = new Client("127.0.0.1", 9876);
   c.interact();
   c.close();
  } 
  catch(Exception ex)
  {
   System.out.println(ex);
  }
 }
}