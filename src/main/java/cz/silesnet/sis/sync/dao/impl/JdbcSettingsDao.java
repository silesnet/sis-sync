package cz.silesnet.sis.sync.dao.impl;

import cz.silesnet.sis.sync.dao.SettingsDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * User: der3k
 * Date: 9.10.11
 * Time: 19:08
 */
public class JdbcSettingsDao implements SettingsDao {
  private static final String SQL = "SELECT value FROM settings WHERE name=?";
  private JdbcTemplate template;

  public void setJdbcTemplate(JdbcTemplate template) {
    this.template = template;
  }

  @Override
  public String valueOf(final String key) {
    return (String) template.queryForObject(SQL, new Object[]{key}, String.class);
  }
}
