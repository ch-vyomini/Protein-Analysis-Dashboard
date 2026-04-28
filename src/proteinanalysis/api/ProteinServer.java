package proteinanalysis.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ProteinServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/protein", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                String query = exchange.getRequestURI().getQuery();
                String input;

                if (query != null && query.contains("=")) {
                    input = query.split("=")[1].toUpperCase();
                } else {
                    input = "P69905";
                }

                String sequence = "";
                String pdbId = input;
                String proteinInfo = "{}";

                // 🔥 CASE 1: UniProt
                if (input.length() > 4) {
                    sequence = ApiFetcher.getProteinSequence(input);
                    pdbId = ApiFetcher.getPDBfromUniProt(input);

                    if (pdbId.equals("NONE")) {
                        pdbId = "";
                    }
                }

                // 🔥 CASE 2: PDB
                if (input.length() == 4) {
                    sequence = ApiFetcher.getSequenceFromPDB(input);
                }

                // 🔥 Get metadata
                if (pdbId.length() == 4) {
                    proteinInfo = ApiFetcher.getProteinInfo(pdbId);
                }

                String response = "{"
                        + "\"sequence\":\"" + sequence + "\","
                        + "\"pdb\":\"" + pdbId + "\","
                        + "\"info\":" + proteinInfo
                        + "}";

                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Content-Type", "application/json");

                exchange.sendResponseHeaders(200, response.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.setExecutor(null);
        server.start();

        System.out.println("Server running at http://localhost:8080/protein?id=P69905");
    }
}





