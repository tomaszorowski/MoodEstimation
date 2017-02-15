package com.example.tomek.moodestimation.XmlWebService;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Response")
public class Response {

    public static final int RESPONSE_CODE_OK = 0;

    @XStreamAlias("reponseCode")
    @XStreamAsAttribute
    protected int responseCode = RESPONSE_CODE_OK;

    @XStreamAlias("message")
    @XStreamAsAttribute
    protected String responseMessage;

    @XStreamAlias("Parameters")
    protected Parameters parameters;

    @XStreamAlias("Body")
    protected Object body;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Parameters getParameters() {
        if (parameters==null) {
            parameters = new Parameters();
        }
        return parameters;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getParameter(String key, String defaultValue) {
        return getParameters().containsKey(key) ? getParameters().get(key) : defaultValue;
    }

    public String getParameter(String key) {
        return getParameter(key, null);
    }

    public void setParameter(String key, String value) {
        getParameters().put(key, value);
    }

    public void setParameters(String[] parameters) {
        getParameters().put(parameters);
    }

}
