package com.project.transaction.authorizer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionAuthorizerApplicationTests {

	@Test
	void contextLoads() {
	}

}

//{"account": {"id": 1, "active-card": true, "available-limit": 100}}
//{"transaction": {"id": 1, "merchant": "Burger King", "amount": 90,"time":"2019-02-13T10:00:00.000Z"}}

// {"account": {"id": 1, "active-card": true, "available-limit": 100}}
// {"account": {"id": 2, "active-card": true, "available-limit": 350}}
// {"account": {"id": 4, "active-card": false, "available-limit": 350}}