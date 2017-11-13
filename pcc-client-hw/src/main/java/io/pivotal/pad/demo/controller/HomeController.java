package io.pivotal.pad.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


	@RequestMapping("/")
	public String home() {
		return "Customer Search Service -- Available APIs: <br/>"
				+ "<br/>"
				+ "GET /addcustomer?key={email}&value={name}  - insert a value <br/>"
				+ "GET /getcustomer?key={email}               - get specific value <br/>"
				+ "GET /deletecustomer?key={email}            - delete a value <br/>";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/addcustomer")
	@ResponseBody
	public String insertCustomerData(@RequestParam(value = "email", required = true)
	String email, @RequestParam(value = "value", required = true) String name)  {



		return "";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getcustomer")
	@ResponseBody
	public String getCustomerData(@RequestParam(value = "email", required = true)
					String email)  {


		return "";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/deletecustomer")
	@ResponseBody
	public String deleteCustomerData(@RequestParam(value = "email", required = true)
					String email)  {


		return "";
	}


}
