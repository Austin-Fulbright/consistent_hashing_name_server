import java.io.*;
import java.net.*;
import java.util.*;


public class BootStrapServer{
    
    static HashMap<Integer, String> keyValues = new HashMap<>();
    static int id = 0;
    static int socketNumber = 0;
    static int predid = 0;
    static int succid = 0;
    static int predListening = 0;
    static int succListening = 0;
    static Socket socket = null;
    static ArrayList<Integer> serverIDS = new ArrayList<>();
    public BootStrapServer(){

    }

    public static String lookup(int lookKey){
        if(keyValues.containsKey(lookKey)){
            return keyValues.get(lookKey);
        }
        else{
            return("Key not found");
        }
    }
    public static ArrayList<Integer> insert(int key, String value){

        ArrayList<Integer> visited = new ArrayList<>();
            keyValues.put(key, value);
            visited.add(0);
        return visited;
    }
    public static ArrayList<Integer> delete(int key){
        ArrayList<Integer> visited = new ArrayList<>();
        if(keyValues.containsKey(key)){
            keyValues.remove(key);
            visited.add(0);
            return visited;
        }
        else{
         System.out.println("key not found");
         return visited;
        }
    }
    public static void sendKeyValuesFirst(DataOutputStream out, int id){
        try{
        for(int i = 0; i<=id; i++){
            if(keyValues.containsKey(i)){
                out.writeUTF(Integer.toString(i));
                out.writeUTF(keyValues.get(i));
                keyValues.remove(i);
            }
        }
        out.writeUTF("-1");
    }catch(Exception e){
        System.out.println(e);
    }
    }
    public static void main(String args[]){
    try{
        ServerInfo bs = new ServerInfo();
        File config = new File(args[0]);
        Scanner fileScan = new Scanner(config);
        bs.id = fileScan.nextInt();
        bs.listeningPort = fileScan.nextInt();
        serverIDS.add(id);
        ServerSocket bootStrap = new ServerSocket(bs.listeningPort);


        int k = 0;
        String v = "";

        while (fileScan.hasNextLine()==true) {
            k = fileScan.nextInt();
            v = fileScan.next();
            keyValues.put(k, v);
        }
        BootStrapActive bsthread = new BootStrapActive();
        bsthread.start();
        String msg = "";

        while(true){
            socket = bootStrap.accept();
            System.out.println("nameServer connected");
            DataInputStream istream = new DataInputStream(socket.getInputStream());
            DataOutputStream ostream = new DataOutputStream(socket.getOutputStream());
            msg = istream.readUTF();
            if(msg.equals("enter")){

                //if the bootstrap is the only one in the server case1
                if(bs.predissesorPort==0){
                    msg = istream.readUTF();
                    bs.predissesorid = Integer.parseInt(msg);
                    bs.successorid = Integer.parseInt(msg);
                    msg = istream.readUTF();
                    bs.predissesorPort = Integer.parseInt(msg);
                    bs.successorport = Integer.parseInt(msg);
                    ostream.writeUTF("0");
                    ostream.writeUTF(Integer.toString(bs.listeningPort));
                    sendKeyValuesFirst(ostream, bs.predissesorid);

                }
                //there is at least one server in the system
                else{
                    msg = istream.readUTF();

                    //the predissesor IP is smaller than the ID of the nameserver.
                    //keys from the bootstrap will be added into the new nameserver.
                    if(predid>Integer.parseInt(msg)){

                    }
                }

            }


        }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }





}