package postal_label_generator_service;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class PdfCreatorTest {

    @Test
    public void testConvertPostalDataToPdf() throws IOException, COSVisitorException {
        ArrayList<String> data = new ArrayList<>();
        data.add("Szerep Elek");
        data.add("Hungary");
        data.add("Budapest");
        data.add("Nagymező utca 5.");
        data.add("1000");
        assertEquals("PostalLabelSzHuBuNa10.pdf", PdfCreator.convertPostalDataToPdf(data));
    }

    @Test
    public void convertToBytes() throws Exception {

    }

}