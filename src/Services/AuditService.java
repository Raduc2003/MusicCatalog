package Services;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditService {

    private static final String AUDIT_FILE_PATH = "db_audit_log.csv";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public AuditService() {
        initializeCsvFile();
    }

    private void initializeCsvFile() {
        File file = new File(AUDIT_FILE_PATH);
        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(AUDIT_FILE_PATH))) {
                String[] header = {"Timestamp", "Operation", "Query"};
                writer.writeNext(header);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void log(String operation, String query) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(AUDIT_FILE_PATH, true))) {
            String timestamp = dateFormat.format(new Date());
            String[] record = {timestamp, operation, query};
            writer.writeNext(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
