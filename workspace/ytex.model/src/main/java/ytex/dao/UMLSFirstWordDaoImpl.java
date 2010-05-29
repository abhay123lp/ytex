package ytex.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ytex.model.UMLSFirstWord;

public class UMLSFirstWordDaoImpl implements UMLSFirstWordDao {
	private SimpleJdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	private Properties searchProperties;

	private String query;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setSearchProperties(Properties searchProperties) {
		this.searchProperties = searchProperties;
		this.query = searchProperties.getProperty("retrieveCUIByFword");
	}

	public Properties getSearchProperties() {
		return searchProperties;
	}

	public static class UMLSFirstWordRowMapper implements
			RowMapper<UMLSFirstWord> {

		@Override
		public UMLSFirstWord mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			UMLSFirstWord fword = new UMLSFirstWord();
			fword.setCui(rs.getString("cui"));
			fword.setFword(rs.getString("fword"));
			fword.setText(rs.getString("text"));
			return fword;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.va.vacs.esld.dao.UMLSFirstWordDao#getUMLSbyFirstWord(java.lang.String
	 * )
	 */
	public List<UMLSFirstWord> getUMLSbyFirstWord(String textStart) {
		String words[] = textStart.toLowerCase().split("\\s+");
		String fword = textStart.toLowerCase();
		int nFWordLength = fword.length();
		String text = textStart.toLowerCase();
		int nTextLength = textStart.length();
		if (words.length > 1) {
			fword = words[0];
			nFWordLength = fword.length();
		}
		return this.jdbcTemplate.query(query, new UMLSFirstWordRowMapper(),
				new Object[] { fword.length(), fword, nTextLength, text });
	}
}
