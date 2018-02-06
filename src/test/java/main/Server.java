package main;


import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  public static void main(String[] args) {

    // run a server instance to talk to ...
    Server server = new Server("externalServer");
    server.run();
  }

  private Ignite ignite;

  public Server(String theName) {

    Slf4jLogger gridLog = new Slf4jLogger(logger);


    IgniteConfiguration configuration = new IgniteConfiguration();
    configuration.setClientMode(false);
    //configuration.setConsistentId("42");
    configuration.setGridName(theName);
    configuration.setGridLogger(gridLog);

    ignite = Ignition.start(configuration);
  }

  public void run() {

    System.out.println("running");
    logger.error("running");
    while(true) {

    }
  }
}
