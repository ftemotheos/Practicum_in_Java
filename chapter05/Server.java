package chapter05;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
//    args[] = "-control", "futyj,fh,f", "3000"
//    args[] = "chapter05.Server$Time", "3001"
//    args[] = "chapter05.Server$Revers", "3002"
//    args[] = "chapter05.Server$HTTPMirror", "3003"
//    args[] = "chapter05.Server$UniqueID", "3004"
//    args[] = "", "3005"
    public static void main(String[] args) {
        try {
            if (args.length < 2) throw new IllegalArgumentException("Must be specified a service!");
            Server s = new Server(System.out, 10);
            int i = 0;
            while (i < args.length) {
                if (args[i].equals("-control")) {
                    i++;
                    String password = args[i++];
                    int port = Integer.parseInt(args[i++]);
                    s.addService(new Control(s, password), port);
                } else {
                    String serviceName = args[i++];
                    Class serviceClass = Class.forName(serviceName);
                    Service service = (Service)serviceClass.newInstance();
                    int port = Integer.parseInt(args[i++]);
                    s.addService(service, port);
                }
            }
        } catch (Exception e) {
            System.err.println("Server: " + e);
            System.err.println("Format is: java Server " + "[-control <password> <port>]" + "[<servicename> <port> ...]");
            System.exit(1);
        }
    }
    Map services;
    Set connections;
    int maxConnections;
    ThreadGroup threadGroup;
    PrintWriter logStream;
    public Server(OutputStream logStream, int maxConnections) {
        setLogStream(logStream);
        log("Server started!");
        threadGroup = new ThreadGroup(Server.class.getName());
        this.maxConnections = maxConnections;
        services = new HashMap();
        connections = new HashSet();
    }
    public synchronized void setLogStream(OutputStream out) {
        if (out != null) logStream = new PrintWriter(out);
        else logStream = null;
    }
    protected synchronized void log(String s) {
        if (logStream != null) {
            logStream.println("[" + new Date() + "]" + s);
            logStream.flush();
        }
    }
    protected void log(Object o) {
        log(o.toString());
    }
    public synchronized void addService(Service service, int port) throws IOException {
        Integer key = new Integer(port);
        if (services.get(key) != null) throw new IllegalArgumentException("The port " + port + "is already used!");
        Listener listener = new Listener(threadGroup, port, service);
        services.put(key, listener);
        log("Starting the service " + service.getClass().getName() + " to port " + port);
        listener.start();
    }
    public synchronized void removeService(int port) {
        Integer key = new Integer(port);
        final Listener listener = (Listener)services.get(key);
        if (listener == null) return;
        listener.pleaseStop();
        services.remove(key);
        log("Stopping the service " + listener.service.getClass().getName() + " to port " + port);
    }
    public class Listener extends Thread {
        ServerSocket listen_socket;
        int port;
        Service service;
        volatile boolean stop = false;
        public Listener() throws IOException {
            super();
        }
        public Listener(ThreadGroup group, int port, Service service) throws IOException {
            super(group, "Listener:" + port);
            listen_socket = new ServerSocket(port);
            listen_socket.setSoTimeout(600000);
            this.port = port;
            this.service = service;
        }
        public void pleaseStop() {
            this.stop = true;
            this.interrupt();
            try { listen_socket.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        public void run() {
            while(!stop) {
                try {
                    Socket client = listen_socket.accept();
                    addConnection(client, service);
                }
                catch (InterruptedIOException e) { e.printStackTrace(); }
                catch (IOException e) { log(e); }
            }
        }
        protected synchronized void addConnection(Socket s, Service service) {
            if (connections.size() >= maxConnections) {
                try {
                    PrintWriter out = new PrintWriter(s.getOutputStream());
                    out.print("Connection refused: server is congested, try connecting again later");
                    out.flush();
                    s.close();
                    log("Connection refused for " + s.getInetAddress().getHostAddress() + ":" + s.getPort() + ": exhausted limit the number of connections");
                }
                catch (IOException e) { log(e); }
            }
            else {
                Connection c = new Connection(s, service);
                connections.add(c);
                log("Connected for " + s.getInetAddress().getHostAddress() + ":" + s.getPort() + " to port" + s.getLocalPort() + " for service" + service.getClass().getName());
                c.start();
            }
        }
        protected synchronized void endConnection(Connection c) {
            connections.remove(c);
            log("Connection to " + c.client.getInetAddress().getHostAddress() + ":" + c.client.getPort() + "is closed!");
        }
        public synchronized void setMaxConnections(int max) {
            maxConnections = max;
        }
        public synchronized void displayStatus(PrintWriter out) {
            Iterator keys = services.keySet().iterator();
            while (keys.hasNext()) {
                Integer port = (Integer)keys.next();
                Listener listener = (Listener)services.get(port);
                out.print("SERVICE " + listener.service.getClass().getName() + " TO PORT " + port + "\n");
            }
            out.print("CONNECTION LIMIT: " + maxConnections + "\n");
            Iterator conns = connections.iterator();
            while (conns.hasNext()) {
                Connection c = (Connection)conns.next();
                out.print("CONNECTION TO " + c.client.getInetAddress().getHostAddress() + ":" + c.client.getPort() + " TO PORT " + c.client.getLocalPort() + " FOR SERVICE " + c.service.getClass().getName() + "\n");
            }
        }
        public class Connection extends Thread {
            Socket client;
            Service service;
            public Connection(Socket client, Service service) {
                super("Server.Connection:" + client.getInetAddress().getHostAddress() + ":" + client.getPort());
                this.client = client;
                this.service = service;
            }
            public void run() {
                try {
                    InputStream in = client.getInputStream();
                    OutputStream out = client.getOutputStream();
                    service.serve(in, out);
                }
                catch (IOException e) { log(e); }
                finally { endConnection(this); }
            }
        }
    }
    public interface Service {
        void serve(InputStream in, OutputStream out) throws IOException;
    }
    public static class Time implements Service {
        public void serve(InputStream i, OutputStream o) throws IOException {
            PrintWriter out = new PrintWriter(o);
            out.print(new Date() + "\n");
            out.close();
            i.close();
        }
    }
    public static class Revers implements Service {
        public void serve(InputStream i, OutputStream o) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(i));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(o)));
            out.print("Welcome to the line reversal server.\n");
            out.print("Enter lines. End with a '.' on a line by itself.\n");
            for(;;) {
                out.print("> ");
                out.flush();
                String line = in.readLine();
                if ((line == null) || line.equals(".")) break;
                for (int j = line.length() - 1; j >= 0; j--) {
                    out.print(line.charAt(j));
                }
                out.print("\n");
            }
            out.close();
            in.close();
        }
    }
    public static class HTTPMirror implements Service {
        public void serve(InputStream i, OutputStream o) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(i));
            PrintWriter out = new PrintWriter(o);
            out.print("HTTP/1.0 200 \n");
            out.print("Content-Type: text/plain\n\n");
            String line;
            while((line = in.readLine()) != null) {
                if (line.length() == 0) break;
                out.print(line + "\n");
            }
            out.close();
            in.close();
        }
    }
    public static class UniqueID implements Service {
        public int id = 0;
        public synchronized int nextId() { return id++; }
        public void serve(InputStream i, OutputStream o) throws IOException {
            PrintWriter out = new PrintWriter(o);
            out.print("Your number: " + nextId() + "\n");
            out.close();
            i.close();
        }
    }
    public static class Control implements Service {
        Server server;
        String password;
        boolean connected = false;
        public Control(Server server, String password) {
            this.server = server;
            this.password = password;
        }
        public void serve(InputStream i, OutputStream o) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(i));
            PrintWriter out = new PrintWriter(o);
            String line;
            boolean authorized = false;
            synchronized(this) {
                if (connected) {
                    out.print("ALLOWED ONLY ONE CONNECTION.\n");
                    out.close();
                    return;
                }
                else connected = true;
            }
            for(;;) {
                out.print("> ");
                out.flush();
                line = in.readLine();
                if (line == null) break;
                try {
                    StringTokenizer t = new StringTokenizer(line);
                    if (!t.hasMoreTokens()) continue;
                    String command = t.nextToken().toLowerCase();
                    if (command.equals("password")) {
                        String p = t.nextToken();
                        if (p.equals(this.password)) {
                            out.print("OK\n");
                            authorized = true;
                        }
                        else out.print("WRONG PASSWORD\n");
                    }
                    else if (command.equals("add")) {
                        if (!authorized) out.print("PASSWORD NEEDED\n");
                        else {
                            String serviceName = t.nextToken();
                            Class serviceClass = Class.forName(serviceName);
                            Service service;
                            try {
                                service = (Service)serviceClass.newInstance();
                            }
                            catch (NoSuchMethodError e) {
                                throw new IllegalArgumentException("Service has to be no-argument constructor");
                            }
                            int port = Integer.parseInt(t.nextToken());
                            server.addService(service, port);
                            out.print("SERVICE ADDED\n");
                        }
                    }
                    else if (command.equals("remove")) {
                        if (!authorized) out.print("PASSWORD NEEDED\n");
                        else {
                            int port = Integer.parseInt(t.nextToken());
                            server.removeService(port);
                            out.print("SERVICE REMOVED\n");
                        }
                    }
                    else if (command.equals("max")) {
                        if (!authorized) out.print("PASSWORD NEEDED\n");
                        else {
                            int max = Integer.parseInt(t.nextToken());
                            server.new Listener().setMaxConnections(max);
                            out.print("CONNECTIONS LIMIT CHANGED\n");
                        }
                    }
                    else if (command.equals("status")) {
                        if (!authorized) out.print("PASSWORD NEEDED\n");
                        else server.new Listener().displayStatus(out);
                    }
                    else if (command.equals("help")) {
                        out.print("COMMANDS:\n\tpassword <password>\n\tadd <service> <port>\n\tremove <port>\n\tmax <max-connections>\n\tstatus\n\thelp\n\tquit\n");
                    }
                    else if (command.equals("quit")) break;
                    else out.print("UNKNOWN COMMAND\n");
                }
                catch (Exception e) {
                    out.print("ERROR DURING ANALYSIS OR EXECUTION OF PROGRAM:\n" + e + "\n");
                }
            }
            connected = false;
            out.close();
            in.close();
        }
    }
}
