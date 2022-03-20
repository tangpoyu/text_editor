package model;

import java.io.File;

public class Icon extends Prototype{
    private String URL="https://picsum.photos/";
    private String name;
    private String size;
    private String path;
    private String className;
    private ImageDownloader imageDownloader = new ImageDownloader();

    public void setName(String name){
        this.name=name;
    }

    public void setClassName(String className){ this.className = className; }

    public void setSize(String size){
        this.size=size;
        mergeUrl();
        path=imageDownloader.downloadImageFromUrl(URL,"D:"+ File.separator, name);
    }

    public String getPath(){
        return path;
    }

    public String getClassName(){
        return className;
    }

    public void mergeUrl(){
        URL=URL+size;
    }

}
