package com.example.tomek.moodestimation.XmlWebService;
import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
@XStreamConverter(Parameters.ParametersConverter.class)
@XStreamAlias("Parameters")
public class Parameters extends HashMap<String, String> {

    public Parameters() {}

    public void put(String[] values) {
        if (values!=null) {
            for (int i=0; i<values.length/2; i++) {
                put(values[i*2], values[i*2+1]);
            }
        }
    }


    public static class ParametersConverter implements Converter {

        @Override
        public boolean canConvert(Class cl) {
            return cl.equals(Parameters.class);
        }

        @Override
        public void marshal(java.lang.Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
            if (obj==null) return;
            Parameters map = (Parameters) obj;
            for (String key: map.keySet()) {
                writer.startNode(key);
                writer.setValue(map.get(key));
                writer.endNode();
            }
        }

        @Override
        public java.lang.Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            Parameters map = new Parameters();
            while (reader.hasMoreChildren()) {
                reader.moveDown();
                map.put(reader.getNodeName(), reader.getValue());
                reader.moveUp();
            }
            return map;
        }

    }

}
