package main;


import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.UUID;

public class Server {

  public static void main(String[] args) {

    // run a server instance to talk to ...
    Server server = new Server();
    server.run();
  }

  private Ignite ignite;

  public Server() {
    IgniteConfiguration configuration = new IgniteConfiguration();
    configuration.setClientMode(false);
    configuration.setConsistentId("42");
    configuration.setGridName("foServer");
    ignite = Ignition.start(configuration);
  }

  public void run() {

    System.out.println("running");
    while(true) {

    }
  }
}
