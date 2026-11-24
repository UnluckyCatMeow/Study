module order.processing {
    requires java.base;
    requires transitive order.storage;

    exports org.example.processing;
    // org.example.processing.internal is NOT exported (internal to this module)
}