package edu.isistan.seas.node.jobstealing;

public class JSMessage {

    public static final int STEAL_REQUEST_TYPE = 0x10000000;

    private int requestType;

    public JSMessage(int requestType) {
        this.requestType = requestType;
    }

    public int getRequestType() {
        return requestType;
    }
}
