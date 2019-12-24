package ru.ved.taskfiat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.ved.taskfiat.dao.Dao;
import ru.ved.taskfiat.model.Quote;

@SpringBootApplication
public class TaskFiatApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(TaskFiatApplication.class);

	@Autowired
	private Dao dao;

	public static void main(final String[] args) {
		SpringApplication.run(TaskFiatApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		// just for test purposes
		createTable();
		loadTestData();
	}

	private void loadTestData() {
		LOG.info("Loading test data");
		dao.insert(new Quote("TESTQUOTE101", 101.2, 101.5, 200, 300, 101.2));
		dao.insert(new Quote("TESTQUOTE102", 111.2, 111.5, 300, 400, 111.5));
		dao.getAll().forEach(q -> LOG.info(q.toString()));
	}

	private void createTable() {
		final String ddl = "CREATE TABLE quotes (" + "id IDENTITY PRIMARY KEY, " + "isin VARCHAR(12), "
				+ "bid DECIMAL, " + "ask DECIMAL, " + "bidSize INT, " + "askSize INT, " + "elvl DECIMAL)";
		LOG.info("Creating table");
		dao.executeDdl("DROP TABLE quotes IF EXISTS");
		dao.executeDdl(ddl);
	}

}
