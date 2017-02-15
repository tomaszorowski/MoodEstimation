package com.example.tomek.moodestimation.XmlWebService;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamHelper<Model> {


    public XStreamHelper() {

    }


    public String parseModelToString(Model model){
        XStream xstream = new XStream(new DomDriver());
        return xstream.toXML(model);
    }
    public Model parseStringToModel(String stringModel){
        XStream xstream1 = new XStream();
        Model model = (Model)xstream1.fromXML(stringModel);
        return model;
    }
}