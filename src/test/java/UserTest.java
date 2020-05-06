import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roger.crm.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class, MockServletContext.class })
public class UserTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void LoginAdminTest() throws Exception {

		JSONObject json = new JSONObject() ;
		json.put("username", "admin") ;
		json.put("password", "admin") ;
		RequestBuilder request = post("/authenticate").
				contentType(MediaType.APPLICATION_JSON).
				content(json.toString());

		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("token").exists());
	}
	@Test
	public void LoginManagerTest() throws Exception {

		JSONObject json = new JSONObject() ;
		json.put("username", "manager") ;
		json.put("password", "manager") ;
		RequestBuilder request = post("/authenticate").
				contentType(MediaType.APPLICATION_JSON).
				content(json.toString());
		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("token").exists());
	}
	@Test
	public void LoginOperatorTest() throws Exception {

		JSONObject json = new JSONObject() ;
		json.put("username", "operator") ;
		json.put("password", "operator") ;
		RequestBuilder request = post("/authenticate").
				contentType(MediaType.APPLICATION_JSON).
				content(json.toString());

		mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("token").exists());
	}
}