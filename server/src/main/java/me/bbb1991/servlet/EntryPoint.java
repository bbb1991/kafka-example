package me.bbb1991.servlet;

import com.google.gson.Gson;
import me.bbb1991.model.Employee;
import me.bbb1991.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Path("/entry")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(EntryPoint.class);

    private BaseService service = new BaseService();

    public EntryPoint() throws IOException {
        logger.info("Initializing endpoint");
    }

    @GET
    public String doGet() {
        logger.info("Incoming GET request!");
        Gson gson = new Gson();
        return gson.toJson(new Employee("Smith", "John", 25, "Tharsis, Mars", null));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String doPost(Employee employee) {
        logger.info("Incoming POST request");
        logger.info("Employee is: {}", employee);
        service.sendObjectToCunsumers(employee);
        return "{\"status\":\"OK\"}";
    }
}