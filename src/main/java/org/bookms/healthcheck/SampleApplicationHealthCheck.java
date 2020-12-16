package org.bookms.healthcheck;

import com.codahale.metrics.health.HealthCheck;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class SampleApplicationHealthCheck extends HealthCheck {

    private final Client client;

    public SampleApplicationHealthCheck(Client client) {
        this.client = client;
    }

    @Override
    protected Result check() throws Exception {
        WebTarget webTarget = client.target("http://localhost:8085/fkms/author/1001");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        @SuppressWarnings("rawtypes")
        ArrayList employees = response.readEntity(ArrayList.class);
        if(employees !=null && employees.size() > 0){
            return Result.healthy();
        }
        return Result.unhealthy("API Failed");
    }
}
