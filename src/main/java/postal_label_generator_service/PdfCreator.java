package postal_label_generator_service;

import java.io.*;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import spark.Request;
import spark.Response;


public class PdfCreator {


    /**
     * The method creates a .pdf file of the given information to display a postal label.
     *
     * @param postalData List of Strings specified in getPostalData method
     * @return String - the name of the saved pdf file
     * @throws IOException file is not found
     * @throws COSVisitorException something gone wrong when visiting a PDF object
     * @see PostalLabelServiceController#getPostalData(Request, Response)
     */
    public static String convertPostalDataToPdf(List<String> postalData) throws IOException, COSVisitorException {
            String id = PdfCreator.generateId(postalData);
            String fileName = "PostalLabel" + id + ".pdf"; // name of our file

            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();

            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);
            for (int i = 0, y_pos = 740; i < 5; i++, y_pos -= 50) {
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 40);
                content.moveTextPositionByAmount(80, y_pos);
                content.drawString(postalData.get(i));
                content.endText();
            }

            content.close();
            doc.save(fileName);
            doc.close();
            return fileName;
    }

    /**
     * The method returns an individual id, based on the data the user provides. It takes the first two letters
     * of each entry and concats to make an id.
     *
     * @param listOfStrings List of Strings specified in getPostalData method
     * @return String - individual id
     * @see PostalLabelServiceController#getPostalData(Request, Response)
     */
    private static String generateId(List<String> listOfStrings) {
        String newID = "";
        for(String elem : listOfStrings) {
            newID = newID.concat(elem.substring(0, 2));
        }
        return newID;
    }

    /**
     * Opens the given file if present, reads the content and casts it to byteArray.
     *
     * @param filename String - The name of the saved .pdf
     * @return Content of the file as bytes
     * @throws IOException if file not found
     */
    public static byte[] convertToBytes(String filename) throws IOException {
        File file = new File(filename);

        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int readNum; (readNum = fis.read(buf)) != -1; ) {
            bos.write(buf, 0, readNum);
        }
    return bos.toByteArray();

    }
}
