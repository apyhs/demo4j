package demo._common.serialize;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * de.ruedigermoeller.fst
 */
public class FstSerializeUtils {

    public static void serialize(Object object, OutputStream outputStream) throws IOException {
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException(FstSerializeUtils.class.getSimpleName() + " requires a Serializable payload " +
                    "but received an object of type [" + object.getClass().getName() + "]");
        }
        FSTObjectOutput output = new FSTObjectOutput(outputStream);
        output.writeObject(object);
        output.flush();
    }

    public static Object deserialize(InputStream inputStream) throws IOException {
        FSTObjectInput input = new FSTObjectInput(inputStream);
        try {
            return input.readObject();
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to deserialize object type", e);
        }
    }

}
