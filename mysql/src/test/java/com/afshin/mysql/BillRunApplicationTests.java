package com.afshin.mysql;

import com.afshin.mysql.entity.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BillRunApplicationTests {

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setup() {
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Test
	public void testJobResults() {
		List<Bill> billStatements = this.jdbcTemplate.query("select bill_id, " +
						"first_name, last_name, minutes, data_usage, bill_amount " +
						"FROM bill_statement ORDER BY bill_id",
				(rs, rowNum) -> new Bill(
						rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"),
						rs.getInt("DATA_USAGE"), rs.getInt("MINUTES"),
						rs.getDouble("bill_amount"),rs.getDate("create_date")));

		assertThat(billStatements.size()).isEqualTo(5);
		Bill billStatement = billStatements.get(0);
		assertThat(billStatement.getBillAmount()).isEqualTo(6.0);
		assertThat(billStatement.getFirstName()).isEqualTo("jane");
		assertThat(billStatement.getLastName()).isEqualTo("doe");
		assertThat(billStatement.getBillId()).isEqualTo(1);
		assertThat(billStatement.getMinutes()).isEqualTo(500);
		assertThat(billStatement.getDataUsage()).isEqualTo(1000);

	}
}