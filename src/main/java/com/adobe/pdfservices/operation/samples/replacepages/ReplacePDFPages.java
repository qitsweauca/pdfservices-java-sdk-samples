/*
 * Copyright 2019 Adobe
 * All Rights Reserved.
 *
 * NOTICE: Adobe permits you to use, modify, and distribute this file in
 * accordance with the terms of the Adobe license agreement accompanying
 * it. If you have received this file from a source other than Adobe,
 * then your use, modification, or distribution of it requires the prior
 * written permission of Adobe.
 */

package com.adobe.pdfservices.operation.samples.replacepages;

import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.exception.SdkException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.io.FileRef;
import com.adobe.pdfservices.operation.pdfops.ReplacePagesOperation;
import com.adobe.pdfservices.operation.pdfops.options.PageRanges;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This sample illustrates how to replace specific pages in a PDF file.
 * <p>
 * Refer to README.md for instructions on how to run the samples.
 */
public class ReplacePDFPages {

    // Initialize the logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplacePDFPages.class);

    public static void main(String[] args) {

        try {
            // Initial setup, create credentials instance.
            Credentials credentials = Credentials.serviceAccountCredentialsBuilder()
                    .fromFile("pdfservices-api-credentials.json")
                    .build();

            // Create an ExecutionContext using credentials and create a new operation instance.
            ExecutionContext executionContext = ExecutionContext.create(credentials);
            ReplacePagesOperation replacePagesOperation = ReplacePagesOperation.createNew();

            // Set operation base input from a source file.
            FileRef baseSourceFile = FileRef.createFromLocalFile("src/main/resources/baseInput.pdf");
            replacePagesOperation.setBaseInput(baseSourceFile);

            // Create a FileRef instance using a local file.
            FileRef firstInputFile = FileRef.createFromLocalFile("src/main/resources/replacePagesInput1.pdf");
            PageRanges pageRanges = getPageRangeForFirstFile();

            // Adds the pages (specified by the page ranges) of the input PDF file for replacing the
            // page of the base PDF file.
            replacePagesOperation.addPagesForReplace(firstInputFile, pageRanges, 1);


            // Create a FileRef instance using a local file.
            FileRef secondInputFile = FileRef.createFromLocalFile("src/main/resources/replacePagesInput2.pdf");

            // Adds all the pages of the input PDF file for replacing the page of the base PDF file.
            replacePagesOperation.addPagesForReplace(secondInputFile, 3);

            // Execute the operation
            FileRef result = replacePagesOperation.execute(executionContext);

            // Save the result at the specified location
            result.saveAs("output/replacePagesOutput.pdf");
        } catch (IOException | ServiceApiException | SdkException | ServiceUsageException e) {
            LOGGER.error("Exception encountered while executing operation", e);
        }
    }

    private static PageRanges getPageRangeForFirstFile() {
        // Specify pages of the first file for replacing the page of base PDF file.
        PageRanges pageRanges = new PageRanges();
        // Add pages 1 to 3.
        pageRanges.addRange(1, 3);

        // Add page 4.
        pageRanges.addSinglePage(4);

        return pageRanges;
    }

}
