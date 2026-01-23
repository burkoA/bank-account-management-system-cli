package utils;

import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;

public class JsonCreator {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File userFile = new File("users.json");
    private static final File transactionFile = new File("transaction.json");

    public static void createUsersJsonFiles() {
        createFileIfMissing(userFile);
    }

    public static void createTransactionJsonFiles() {
        createFileIfMissing(transactionFile);
    }

    private static void createFileIfMissing(File file) {
        if (!file.exists()) {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, new ArrayList<>());
        }
    }
}
