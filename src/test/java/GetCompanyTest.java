import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class GetCompanyTest {
	@Autowired
	MockMvc mvc; //創建MockMvc類的物件

	@Test
	public void contextLoads() throws Exception {
		String uri = "/company";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		System.out.println(status);
		Assert.assertEquals("錯誤",200,status);
	}
}
