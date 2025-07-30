package template.replacer;

import template.replacer.model.Replacement;
import template.replacer.model.Template;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        var options = ArgumentsParser.from(args);
        TemplateParser templateParser = new TemplateParser();
        ArrayList<String> templateFiles = options.templates();

        if (templateFiles.isEmpty()) {
            System.out.println("No templates provided, exiting...");
            return;
        }

        templateFiles.forEach(templateFile -> {
            try {
                System.out.println("Processing template: " + templateFile);
                var templates = templateParser.read(new FileInputStream(templateFile));
                for (Template template : templates) {
                    var transformedTargets = applyTemplate(template);
                    if (options.dryRun()) {
                        transformedTargets.forEach(target -> {
                            System.out.println("Source path: " + templateFile);
                            System.out.println("Target path: " + target.path());
                            System.out.println("---- transformed content begin ----");
                            target.lines().forEach(System.out::println);
                            System.out.println("---- transformed content end ----");
                            System.out.println("\n\n");
                        });
                    } else {
                        for (TransformedTarget target : transformedTargets) {
                            try {
                                System.out.println("Writing " + target.path() + "...");
                                Files.write(Path.of(target.path()), target.lines());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static List<TransformedTarget> applyTemplate(Template template) throws IOException {
        List<String> sourceLines = Files.readAllLines(Path.of(template.source()));
        return template.target().stream().map(target -> {
            ArrayList<Replacement> allReplacementRules = new ArrayList<>();
            allReplacementRules.addAll(target.replacements().orElse(Collections.emptyList()));
            allReplacementRules.addAll(template.replacements().orElse(Collections.emptyList()));

            ArrayList<String> targetLines = new ArrayList<>(sourceLines.size());
            for (int i = 0; i < sourceLines.size(); i++) {
                targetLines.add(runReplacement(sourceLines, i, allReplacementRules));
            }

            return new TransformedTarget(target.path(), targetLines);
        }).collect(Collectors.toList());
    }

    private static String runReplacement(List<String> lines, int lineIndex, ArrayList<Replacement> allReplacements) {
        var line = lines.get(lineIndex);
        for (Replacement replacement : allReplacements) {
            if (replacement.line().orElse(-1) == lineIndex + 1 && line.contains(replacement.from())) {
                return line.replace(replacement.from(), replacement.to());
            } else if (replacement.line().isEmpty() && line.contains(replacement.from())) {
                return line.replace(replacement.from(), replacement.to());
            }
        }
        return line;
    }

}
