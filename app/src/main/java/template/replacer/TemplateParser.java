package template.replacer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import template.replacer.model.Template;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TemplateParser {

    private final ObjectMapper mapper;

    public TemplateParser() {
        this.mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public List<Template> read(InputStream resourceAsStream) throws IOException {
        return mapper.readValue(
                resourceAsStream,
                mapper.getTypeFactory().constructCollectionType(List.class, Template.class)
        );
    }

}
