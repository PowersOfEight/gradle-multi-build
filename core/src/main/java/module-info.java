module edu.cofc.core {
    requires com.sun.xml.bind;
    requires jakarta.xml.bind;
    opens edu.cofc.core.serialize to jakarta.xml.bind;
    exports edu.cofc.core.serialize;
    exports edu.cofc.core.serialize.exception;
}