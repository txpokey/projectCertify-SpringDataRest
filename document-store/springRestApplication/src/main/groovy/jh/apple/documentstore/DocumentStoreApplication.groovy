package jh.apple.documentstore

import jh.apple.documentstore.service.bootstrap.ApplicationContentBootstrapContract
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import javax.annotation.PostConstruct

@SpringBootApplication(scanBasePackages = ["jh.apple.documentstore"])
class DocumentStoreApplication {

	static void main(String[] args) {
		SpringApplication.run(DocumentStoreApplication, args)
	}
    @Autowired
    @Qualifier("applicationContentBootstrapService")
    private ApplicationContentBootstrapContract bootspinner

    @PostConstruct
    void postConstruct() {
        assert bootspinner
//        def result = bootspinner.spinUp()
//        assert result
    }
}
