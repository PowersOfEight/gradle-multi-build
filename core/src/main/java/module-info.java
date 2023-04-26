module edu.cofc.core {
//    requires xstream;
//    requires io.github.xstream.mxparser;
//    opens edu.cofc.core.serialize to xstream;
    requires com.sun.xml.bind;
    requires jakarta.xml.bind;
    exports edu.cofc.core.serialize;
}