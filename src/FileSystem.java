import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileSystem {

    // Замените на свою фамилию и имя
    private static final String SURNAME = "Potanin";
    private static final String NAME = "Philipp";

    public static void main(String[] args) throws IOException {
        Path root = Paths.get(SURNAME);
        Files.createDirectories(root);

        Path nameFile = root.resolve(NAME);
        createFileIfMissing(nameFile);

        Path dir3 = root.resolve("dir1/dir2/dir3");
        Files.createDirectories(dir3);

        Files.copy(
                nameFile,
                dir3.resolve(NAME),
                StandardCopyOption.REPLACE_EXISTING
        );

        Path file1 = root.resolve("dir1/file1");
        Path file2 = root.resolve("dir1/dir2/file2");

        createFileIfMissing(file1);
        createFileIfMissing(file2);

        System.out.println("Содержимое директории " + SURNAME + ":");

        try (Stream<Path> paths = Files.walk(root)) {
            paths.forEach(path -> {
                String prefix = Files.isDirectory(path) ? "D: " : "F: ";
                String relative = root.relativize(path).toString();

                if (relative.isEmpty()) {
                    relative = root.getFileName().toString();
                }

                System.out.println(prefix + relative);
            });
        }

        Path dir1 = root.resolve("dir1");
        deleteRecursively(dir1);

        System.out.println("Директория " + dir1 + " удалена.");
    }

    private static void createFileIfMissing(Path file) throws IOException {
        if (Files.notExists(file)) {
            Files.createFile(file);
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (Files.notExists(path)) {
            return;
        }

        try (Stream<Path> paths = Files.walk(path)) {
            paths.sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }
}