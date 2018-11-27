package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.lang.reflect.Type;
import java.util.List;

/**
 * https://github.com/joffrey-bion/livedoc/tree/master/livedoc-core/src/main/java/org/hildan/livedoc/core/scanners/properties
 * 
 * A component that inspects the list of properties of a given type. This is
 * used to generate the documentation of the properties, and also to explore
 * types recursively. The definition of a property is decided by the
 * implementations of this interface.
 *
 * @see Property
 */
public interface PropertyScanner {

    /**
     * Gets the properties of the given type, as defined by the implementation.
     *
     * @param type
     *            the type to get the properties of
     *
     * @return the properties of the given type
     */
    List<Property> getProperties(Type type);
}
