import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class FileManagerTest {

    FileManager manager = new FileManager();
    String path = "src\\test\\resources\\";

    @Test
    public void merge() {
        List<String> files = new ArrayList<>();
        files.add(path + "in1_1.txt");
        files.add(path + "in1_2.txt");
        files.add(path + "in1_3.txt");
        manager.merge(files, path + "out_1.txt");
        assertEqualsFileContent(new String[] {
                "Это был странный мир: по утрам за окном не пели птицы," +
                        " не шелестела трава, собаки лаяли совершенно беззвучно, а мужики,",
                "то и дело ругающиеся на улицах, широко раскрывали рты, но ни единого звука оттуда не доносилось." +
                        " В десять лет, ",
                "после тяжело перенесённой скарлатины, Костя почти оглох, и вот уже шестой год его окружал " +
                        "мир тишины и покоя.",
                "<s_tar>",
                "Он ещё помнил звуки, однако существовали они в его голове отдельно от людей, других живых " +
                        "существ и предметов,",
                "которые эти звуки производили. Это было, как если бы он видел тени, а самих предметов, " +
                        "эти тени отбрасывающих, видеть не мог.",
                "<s_tar>",
                "",
                "Слова «громкий», «звучно», «шуметь» не значили в этом мире ничего.",
                "Они напоминали символы давно исчезнувшей цивилизации, оставленные как загадка потомкам " +
                        "на стенах древней пещеры.",
                "Для того чтобы проникнуть в эту пещеру, у Кости недоставало ни сил, ни возможностей, ни средств.",
                "<s_tar>"
        }, path + "out_1.txt");
    }

    @Test
    public void separate() {
        manager.separate(path + "sep.txt");
        assertEqualsFileContent(new String[] {
                "<m_tar>", "Hello world!", "</m_tar>"
        }, path + "sep1.txt");
        assertEqualsFileContent(new String[] {
                "<m_tar>", "", "Do u feel good?", "", "</m_tar>"
        }, path + "sep2.txt");
        assertEqualsFileContent(new String[] {
                "<m_tar>", "That's nice!", "</m_tar>"
        }, path + "sep3.txt");
    }

    private void assertEqualsFileContent(String[] content, String file) throws AssertionError {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (String line: content) {
                assertEquals(line, reader.readLine());
            }
        } catch (IOException ex) {
            throw new AssertionError("File wasn't found");
        }
    }
}
