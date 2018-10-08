package ImportB2;

import ImportB2.service.helper.ServiceHelper;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AppTest {

    @Test
    public void shouldCreateContactDto() {

        ServiceHelper serviceHelper = new ServiceHelper();
        assertNotNull(serviceHelper.createContactDto("test1"));
    }
}
