package unibl.etf.ip.webshop_ip2023.util;

import org.slf4j.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

@Service
public class LoggingServiceImpl implements LoggingService {

    Logger logger = LoggerFactory.getLogger("request.response");

    @Override
    public void displayReq(HttpServletRequest request, Object body) {
        StringBuilder reqMessage = new StringBuilder();
        Map<String, String> parameters = getParameters(request);

        reqMessage.append("REQUEST ");
        reqMessage.append("method = [").append(request.getMethod()).append("]");
        reqMessage.append(" path = [").append(request.getRequestURI()).append("] ");

        if (!parameters.isEmpty()) {
            reqMessage.append(" parameters = [").append(parameters).append("] ");
        }

        if (!Objects.isNull(body)) {
            reqMessage.append(" body = [").append(body).append("]");
        }

        logger.info("log Request: {}", reqMessage);
    }

    @Override
    public void displayResp(HttpServletRequest request, HttpServletResponse response, Object body) {
        StringBuilder respMessage = new StringBuilder();
        Map<String, String> headers = getHeaders(response);
        respMessage.append("RESPONSE ");
        respMessage.append(" method = [").append(request.getMethod()).append("]");
        if (!headers.isEmpty()) {
            respMessage.append(" ResponseHeaders = [").append(headers).append("]");
        }
        if (body != null)
            respMessage.append(" responseBody = [").append(body.toString().contains("error") ? " -- ERROR --" : body.toString()).append("]");
        else
            respMessage.append(" -- ERROR -- ");
        logger.info("log Response: {}", respMessage);
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        Collection<String> headerMap = response.getHeaderNames();
        for (String str : headerMap) {
            headers.put(str, response.getHeader(str));
        }
        return headers;
    }

    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        return parameters;
    }


}