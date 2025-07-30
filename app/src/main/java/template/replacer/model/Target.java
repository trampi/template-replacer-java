package template.replacer.model;

import java.util.List;
import java.util.Optional;

public record Target(String path, Optional<List<Replacement>> replacements) {
}
