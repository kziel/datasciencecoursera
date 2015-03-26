/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runcamera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Krzysiek
 */
public class RunCamera {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("arp -a");
        BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        String ipAddrres = null;
        while ((line = is.readLine()) != null) {
            ipAddrres = findIp(line, "80-1f-02-7a-b4-60");
            if (ipAddrres != null) {
                //System.out.println(":" + ipAddrres.replaceAll("\\s", "") + ":");
                break;
            }
        }

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            System.err.println(e);
            return;
        }
        if (ipAddrres != null) {
            ipAddrres = ipAddrres.replaceAll("\\s", "");
            java.awt.Desktop.getDesktop().browse(new URI("http://" + ipAddrres));
        }
        System.exit(0);
    }

    private static String findIp(String line, String mac) {
        if (line.contains(mac)) {
            return line.subSequence(0, 15).toString();
        }
        return null;
    }
}
