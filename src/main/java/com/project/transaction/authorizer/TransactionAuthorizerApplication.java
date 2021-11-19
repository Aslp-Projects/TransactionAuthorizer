package com.project.transaction.authorizer;

import com.project.transaction.authorizer.dto.AccountOutDto;
import com.project.transaction.authorizer.service.ITransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class TransactionAuthorizerApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(TransactionAuthorizerApplication.class, args);
		Scanner scanner = new Scanner(System.in);
		ITransactionService transactionService = applicationContext.getBean(ITransactionService.class);

		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (!line.isEmpty()) {
				try {
					AccountOutDto accountOutDto = transactionService.runProcess(line);
					System.out.println(accountOutDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}


//{"account": {"id": 1, "active-card": true, "available-limit": 100}}
//{"transaction": {"id": 1, "merchant": "Burger King", "amount": 90,"time":"2019-02-13T10:00:00.000Z"}}

// {"account": {"id": 1, "active-card": true, "available-limit": 100}}
// {"account": {"id": 2, "active-card": true, "available-limit": 350}}
// {"account": {"id": 4, "active-card": false, "available-limit": 350}}