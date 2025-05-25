package core.models.persistency;

import java.util.List;

public interface Reader<T> {
    List<T> readFromFile(String relativePath);
}