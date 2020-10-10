package fmfi.dalekohlad.Communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Communication {
    private static final Logger lgr = LogManager.getLogger(Communication.class);

    public void periodic_update(String host, int port) throws IOException {
        // TODO
        lgr.debug(String.format("Connecting to %s:%d", host, port));
        Socket sock = new Socket(host, port);
        DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
    }

}
