package Export;

import java.io.*;

public class ExportCreator {

    private static final String header = "zeit,montag,dienstag,mittwoch,donnerstag,freitag";
    private static final String seperator ="\n";



    public static void writeCSV(String fileName, String table) {

        FileWriter writer = null;

        String[] rows = table.toString().split(";");

        try {

            writer = new FileWriter(fileName);
            writer.append(header.toString());
            writer.append(seperator);

            for (int i = 0; i < rows.length; i++) {

                writer.append(rows[i].toString());
                writer.append(seperator);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
