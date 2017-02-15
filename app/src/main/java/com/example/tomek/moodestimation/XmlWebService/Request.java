package com.example.tomek.moodestimation.XmlWebService;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Request")
public class Request {

    @XStreamAsAttribute
    @XStreamAlias("token")
    protected String token;

    @XStreamAsAttribute
    @XStreamAlias("action")
    protected String action;

    @XStreamAlias("Parameters")
    protected Parameters parameters;

    @XStreamAlias("Body")
    protected Object body;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Parameters getParameters() {
        if (parameters==null) {
            parameters = new Parameters();
        }
        return parameters;
    }



    public void setParameters(String[] parameters) {
        getParameters().put(parameters);
    }

    public String getParameter(String key, String defaultValue) {
        return getParameters().containsKey(key) ? getParameters().get(key) : defaultValue;
    }

    public String getParameter(String key) {
        return getParameter(key, null);
    }

}
