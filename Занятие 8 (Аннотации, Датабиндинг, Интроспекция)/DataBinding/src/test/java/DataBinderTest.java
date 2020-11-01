import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataBinderTest {
    DataBinder binder = new DataBinder();

    @Test
    public void testBinder() throws IOException {
        Path dataPath = Paths.get("src/main/resources", "FilmList(Data).xml");
        Path resultPath = Paths.get("src/main/resources", "ActorList(Result).xml");
        String xml = new String(Files.readAllBytes(dataPath));
        binder.fromXml(xml);
        String resultXml = binder.toXml();
        Assertions.assertEquals(new String(Files.readAllBytes(resultPath)), resultXml);
    }

}
