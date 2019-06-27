package jh.apple.documentstore

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DocumentStoreApplication {

	static void main(String[] args) {
		SpringApplication.run(DocumentStoreApplication, args)
	}
//    @Autowired
//    @Qualifier("applicationContentBootstrap")
//    private ApplicationContentBootstrapService bootspinner

//    @PostConstruct
//    void postConstruct() {
//        assert bootspinner
//        def result = bootspinner.spinUp()
//        assert result
//    }
}
