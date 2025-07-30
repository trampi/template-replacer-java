package template.replacer.model;

import java.util.List;
import java.util.Optional;

public record Template(String source, Optional<List<Replacement>> replacements, List<Target> target) {
}

