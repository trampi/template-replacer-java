package template.replacer.model;

import java.util.Optional;

public record Replacement(String from, String to, Optional<Integer> line) {
}
