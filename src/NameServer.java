import java.util.*;
import java.io.*;
import java.net.*;

public class NameServer{
    public static Socket socket = null;
    static HashMap<Integer, String> keyValues = new HashMap<>();
    public static void main(String args[]) throws Exception{
        String configPath = args[0];
        File config = new File(configPath);
        Scanner sc = new Scanner(config);
        ServerInfo ns = new ServerInfo();
        ns.id= sc.nextInt();
        ns.listeningPort = sc.nextInt();
        ns.bootstrapip = sc.next();
        ns.bootstrapPort = sc.nextInt();
        Scanner input = new Scanner(System.in);
        String msg = "";
        int keyin = 0;
        while(true){
            if(input.next().equals("enter")){
                socket = new Socket(ns.bootstrapip, ns.bootstrapPort);
                DataInputStream istream = new DataInputStream(socket.getInputStream());
                DataOutputStream ostream = new DataOutputStream(socket.getOutputStream());
                ostream.writeUTF("enter");
                ostream.writeUTF(Integer.toString(ns.id));
                ostream.writeUTF(Integer.toString(ns.listeningPort));
                while(true){
                    keyin = Integer.parseInt(istream.readUTF());
                    if(keyin == -1){
                        break;
                    }
                    msg = istream.readUTF();
                    keyValues.put(keyin, msg);
                }
                System.out.println(keyValues.get(12));
                System.out.println(keyValues.get(434));
            }
        }
    }


}