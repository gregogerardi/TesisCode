package edu.isistan.mobileGrid.network;


public interface Node {

    /**
     * Gets the id of the node.
     *
     * @return The id of the node.
     */
    int getId();

    /**
     * Starts a transfer
     *
     * @param scr
     * @param id
     */
    void incomingData(Node scr, int id);

    /**
     * Receive a job or update message from other node.
     *
     * @param message
     */
    void onMessageReceived(Message<?> message);

    /**
     * ACK notification to the sender
     *
     * @param message
     */
    void onMessageSentAck(Message message);

    /**
     * Fail notification to the sender
     *
     * @param message The message that failed to be received.
     */
    void fail(Message message);

    boolean isOnline();

    /**
     * Notifies a transfer starting
     *
     * @param dst
     * @param id
     * @param data
     */
    void startTransfer(Node dst, int id, Object data);

    /**
     * Notifies transfer error
     *
     * @param scr
     * @param id
     */
    void failReception(Node scr, int id);

    /**
     * Indicates the type of node according to its source of power
     */
    boolean runsOnBattery();

    boolean isSending();

    boolean isReceiving();
}
