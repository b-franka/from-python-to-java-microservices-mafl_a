package postal_label_generator_service;


import java.net.URISyntaxException;
import static spark.Spark.*;

public class PostalLabelGeneratorService {
    /**
     * @param args
     */
    public static void main(String[] args) {

        PostalLabelServiceController service = PostalLabelServiceController.getInstance();

        setup(args);

        // --- MAPPINGS ---
        get("/pdf", service::getPostalData);

        get("/", (request, response) -> {
            response.redirect("http://images-cdn.9gag.com/photo/24566_700b.jpg");
            return null;
        });

        // --- EXCEPTION HANDLING ---
        exception(URISyntaxException.class, (exception, request, response) -> {
            response.status(500);
            response.body(String.format("URI building error, maybe wrong format? : %s", exception.getMessage()));
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body(String.format("Unexpected error occurred: %s", exception.getMessage()));
        });
    }

    /**
     * @param args
     */
    private static void setup(String[] args) {
        if (args == null || args.length == 0) {
//            logger.error("Port must be given as the first argument.");
            System.exit(-1);
        }

        try {
            port(Integer.parseInt(args[0]));
        } catch (NumberFormatException e) {
//            logger.error("Invalid port given '{}', it should be number.", args[0]);
            System.exit(-1);
        }
    }
}
