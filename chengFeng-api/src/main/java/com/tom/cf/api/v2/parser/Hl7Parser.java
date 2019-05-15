package com.tom.cf.api.v2.parser;

import com.tom.cf.api.v2.model.Digit;
import com.tom.cf.api.v2.model.Ecg;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

public class Hl7Parser extends FileParser{

    private SAXReader reader = new SAXReader();

    public Hl7Parser(){}

    public Hl7Parser(File ecgFile){
        super(ecgFile);
    }

    @Override
    public Ecg doEcgParser() {
        Ecg ecg = Ecg.newInstance();
        Digit digit = null;
        try {
            Document doc = reader.read(ecgFile);
            Element root=doc.getRootElement();
            Element sequenceSet=root.element("component").element("series").element("component").element("sequenceSet");
            @SuppressWarnings("unchecked")
            List<Element> list=sequenceSet.elements("component");
            for(int i=0;i<list.size();i++){
                Element sequence=list.get(i).element("sequence");
                String codeStr=sequence.element("code").attributeValue("code");

                Element valueElement = sequence.element("value");

                // 此处默认为 0.001s
                Element incrementElement = valueElement.element("increment");
                if(incrementElement != null){
                    String incrementStr = incrementElement.attributeValue("value");
                    ecg.setIncrement(Double.parseDouble(incrementStr));
                }
                Element scaleElement = valueElement.element("scale");
                if(scaleElement == null){
                    continue;
                }
                String scaleValueStr = scaleElement.attributeValue("value");
                String scaleUnitStr = scaleElement.attributeValue("unit");

                String datastr=sequence.element("value").elementText("digits");
                if(datastr!=null){
                    datastr=datastr.replace(" ", ",");
                    if(datastr.lastIndexOf(",")==(datastr.length()-1)){
                        datastr=datastr.substring(0, datastr.lastIndexOf(","));
                    }
                }
                digit = new Digit();
                digit.setCode(codeStr);
                digit.setScale( Double.parseDouble(scaleValueStr));
                digit.setUnit(scaleUnitStr);
                digit.setDigits(datastr.split(","));

                ecg.addDigit(digit);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return ecg;
    }

    @Override
    public boolean isSupportFileType(File ecgFile) {
        String fileType = ecgFile.getName().substring(ecgFile.getName().lastIndexOf(".")+1);
        return "xml".equalsIgnoreCase(fileType);
    }
}
