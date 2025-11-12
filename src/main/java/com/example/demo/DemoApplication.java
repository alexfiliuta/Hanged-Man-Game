package com.example.demo;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.err.println("Application Started Successfully");
		Scanner sc = new Scanner(System.in);
		System.out.println(sc.nextLine());
		sc.close();

	}

	@Override
	public void run(String... args) throws Exception {
		// add startup logic here if needed
	}
}
