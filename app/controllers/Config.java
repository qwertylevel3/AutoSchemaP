package controllers;

/**
 * Created by Administrator on 2016/3/22.
 */
public class Config {
    private static Config p=null;

    private Config(){

    }
    public static Config getInstance(){
        if(p==null){
            p=new Config();
        }
        return p;
    }

    public String getXsdFilePath(){
        return xsdFilePath;
    }
    public String getIndexFilePath(){
        return indexFilePath;
    }
    public String getResultFilePath(){
        return resultFilePath;
    }
    public String getShowDetailFilePath(){
        return showDetailFilePath;
    }
    public String getOutputDirPath(){
        return outputDirPath;
    }


    private String xsdFilePath="C:\\Users\\Administrator\\asd\\easy.xsd";
    private String indexFilePath="C:\\Users\\Administrator\\asd\\output\\index.xml";
    private String resultFilePath="C:\\Users\\Administrator\\asd\\output\\result.xml";
    private String showDetailFilePath="C:\\Users\\Administrator\\asd\\output\\showDetail.xml";
    private String outputDirPath="C:\\Users\\Administrator\\asd\\output";
}
