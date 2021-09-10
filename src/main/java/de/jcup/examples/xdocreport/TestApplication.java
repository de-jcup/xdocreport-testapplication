package de.jcup.examples.xdocreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.document.DocumentKind;

public class TestApplication {

    public static void main(String[] args) throws Exception {
        new TestApplication().start();
    }

    private void start() throws IOException, XDocConverterException {
        
        System.out.println("**********************************************************************************" );
        System.out.println(" Demo application, reads a DOCX file ancd converts to PDF \n  see last line for output location ");
        System.out.println("**********************************************************************************" );

        /* read the example DOCX file: */
        File docxExampleFile = ensureExamplePDFFileExists();

        /* create a temp output path :*/
        Path tmpOutputPath = Files.createTempFile("example_xdocreport_docx2pdf", ".pdf");

        convertToPDF(docxExampleFile, tmpOutputPath);

        System.out.println("Wrote converted PDF to:" + tmpOutputPath.toAbsolutePath());

    }

    private File ensureExamplePDFFileExists() {
        File docxExampleFile = new File("./src/main/resources/example.docx");
        if (!docxExampleFile.exists()) {
            throw new IllegalStateException("file does not exist:" + docxExampleFile.getAbsolutePath());
        }
        return docxExampleFile;
    }

    private void convertToPDF(File docxExampleFile, Path tmpOutputPath)
            throws FileNotFoundException, XDocConverterException {
        /* convert to PDF */
        Options options = Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF);

        IConverter converter = ConverterRegistry.getRegistry().getConverter(options);

        InputStream in = new FileInputStream(docxExampleFile);
        OutputStream out = new FileOutputStream(tmpOutputPath.toFile());
        converter.convert(in, out, options);
    }

}
