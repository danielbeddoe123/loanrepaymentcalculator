package com.beddoed.loans.input;

import com.beddoed.loans.domain.Lender;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.beddoed.loans.domain.Lender.LenderBuilder.lenderBuilder;

public class LendersFileReader {

    public static List<Lender> readLenders(String fileName) throws FileNotFoundException {
        final File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + fileName + "' was not found on the filesystem");
        }

        return parseLenders(file);
    }

    private static List<Lender> parseLenders(File file) {
        int row = 0;
        final List<Lender> lenders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line == null) {
                throw new IllegalStateException("File does not have any rows!");
            }
            while (line != null) {
                if (row > 0) {
                    line = reader.readLine();
                    if (line != null) {
                        if (line.trim().isEmpty()) {
                            throw new IllegalStateException("Empty line found in file!");
                        }
                        Lender lender = parseLenderFromLine(line);
                        lenders.add(lender);
                    }
                }
                row++;
            }
            if (lenders.isEmpty()) {
                throw new IllegalStateException("File only contains header row!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lenders;
    }

    private static Lender parseLenderFromLine(String line) {
        final String[] splitLine = line.split(",");
        if (splitLine.length != 3) {
            throw new IllegalStateException("Row is not in correct format: " + line);
        }
        final double rate = getDouble(splitLine[1], line);
        final double available = getDouble(splitLine[2], line);
        return lenderBuilder()
                .setName(splitLine[0])
                .setAvailable(available)
                .setRate(rate)
                .build();
    }

    private static double getDouble(String rowValue, String line) {
        try {
            return Double.parseDouble(rowValue);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Row value: '" + rowValue + "' cannot be parsed into double from line: '" + line + "'");
        }
    }

}
