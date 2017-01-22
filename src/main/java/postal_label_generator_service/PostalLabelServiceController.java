package postal_label_generator_service;


import org.apache.pdfbox.exceptions.COSVisitorException;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Response;
import spark.Request;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class PostalLabelServiceController {

    private static PostalLabelServiceController INSTANCE;

    /**
     * @return only instance of the class
     */
    public static PostalLabelServiceController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostalLabelServiceController();
        }
        return INSTANCE;
    }

    private PostalLabelServiceController() {}


    /**
     * @param request Request object
     * @param response Response object
     * @return status
     * @throws JSONException problem with the JSON API
     * @throws COSVisitorException something gone wrong when visiting a PDF object
     * @throws IOException if file not found
     */
    public int getPostalData(Request request, Response response) throws JSONException, COSVisitorException, IOException {
        JSONObject json = new JSONObject(request.body());
        List<String> postalData = new ArrayList<>();
        postalData.add(json.getString("name"));
        postalData.add(json.getString("country"));
        postalData.add(json.getString("city"));
        postalData.add(json.getString("address"));
        postalData.add(json.getString("zipcode"));

        String fileName = PdfCreator.convertPostalDataToPdf(postalData);
        response.header("Content-Type", "application/pdf");
        response.header("Content-Disposition", "attachment;filename="+ fileName);
        ServletOutputStream outputStream = response.raw().getOutputStream();
        outputStream.write(PdfCreator.convertToBytes(fileName));
        outputStream.flush();
        outputStream.close();
        return 200;
    }

}
