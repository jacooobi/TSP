package put.poznan;

import put.poznan.Structures.Node;
import put.poznan.Structures.Nodes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataReader {
    private Path filePath;
    private Nodes nodes = new Nodes();

    public DataReader(String name) {
        String basePath = Paths.get(".").toAbsolutePath().normalize().toString();
        filePath = Paths.get(String.format("%s/data/%s.tsp", basePath, name));
    }

    public Nodes load() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath.toString()));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(" ");
                String id = parts[0];
                String posX = parts[1]; // 004
                String posY = parts[2];

                Node position = new Node();
                position.put("id", Integer.parseInt(id) - 1);
                position.put("x", Integer.parseInt(posX));
                position.put("y", Integer.parseInt(posY));

                this.nodes.add(position);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.nodes;
    }
}
