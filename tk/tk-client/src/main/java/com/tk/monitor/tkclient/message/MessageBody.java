package com.tk.monitor.tkclient.message;


/**
 *
 * 消息载体
 * @author baron
 */
public class MessageBody {

    private String Type;
    private String Content;
    private String Property;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getProperty() {
        return Property;
    }

    public void setProperty(String property) {
        Property = property;
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "Type='" + Type + '\'' +
                ", Content='" + Content + '\'' +
                ", Property='" + Property + '\'' +
                '}';
    }
}
