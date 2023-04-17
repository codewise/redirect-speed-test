package com.codewise.gtmetrix.output;

import com.codewise.gtmetrix.test.data.TestResult;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CsvOutputHandler implements OutputHandler {

    private static final Logger log = LogManager.getLogger(CsvOutputHandler.class);
    private static final String[] HEADERS = {"test id", "report id", "report type", "url", "location", "browser",
            "attempt", "redirect duration in millis"};
    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS)
            .build();

    private final CSVPrinter csvPrinter;

    @SneakyThrows
    public CsvOutputHandler(String fileName) {
        log.debug("Opening test result file {}", fileName);
        BufferedWriter writer =
                Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        csvPrinter = new CSVPrinter(writer, CSV_FORMAT);
        log.debug("Writing csv headers");
        csvPrinter.flush();
        log.debug("Wrote csv headers");
    }

    @SneakyThrows
    public void saveTestResult(TestResult testResult) {
        log.debug("Saving test result: {}", testResult);
        csvPrinter.printRecord(
                testResult.testId(),
                testResult.reportId(),
                testResult.reportType(),
                testResult.url(),
                testResult.location(),
                testResult.browser(),
                testResult.attemptNumber(),
                testResult.result()
        );
        csvPrinter.flush();
        log.debug("Saved test result: {}", testResult);
    }

    @SneakyThrows
    @Override
    public void close() {
        log.debug("Closing csv file");
        csvPrinter.close(true);
        log.debug("Closed csv file");
    }
}