package template.replacer;

import java.util.ArrayList;

record ArgumentsParser(boolean dryRun, ArrayList<String> templates) {
    static ArgumentsParser from(String[] args) {
        boolean dryRun = false;
        ArrayList<String> files = new ArrayList<>();
        for (String arg : args) {
            if (arg.equals("-d") || arg.equals("--dry") || arg.equals("--dry-run")) {
                dryRun = true;
            } else {
                files.add(arg);
            }
        }
        return new ArgumentsParser(dryRun, files);
    }

}
