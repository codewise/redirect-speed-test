package com.codewise.gtmetrix.output;

import com.codewise.gtmetrix.test.data.TestResult;

public interface OutputHandler extends AutoCloseable {

    void saveTestResult(TestResult testResult);

    @Override
    void close();

    static OutputHandler getHandlerForFile(String fileName) {
        int extensionBeginningIndex = fileName.lastIndexOf(".") + 1;
        String fileExtension = fileName.substring(extensionBeginningIndex).toLowerCase();
        return switch (fileExtension) {
            case "csv" -> new CsvOutputHandler(fileName);
            default -> throw new UnknownOutputHandlerException(fileExtension);
        };
    }
}
