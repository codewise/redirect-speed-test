package com.codewise.gtmetrix.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ConfigurationReader {

    private static final Logger log = LogManager.getLogger(ConfigurationReader.class);
    private static final Yaml YAML = new Yaml(new Constructor(Configuration.class, new LoaderOptions()));

    public Configuration readConfiguration(String fileName) {
        log.info("Reading configuration file {}", fileName);
        InputStream configurationFileAsStream = getClass().getClassLoader().getResourceAsStream(fileName);
        Configuration configuration = YAML.load(configurationFileAsStream);
        log.info("Successfully read configuration file {}. Configuration: {}", fileName, configuration);
        return configuration;
    }
}
