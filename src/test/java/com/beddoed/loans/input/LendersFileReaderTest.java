package com.beddoed.loans.input;

import com.beddoed.loans.domain.Lender;
import com.beddoed.loans.input.LendersFileReader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.util.List;

import static com.beddoed.loans.domain.Lender.LenderBuilder.lenderBuilder;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

public class LendersFileReaderTest {

    private static final String HEADER_ROW = "Lender,Rate,Available";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldRejectInvalidFilePath() throws FileNotFoundException {
        // Given
        final String fileName = "invalid-file-name.csv";
        expectedException.expect(FileNotFoundException.class);
        expectedException.expectMessage(format("File '%s' was not found on the filesystem", fileName));

        // When
        LendersFileReader.readLenders(fileName);
    }

    @Test
    public void shouldParseValidLenderFile() throws IOException {
        // Given
        final Lender lender1 = lenderBuilder().setName("Bob").setRate(0.075).setAvailable(640).build();
        final Lender lender2 = lenderBuilder().setName("Jane").setRate(0.069).setAvailable(480).build();
        final File temporaryFile = createTemporaryFile(lender1, lender2);

        // When
        final List<Lender> lenders = LendersFileReader.readLenders(temporaryFile.getAbsolutePath());

        // Then
        assertEquals("There should be two lenders", 2, lenders.size());
        assertEquals("Lender1 doesn't match expected", lender1, lenders.get(0));
        assertEquals("Lender2 doesn't match expected", lender2, lenders.get(1));
    }

    @Test
    public void shouldRejectEmptyFile() throws IOException {
        // Given
        final File temp = File.createTempFile("test-file", ".tmp");

        // Expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("File does not have any rows!");

        // When
        LendersFileReader.readLenders(temp.getAbsolutePath());
    }

    @Test
    public void shouldRejectWithOnlyHeaderRow() throws IOException {
        // Given
        final File temp = File.createTempFile("test-file", ".tmp");
        final PrintWriter printWriter = createPrintWriter(temp);
        printWriter.print(HEADER_ROW);
        printWriter.close();

        // Expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("File only contains header row!");

        // When
        LendersFileReader.readLenders(temp.getAbsolutePath());
    }

    @Test
    public void shouldRejectWhenFileContainsRowsWhichAreNotCommaSeparated() throws IOException {
        // Given
        final File temp = File.createTempFile("test-file", ".tmp");
        final PrintWriter printWriter = createPrintWriter(temp);
        printWriter.print(HEADER_ROW);
        final String badRow = "somerubbish";
        printWriter.print("\n" + badRow);
        printWriter.close();

        // Expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(String.format("Row is not in correct format: %s", badRow));

        // When
        LendersFileReader.readLenders(temp.getAbsolutePath());
    }

    @Test
    public void shouldRejectWhenFileContainsRowsWhichHaveRateValuesNotInCorrectFormat() throws IOException {
        // Given
        final File temp = File.createTempFile("test-file", ".tmp");
        final PrintWriter printWriter = createPrintWriter(temp);
        printWriter.print(HEADER_ROW);
        final String badValue = "Some String Which Is Invalid For Rate Field";
        final String badRow = "Some Name," + badValue + ",100";
        printWriter.print("\n" + badRow);
        printWriter.close();

        // Expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(String.format("Row value: '%s' cannot be parsed into double from line: '%s'", badValue, badRow));

        // When
        LendersFileReader.readLenders(temp.getAbsolutePath());
    }

    @Test
    public void shouldRejectWhenFileContainsEmptyLines() throws IOException {
        // Given
        final File temp = File.createTempFile("test-file", ".tmp");
        final PrintWriter printWriter = createPrintWriter(temp);
        printWriter.print("\n\n");
        printWriter.close();

        // Expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(String.format("Empty line found in file!"));

        // When
        LendersFileReader.readLenders(temp.getAbsolutePath());
    }

    @Test
    public void shouldRejectWhenFileContainsRowsWhichHaveAvailableValuesNotInCorrectFormat() throws IOException {
        // Given
        final File temp = File.createTempFile("test-file", ".tmp");
        final PrintWriter printWriter = createPrintWriter(temp);
        printWriter.print(HEADER_ROW);
        final String badValue = "Some String Which Is Invalid For Available Field";
        final String badRow = "Some Name,0.08," + badValue;
        printWriter.print("\n" + badRow);
        printWriter.close();

        // Expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(String.format("Row value: '%s' cannot be parsed into double from line: '%s'", badValue, badRow));

        // When
        LendersFileReader.readLenders(temp.getAbsolutePath());
    }

    private File createTemporaryFile(Lender... lenders) throws IOException {
        File temp = File.createTempFile("test-file", ".tmp");
        PrintWriter printWriter = createPrintWriter(temp);
        printWriter.print(HEADER_ROW);
        for (Lender lender : lenders) {
            printWriter.printf("\n%s,%f,%f", lender.getName(), lender.getRate(), lender.getAvailable());
        }
        printWriter.close();
        return temp;
    }

    private PrintWriter createPrintWriter(File temp) throws IOException {
        FileWriter fileWriter = new FileWriter(temp);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        return printWriter;
    }
}