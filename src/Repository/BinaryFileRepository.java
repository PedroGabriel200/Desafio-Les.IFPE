package Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Shared serialization support for repositories backed by a .bin file. */
abstract class BinaryFileRepository<T extends Serializable> {
    private final Path file;

    BinaryFileRepository(String fileName) {
        this.file = Path.of("data", fileName);
    }

    @SuppressWarnings("unchecked")
    protected List<T> readAll() throws IOException {
        if (Files.notExists(file)) return new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(file))) {
            return (List<T>) input.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new IOException("Não foi possível ler os dados de " + file, e);
        }
    }

    protected void writeAll(List<T> values) throws IOException {
        Files.createDirectories(file.getParent());
        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(file))) {
            output.writeObject(new ArrayList<>(values));
        }
    }
}
