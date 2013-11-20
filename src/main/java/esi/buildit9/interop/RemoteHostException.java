package esi.buildit9.interop;

import com.sun.jersey.api.client.ClientResponse;
import esi.buildit9.rest.util.HttpHelpers;

public class RemoteHostException extends InteropException {

    public final int status;

    public RemoteHostException(ClientResponse response) {
        super(HttpHelpers.readAsUtf8(response));
        status = response.getStatus();
    }
}
