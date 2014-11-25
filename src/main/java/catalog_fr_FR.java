import java.util.ListResourceBundle;

/**
 * Created by pierangeloc on 25-11-14.
 */
public class catalog_fr_FR extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"trousers", "pantalon"},
                {"shoes", "chaussures"},
                {"shirt", "chemise"}
        };
    }
}
