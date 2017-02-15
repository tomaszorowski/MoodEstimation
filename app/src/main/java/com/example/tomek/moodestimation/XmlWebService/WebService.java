package com.example.tomek.moodestimation.XmlWebService;

        import android.os.AsyncTask;
        import android.util.Log;
        import com.thoughtworks.xstream.XStream;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;


public class WebService {

    protected XStream xstream;
    protected String webServiceUrl;
    protected String token;

    public WebService(String webServiceUrl) {
        xstream = new XStream();
        xstream.processAnnotations(new Class[] {
                Response.class,
                Request.class,
                Parameters.class
        });
        this.webServiceUrl = webServiceUrl;
    }

    public IAbortable request(Request request, RequestCallback callback) {

        RequestTask task = new RequestTask(request, callback);
        task.execute();
        return task;
    }

    public Request createRequestInstance(String action, String[] parameters) {
        Request request = createRequestInstance(action);
        if (parameters!=null) {
            request.setParameters(parameters);
        }
        return request;
    }

    public Request createRequestInstance(String action) {
        Request request = new Request();
        request.setAction(action);
        request.setToken(getToken());
        return request;
    }



    public XStream getXStream() {
        return xstream;
    }

    public String getWebServiceUrl() {
        return webServiceUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    // /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\

    protected class RequestTask extends AsyncTask<Request, Void, Object> implements IAbortable {

        protected Request request;
        protected RequestCallback callback;

        RequestTask(Request request, RequestCallback callback) {
            this.request = request;
            this.callback = callback;
        }

        @Override
        protected Object doInBackground(Request... requests) {
            try {
                Log.i("URL: ", getWebServiceUrl());
                URL url = new URL(getWebServiceUrl());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                getXStream().toXML(request, outputStream);
                outputStream.flush();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                Object result = xstream.fromXML(inputStream);
                inputStream.close();

                connection.disconnect();

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            if (Exception.class.isInstance(result)) {
                callback.OnException((Exception) result);
            }
            else {
                try {
                    Response response = (Response) result;
                    if (response.getResponseCode() != Response.RESPONSE_CODE_OK) {
                        callback.OnError(response);
                    } else {
                        callback.OnSuccess(response);
                    }
                } catch (Exception e) {
                    callback.OnException(e);
                }
            }
        }

        @Override
        public void abort() {
            cancel(true);
        }
    }

    public interface RequestCallback {
        void OnSuccess(Response response);
        void OnError(Response response);
        void OnException(Exception exception);
    }


    public interface IAbortable {
        void abort();
    }

}
