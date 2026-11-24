module order.storage {
    requires net.datafaker;
    requires static lombok;

    exports org.example.model;
    exports org.example.storage;
    exports org.example.exception;
    // org.example.storage.internal is NOT exported (internal to this module)
}