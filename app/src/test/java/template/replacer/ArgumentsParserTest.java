package template.replacer;

import junit.framework.TestCase;
import org.junit.Test;

public class ArgumentsParserTest extends TestCase {

    @Test()
    public void testArgumentParsingWithD() {
        String[] args = new String[]{"-d", "file1", "file2"};
        ArgumentsParser argumentsParser = ArgumentsParser.from(args);
        assertTrue(argumentsParser.dryRun());
        assertEquals(2, argumentsParser.templates().size());
        assertEquals("file1", argumentsParser.templates().get(0));
        assertEquals("file2", argumentsParser.templates().get(1));
    }

    @Test()
    public void testArgumentParsingWithDryRun() {
        String[] args = new String[]{"--dry-run", "file1", "file2"};
        ArgumentsParser argumentsParser = ArgumentsParser.from(args);
        assertTrue(argumentsParser.dryRun());
        assertEquals(2, argumentsParser.templates().size());
        assertEquals("file1", argumentsParser.templates().get(0));
        assertEquals("file2", argumentsParser.templates().get(1));
    }

    @Test()
    public void testArgumentParsingWithDry() {
        String[] args = new String[]{"--dry", "file1", "file2"};
        ArgumentsParser argumentsParser = ArgumentsParser.from(args);
        assertTrue(argumentsParser.dryRun());
        assertEquals(2, argumentsParser.templates().size());
        assertEquals("file1", argumentsParser.templates().get(0));
        assertEquals("file2", argumentsParser.templates().get(1));
    }

    @Test()
    public void testArgumentParsingWithoutDryRun() {
        String[] args = new String[]{"file1", "file2"};
        ArgumentsParser argumentsParser = ArgumentsParser.from(args);
        assertFalse(argumentsParser.dryRun());
        assertEquals(2, argumentsParser.templates().size());
        assertEquals("file1", argumentsParser.templates().get(0));
        assertEquals("file2", argumentsParser.templates().get(1));
    }

}