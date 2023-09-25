package com.zs.application;

public class Message {
    private String sender, receiver, content, timestamp;
    private static int nextId = 1;
    private int id;
    boolean isRead;
    
    public Message(String sender, String receiver, String timestamp, String content, boolean isRead) {
        this.id = nextId++;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message Id: " + id + "\n\tFrom: " + sender + "\n\tTo: " + receiver + "\n\tSent at: " + timestamp + "\n\t" + content;
    }
}
