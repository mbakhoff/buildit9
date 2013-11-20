package esi.buildit9.rest.util;

import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class HttpHelpers {

    public static String readAsUtf8(ClientResponse response) {
        InputStream in = null;
        try {
            in = response.getEntityInputStream();
            byte[] bytes = IOUtils.toByteArray(in);
            return new String(bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static String getStack(Exception ex) {
        StringWriter writer = new StringWriter();
        PrintWriter printer = new PrintWriter(writer);
        ex.printStackTrace(printer);
        printer.flush();
        return writer.getBuffer().toString();
    }
}
