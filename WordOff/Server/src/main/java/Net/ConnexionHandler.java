package Net;

import java.io.*;

public class ConnexionHandler implements IClientHandler {
    @Override
    public void handleClientConnection(InputStream is, OutputStream os) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));

        pw.println("Hello");
        br.readLine();
    }
}
