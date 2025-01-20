package com.example.gestion_dart;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.repository.DartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication

public class GestionDartApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(GestionDartApplication.class, args);
	}
	@Autowired
	private DartRepository Repesitory ;

	@Override
	public void run(String... args) throws Exception {


	}
}
