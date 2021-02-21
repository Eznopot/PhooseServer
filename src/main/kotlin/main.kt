fun main(args: Array<String>) {
    val server = UDPServeur()
    val EventHandler = EventHandler()
    while (true) {
        EventHandler.handleEvent(server.receiveUDP())
    }
}