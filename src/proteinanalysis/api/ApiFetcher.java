package proteinanalysis.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiFetcher {

    // ✅ UniProt sequence
    public static String getProteinSequence(String proteinId) {
        try {
            String urlString = "https://rest.uniprot.org/uniprotkb/" + proteinId + ".fasta";
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            StringBuilder sequence = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(">")) {
                    sequence.append(line.trim());
                }
            }

            reader.close();

            return sequence.toString();

        } catch (Exception e) {
            return "";
        }
    }

    // ✅ NEW: PDB sequence fetch
    public static String getSequenceFromPDB(String pdbId) {
        try {
            String urlString = "https://data.rcsb.org/rest/v1/core/polymer_entity/" + pdbId + "/1";
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();

            String json = result.toString();

            int index = json.indexOf("\"pdbx_seq_one_letter_code_can\":\"");
            if (index != -1) {
                int start = index + 34;
                int end = json.indexOf("\"", start);
                return json.substring(start, end).replace("\\n", "");
            }

        } catch (Exception e) {
            return "";
        }

        return "";
    }

    // ✅ UniProt → PDB mapping
    public static String getPDBfromUniProt(String id) {
        try {
            URL url = new URL("https://rest.uniprot.org/uniprotkb/" + id + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();

            String json = result.toString();

            int index = json.indexOf("\"database\":\"PDB\"");
            if (index != -1) {
                int idIndex = json.indexOf("\"id\":\"", index) + 6;
                return json.substring(idIndex, idIndex + 4);
            }

        } catch (Exception e) {
            return "NONE";
        }

        return "NONE";
    }

    // ✅ PDB metadata
    public static String getProteinInfo(String pdb) {
        try {
            URL url = new URL("https://data.rcsb.org/rest/v1/core/entry/" + pdb);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();

            return result.toString();

        } catch (Exception e) {
            return "{}";
        }
    }
}

