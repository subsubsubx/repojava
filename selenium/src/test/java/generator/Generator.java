package generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.Common;
import model.ContactData;
import model.GroupData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.Common.randomString;

public class Generator {

    @Parameter(names = {"--type", "-t"})
    String type;

    @Parameter(names = {"--output", "-o"})
    String output;


    @Parameter(names = {"--format", "-f"})
    String format;

    @Parameter(names = {"--count", "-c"})
    int count;


    public static void main(String[] args) throws IOException {
        Generator generator = new Generator();
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);
        generator.run();
    }

    private void run() throws IOException {
        Object data = generate();
        save(data);
    }

    private void save(Object data) throws IOException {
        if (format.equals("json")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String json = mapper.writeValueAsString(data);
            try (FileWriter writer = new FileWriter(output)) {
                writer.write(json);                    // selenium/src/test/resources/tmp/
            }
            System.out.println(output + " created");
        } else throw new IllegalArgumentException("Unknown format " + format);
    }

    private Object generate() throws IOException {
        if (type.equals("groups")) {
            return generateGroups();
        } else if (type.equals("contacts")) {
            return generateContacts();
        } else throw new IllegalArgumentException("Unknown type " + type);
    }

    private Object generateGroups() throws IOException {
        List<GroupData> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new GroupData()
                    .withName(randomString(i * 10))
                    .withHeader(randomString(i * 9))
                    .withFooter(randomString(i * 8)));
        }
        return list;
    }

    private Object generateContacts() {
        List<ContactData> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new ContactData()
                    .withFirstname(Common.randomString(i * 5))
                    .withLastname(Common.randomString(i * 5)));
        }
        return list;
    }
}
